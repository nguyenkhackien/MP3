package com.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSerializable
import androidx.compose.runtime.setValue
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.serialization.NavBackStackSerializer
import androidx.savedstate.compose.serialization.serializers.MutableStateSerializer
import java.util.UUID
import kotlinx.serialization.Serializable

/** Each visit gets its own saved UI state and ViewModel, even for identical routes. */
@Serializable
data class AppBackStackEntry(
    val route: AppRoute,
    val id: String = UUID.randomUUID().toString()
) : NavKey

/** UI-owned state. All mutations run in the root host's main-thread coroutine. */
class AppNavigationState(
    val startRoute: AppRoute,
    selectedTab: MutableState<AppRoute>,
    val tabBackStacks: Map<AppRoute, NavBackStack<AppBackStackEntry>>,
    val rootBackStack: NavBackStack<AppBackStackEntry>
) {
    var selectedTab: AppRoute by selectedTab
        private set

    init {
        require(startRoute in tabBackStacks)
        require(selectedTab.value in tabBackStacks)
        require(tabBackStacks.values.all { it.isNotEmpty() })
    }

    val isInTabFlow: Boolean get() = rootBackStack.isEmpty()

    val currentBackStack: NavBackStack<AppBackStackEntry>
        get() = if (isInTabFlow) tabBackStacks.getValue(selectedTab) else rootBackStack

    /** Home stays underneath other tabs so system Back returns home before exiting. */
    val visibleStacks: List<NavBackStack<AppBackStackEntry>>
        get() = when {
            !isInTabFlow -> listOf(rootBackStack)
            selectedTab == startRoute -> listOf(tabBackStacks.getValue(startRoute))
            else -> listOf(tabBackStacks.getValue(startRoute), currentBackStack)
        }

    fun handle(intent: NavigationIntent) {
        when (intent) {
            is NavigationIntent.NavigateTo -> navigateTo(intent)
            NavigationIntent.NavigateUp -> navigateUp()
            is NavigationIntent.SelectTab -> selectTab(intent.destination)
            is NavigationIntent.ReplaceAll -> replaceAll(intent.destination)
        }
    }

    private fun navigateTo(intent: NavigationIntent.NavigateTo) {
        // Root destinations switch tabs; child routes are pushed on the active stack.
        if (isInTabFlow && intent.destination in tabBackStacks && intent.popUpToRoute == null) {
            selectTab(intent.destination)
            return
        }
        val stack = currentBackStack
        intent.popUpToRoute?.let { route ->
            val index = stack.indexOfLast { it.route == route }
            if (index >= 0) {
                val keepCount = if (intent.inclusive) index else index + 1
                while (stack.size > keepCount) stack.removeAt(stack.lastIndex)
            }
        }
        if (!intent.singleTop || stack.lastOrNull()?.route != intent.destination) {
            stack.add(AppBackStackEntry(intent.destination))
        }
    }

    fun navigateUp() {
        val stack = currentBackStack
        when {
            stack.size > 1 -> stack.removeAt(stack.lastIndex)
            isInTabFlow && selectedTab != startRoute -> selectedTab = startRoute
            // Never empty the last root. NavDisplay lets system Back exit at that point.
        }
    }

    private fun selectTab(destination: AppRoute) {
        require(destination in tabBackStacks) { "$destination is not a tab destination" }
        rootBackStack.clear()
        selectedTab = destination
    }

    private fun replaceAll(destination: AppRoute) {
        // Replace entry IDs too: no old screen state or ViewModel should survive a reset.
        tabBackStacks.forEach { (route, stack) ->
            stack.clear()
            stack.add(AppBackStackEntry(route))
        }
        rootBackStack.clear()
        selectedTab = if (destination in tabBackStacks) destination else startRoute
        if (destination !in tabBackStacks) rootBackStack.add(AppBackStackEntry(destination))
    }
}

@Composable
fun rememberAppNavigationState(): AppNavigationState {
    val startRoute = AppRoute.Home
    val selectedTab = rememberSerializable(serializer = MutableStateSerializer(AppRoute.serializer())) {
        mutableStateOf<AppRoute>(startRoute)
    }
    val stacks = BottomNavItem.items.associate { item ->
        item.destination to key(item.destination) { rememberAppBackStack(item.destination) }
    }
    val rootStack = rememberAppBackStack()
    return remember(selectedTab, stacks, rootStack) {
        AppNavigationState(startRoute, selectedTab, stacks, rootStack)
    }
}

@Composable
private fun rememberAppBackStack(vararg routes: AppRoute): NavBackStack<AppBackStackEntry> =
    rememberSerializable(serializer = NavBackStackSerializer(AppBackStackEntry.serializer())) {
        NavBackStack(*routes.map { AppBackStackEntry(it) }.toTypedArray())
    }
