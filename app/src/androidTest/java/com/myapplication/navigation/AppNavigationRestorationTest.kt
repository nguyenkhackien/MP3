package com.myapplication.navigation

import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.ui.test.junit4.StateRestorationTester
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class AppNavigationRestorationTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun restoresSelectedTabAndEveryStackWithOriginalEntryIds() {
        val restorationTester = StateRestorationTester(composeRule)
        lateinit var state: AppNavigationState
        restorationTester.setContent {
            state = rememberAppNavigationState()
            Text("${state.selectedTab}: ${state.currentBackStack.size}")
        }
        lateinit var originalStacks: Map<AppRoute, List<AppBackStackEntry>>
        composeRule.runOnIdle {
            state.handle(NavigationIntent.NavigateTo(AppRoute.Blank))
            state.handle(NavigationIntent.SelectTab(AppRoute.Library))
            state.handle(NavigationIntent.NavigateTo(AppRoute.Blank))
            originalStacks = state.tabBackStacks.mapValues { it.value.toList() }
        }
        restorationTester.emulateSavedInstanceStateRestore()
        composeRule.runOnIdle {
            assertTrue(state.isInTabFlow)
            assertEquals(AppRoute.Library, state.selectedTab)
            assertEquals(originalStacks, state.tabBackStacks.mapValues { it.value.toList() })
            state.navigateUp()
            state.navigateUp()
            assertEquals(AppRoute.Home, state.selectedTab)
            assertEquals(listOf(AppRoute.Home, AppRoute.Blank), state.currentBackStack.map { it.route })
        }
    }

    @Test
    fun restoresIndependentRootWithoutRevealingPreviousHistory() {
        val restorationTester = StateRestorationTester(composeRule)
        lateinit var state: AppNavigationState
        restorationTester.setContent {
            state = rememberAppNavigationState()
            Text("${state.isInTabFlow}: ${state.currentBackStack.size}")
        }
        lateinit var rootEntry: AppBackStackEntry
        composeRule.runOnIdle {
            state.handle(NavigationIntent.NavigateTo(AppRoute.Blank))
            state.handle(NavigationIntent.ReplaceAll(AppRoute.Blank))
            rootEntry = state.rootBackStack.single()
        }
        restorationTester.emulateSavedInstanceStateRestore()
        composeRule.runOnIdle {
            assertFalse(state.isInTabFlow)
            assertEquals(rootEntry, state.rootBackStack.single())
            state.navigateUp()
            assertFalse(state.isInTabFlow)
            assertEquals(rootEntry, state.rootBackStack.single())
            state.tabBackStacks.forEach { (route, stack) ->
                assertEquals(listOf(route), stack.map { it.route })
            }
        }
    }
}
