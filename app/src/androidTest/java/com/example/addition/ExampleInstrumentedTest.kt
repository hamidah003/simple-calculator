package com.example.addition

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.addition", appContext.packageName)
    }

    @Test
    fun testOperationDisplay() {
        composeTestRule.setContent {
            CalculatorScreen()
        }

        // Enter "7 + 2"
        composeTestRule.onNodeWithText("7").performClick()
        composeTestRule.onNodeWithText("+").performClick()
        composeTestRule.onNodeWithText("2").performClick()

        // Verify the display shows "7 + 2"
        composeTestRule.onNodeWithText("7 + 2").assertExists()

        // Click "="
        composeTestRule.onNodeWithText("=").performClick()

        // Verify the display shows "9"
        composeTestRule.onNodeWithText("9").assertExists()
    }

    @Test
    fun testChainedOperationDisplay() {
        composeTestRule.setContent {
            CalculatorScreen()
        }

        // Enter "10 + 5 -"
        composeTestRule.onNodeWithText("1").performClick()
        composeTestRule.onNodeWithText("0").performClick()
        composeTestRule.onNodeWithText("+").performClick()
        composeTestRule.onNodeWithText("5").performClick()
        composeTestRule.onNodeWithText("-").performClick()

        // Verify the display shows "15.0 -" (10 + 5 = 15)
        composeTestRule.onNodeWithText("15.0 -").assertExists()

        // Enter "3 ="
        composeTestRule.onNodeWithText("3").performClick()
        composeTestRule.onNodeWithText("=").performClick()

        // Verify the display shows "12.0" (15 - 3 = 12)
        composeTestRule.onNodeWithText("12.0").assertExists()
    }
}