package com.myapplication.navigation

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

interface AppNavigator {
    /** Commands consumed by the single root host; screen ViewModels do not own UI state. */
    val navigationIntent: Flow<NavigationIntent>

    suspend fun navigateTo(
        destination: AppRoute,
        popUpToRoute: AppRoute? = null,
        inclusive: Boolean = false,
        singleTop: Boolean = false
    )

    suspend fun navigateUp()

    suspend fun selectTab(destination: AppRoute)

    /** Clears every tab's history and opens a new root (for example, after logout). */
    suspend fun replaceAll(destination: AppRoute)
}

sealed interface NavigationIntent {
    data class NavigateTo(
        val destination: AppRoute,
        val popUpToRoute: AppRoute? = null,
        val inclusive: Boolean = false,
        val singleTop: Boolean = false
    ) : NavigationIntent

    data object NavigateUp : NavigationIntent

    data class SelectTab(val destination: AppRoute) : NavigationIntent

    data class ReplaceAll(val destination: AppRoute) : NavigationIntent
}

class DefaultAppNavigator @Inject constructor() : AppNavigator {
    // One root UI collector. Pending commands survive a temporary collector interruption,
    // but are not persisted across process death.
    private val intents = Channel<NavigationIntent>(Channel.BUFFERED)
    override val navigationIntent: Flow<NavigationIntent> = intents.receiveAsFlow()

    override suspend fun navigateTo(
        destination: AppRoute,
        popUpToRoute: AppRoute?,
        inclusive: Boolean,
        singleTop: Boolean
    ) {
        intents.send(NavigationIntent.NavigateTo(destination, popUpToRoute, inclusive, singleTop))
    }

    override suspend fun navigateUp() {
        intents.send(NavigationIntent.NavigateUp)
    }

    override suspend fun selectTab(destination: AppRoute) {
        require(BottomNavItem.items.any { it.destination == destination }) {
            "$destination is not a bottom navigation destination"
        }
        intents.send(NavigationIntent.SelectTab(destination))
    }

    override suspend fun replaceAll(destination: AppRoute) {
        intents.send(NavigationIntent.ReplaceAll(destination))
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class NavigationModule {
    @Binds
    @Singleton
    abstract fun bindAppNavigator(navigator: DefaultAppNavigator): AppNavigator
}
