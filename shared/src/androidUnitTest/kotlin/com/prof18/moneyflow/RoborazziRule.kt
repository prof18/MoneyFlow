package com.prof18.moneyflow

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onRoot
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.github.takahirom.roborazzi.RoborazziRule

internal fun roborazziOf(
    scenario: AndroidComposeTestRule<ActivityScenarioRule<ComponentActivity>, ComponentActivity>,
    captureType: RoborazziRule.CaptureType = RoborazziRule.CaptureType.None,
): RoborazziRule {
    return RoborazziRule(
        composeRule = scenario,
        captureRoot = scenario.onRoot(),
        options = RoborazziRule.Options(
            captureType = captureType,
        ),
    )
}
