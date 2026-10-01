package com.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberDecoratedNavEntries
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.myapplication.ui.screens.blank.BlankScreen
import com.myapplication.ui.screens.home.HomeScreen
import com.myapplication.ui.screens.library.LibraryScreen
import com.myapplication.ui.screens.profile.ProfileScreen

/** Add an entry here for each new AppRoute. */
private val appEntryProvider = entryProvider<AppRoute> {
    entry<AppRoute.Home> { HomeScreen() }
    entry<AppRoute.Library> { LibraryScreen() }
    entry<AppRoute.Profile> { ProfileScreen() }
    entry<AppRoute.Blank> { BlankScreen() }
}

@Composable
fun AppNavHost(
    navigator: AppNavigator,
    navigationState: AppNavigationState,
    modifier: Modifier = Modifier
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(navigator, navigationState, lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
            navigator.navigationIntent.collect(navigationState::handle)
        }
    }

    // Decorate all stacks, including inactive tabs, to retain their UI state and ViewModels.
    val allStacks = navigationState.tabBackStacks.values.toList() + listOf(navigationState.rootBackStack)
    val entriesByStack = allStacks.map { stack ->
        key(stack) {
            rememberDecoratedNavEntries(
                backStack = stack,
                entryDecorators = listOf(
                    rememberSaveableStateHolderNavEntryDecorator<AppBackStackEntry>(),
                    rememberViewModelStoreNavEntryDecorator<AppBackStackEntry>()
                ),
                entryProvider = { entry ->
                    val routeEntry = appEntryProvider(entry.route)
                    NavEntry(
                        key = entry,
                        contentKey = entry.id,
                        metadata = routeEntry.metadata
                    ) { routeEntry.Content() }
                }
            )
        }
    }
    val visibleEntries = navigationState.visibleStacks.flatMap { stack ->
        entriesByStack[allStacks.indexOf(stack)]
    }

    NavDisplay(
        entries = visibleEntries,
        onBack = navigationState::navigateUp,
        modifier = modifier
    )
}
