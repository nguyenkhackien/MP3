package com.myapplication.navigation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.myapplication.MainActivity
import org.junit.Rule
import org.junit.Test

/** Exercises the real Hilt ViewModels, navigator commands and NavDisplay decorators. */
class AppNavigationUiTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun childNavigationTabRetentionAndSystemBackWorkTogether() {
        composeRule.onNodeWithText("For You").assertIsDisplayed()
        composeRule.onAllNodesWithText("Nơi Này Có Anh").onFirst().performClick()
        composeRule.onNodeWithText("For You").assertDoesNotExist()

        composeRule.onNodeWithContentDescription("Library").performClick()
        composeRule.onNodeWithText("Your Library").assertIsDisplayed()
        composeRule.onNodeWithContentDescription("Home").performClick()
        composeRule.onNodeWithText("For You").assertDoesNotExist()

        pressBack()
        composeRule.onNodeWithText("For You").assertIsDisplayed()
        composeRule.onNodeWithContentDescription("Profile").performClick()
        composeRule.onNodeWithContentDescription("More options").assertIsDisplayed()
        pressBack()
        composeRule.onNodeWithText("For You").assertIsDisplayed()

        composeRule.activityRule.scenario.recreate()
        composeRule.onNodeWithText("For You").assertIsDisplayed()
    }

    private fun pressBack() {
        composeRule.activityRule.scenario.onActivity {
            it.onBackPressedDispatcher.onBackPressed()
        }
    }
}
