package com.myapplication.navigation

import androidx.compose.runtime.mutableStateOf
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.serialization.NavBackStackSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import org.junit.Assert.*
import org.junit.Test

class AppNavigationStateTest {
    private fun newState() = AppNavigationState(
        startRoute = AppRoute.Home,
        selectedTab = mutableStateOf<AppRoute>(AppRoute.Home),
        tabBackStacks = listOf(AppRoute.Home, AppRoute.Library, AppRoute.Profile).associateWith {
            NavBackStack(AppBackStackEntry(it))
        },
        rootBackStack = NavBackStack()
    )

    private fun AppNavigationState.routes() = currentBackStack.map { it.route }

    @Test
    fun backAtHomeKeepsLastRoot() {
        val state = newState()
        state.navigateUp()
        assertEquals(listOf(AppRoute.Home), state.routes())
    }

    @Test
    fun repeatedVisitsHaveIndependentEntryIds() {
        val state = newState()
        repeat(2) { state.handle(NavigationIntent.NavigateTo(AppRoute.Blank)) }
        assertEquals(listOf(AppRoute.Home, AppRoute.Blank, AppRoute.Blank), state.routes())
        assertEquals(3, state.currentBackStack.map { it.id }.distinct().size)
        state.navigateUp()
        assertEquals(listOf(AppRoute.Home, AppRoute.Blank), state.routes())
    }

    @Test
    fun singleTopKeepsExistingEntry() {
        val state = newState()
        val intent = NavigationIntent.NavigateTo(AppRoute.Blank, singleTop = true)
        state.handle(intent)
        val entry = state.currentBackStack.last()
        state.handle(intent)
        assertEquals(2, state.currentBackStack.size)
        assertSame(entry, state.currentBackStack.last())
    }

    @Test
    fun popUpToRetainsTargetUnlessInclusive() {
        val state = newState()
        state.handle(NavigationIntent.NavigateTo(AppRoute.Blank))
        state.handle(NavigationIntent.NavigateTo(AppRoute.Blank, popUpToRoute = AppRoute.Home))
        assertEquals(listOf(AppRoute.Home, AppRoute.Blank), state.routes())
        state.handle(NavigationIntent.NavigateTo(AppRoute.Blank, popUpToRoute = AppRoute.Home, inclusive = true))
        assertEquals(listOf(AppRoute.Blank), state.routes())
        state.navigateUp()
        assertEquals(listOf(AppRoute.Blank), state.routes())
    }

    @Test
    fun popUpToMissingRouteLeavesExistingHistory() {
        val state = newState()
        state.handle(NavigationIntent.NavigateTo(AppRoute.Blank, popUpToRoute = AppRoute.Profile))
        assertEquals(listOf(AppRoute.Home, AppRoute.Blank), state.routes())
    }

    @Test
    fun switchingTabsPreservesTheirHistoriesAndEntryIds() {
        val state = newState()
        state.handle(NavigationIntent.NavigateTo(AppRoute.Blank))
        val homeEntries = state.currentBackStack.toList()
        state.handle(NavigationIntent.SelectTab(AppRoute.Library))
        state.handle(NavigationIntent.NavigateTo(AppRoute.Blank))
        val libraryEntries = state.currentBackStack.toList()
        state.handle(NavigationIntent.SelectTab(AppRoute.Home))
        assertEquals(homeEntries, state.currentBackStack.toList())
        state.handle(NavigationIntent.SelectTab(AppRoute.Library))
        assertEquals(libraryEntries, state.currentBackStack.toList())
        assertNotEquals(homeEntries.last().id, libraryEntries.last().id)
    }

    @Test
    fun backPopsChildThenReturnsHomeWithItsHistory() {
        val state = newState()
        state.handle(NavigationIntent.NavigateTo(AppRoute.Blank))
        state.handle(NavigationIntent.SelectTab(AppRoute.Profile))
        state.handle(NavigationIntent.NavigateTo(AppRoute.Blank))
        state.navigateUp()
        assertEquals(listOf(AppRoute.Profile), state.routes())
        state.navigateUp()
        assertEquals(AppRoute.Home, state.selectedTab)
        assertEquals(listOf(AppRoute.Home, AppRoute.Blank), state.routes())
    }

    @Test
    fun navigatingToTabRouteSwitchesInsteadOfDuplicatingRoot() {
        val state = newState()
        state.handle(NavigationIntent.NavigateTo(AppRoute.Library))
        state.handle(NavigationIntent.NavigateTo(AppRoute.Library))
        assertEquals(AppRoute.Library, state.selectedTab)
        assertEquals(listOf(AppRoute.Library), state.routes())
        assertEquals(2, state.visibleStacks.size)
    }

    @Test(expected = IllegalArgumentException::class)
    fun selectTabRejectsChildRoute() {
        newState().handle(NavigationIntent.SelectTab(AppRoute.Blank))
    }

    @Test
    fun replaceAllClearsAllHistoriesAndOpensIndependentRoot() {
        val state = newState()
        val oldIds = state.tabBackStacks.values.flatMap { it }.map { it.id }.toSet()
        state.handle(NavigationIntent.NavigateTo(AppRoute.Blank))
        state.handle(NavigationIntent.SelectTab(AppRoute.Profile))
        state.handle(NavigationIntent.NavigateTo(AppRoute.Blank))
        state.handle(NavigationIntent.ReplaceAll(AppRoute.Blank))
        assertFalse(state.isInTabFlow)
        assertEquals(listOf(AppRoute.Blank), state.routes())
        assertEquals(1, state.visibleStacks.size)
        state.tabBackStacks.forEach { (root, stack) ->
            assertEquals(listOf(root), stack.map { it.route })
            assertFalse(stack.single().id in oldIds)
        }
        state.navigateUp()
        assertFalse(state.isInTabFlow)
        state.handle(NavigationIntent.ReplaceAll(AppRoute.Home))
        assertTrue(state.isInTabFlow)
        assertEquals(listOf(AppRoute.Home), state.routes())
    }

    @Test
    fun backOnIndependentRootCannotRevealOldTabHistory() {
        val state = newState()
        state.handle(NavigationIntent.ReplaceAll(AppRoute.Blank))
        state.handle(NavigationIntent.NavigateTo(AppRoute.Blank))
        state.navigateUp()
        state.navigateUp()
        assertFalse(state.isInTabFlow)
        assertEquals(1, state.currentBackStack.size)
    }

    @Test
    fun serializationRestoresRoutesAndStableEntryIds() {
        val state = newState()
        state.handle(NavigationIntent.NavigateTo(AppRoute.Blank))
        state.handle(NavigationIntent.SelectTab(AppRoute.Library))
        state.handle(NavigationIntent.NavigateTo(AppRoute.Blank))
        val serializer = ListSerializer(NavBackStackSerializer(AppBackStackEntry.serializer()))
        val original = state.tabBackStacks.values.toList()
        val restored = Json.decodeFromString(serializer, Json.encodeToString(serializer, original))
        assertEquals(original.map { it.toList() }, restored.map { it.toList() })
        val restoredTab = Json.decodeFromString(AppRoute.serializer(), Json.encodeToString(AppRoute.serializer(), state.selectedTab))
        assertEquals(AppRoute.Library, restoredTab)
    }
}
