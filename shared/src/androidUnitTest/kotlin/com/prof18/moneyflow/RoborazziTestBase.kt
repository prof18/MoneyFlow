package com.prof18.moneyflow

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onRoot
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.github.takahirom.roborazzi.RoborazziRule
import com.github.takahirom.roborazzi.captureRoboImage
import com.prof18.moneyflow.domain.entities.MoneyTransaction
import com.prof18.moneyflow.domain.entities.TransactionTypeUI
import com.prof18.moneyflow.presentation.categories.data.CategoryUIData
import com.prof18.moneyflow.presentation.model.CategoryIcon
import org.junit.After
import org.junit.Rule
import java.io.File

open class RoborazziTestBase(
    captureType: RoborazziRule.CaptureType = RoborazziRule.CaptureType.None,
) {
    @get:Rule
    val composeRule: AndroidComposeTestRule<ActivityScenarioRule<ComponentActivity>, ComponentActivity> =
        createAndroidComposeRule()

    @get:Rule
    val roborazziRule: RoborazziRule = roborazziOf(composeRule, captureType)

    private val snapshotDir: File = File(
        System.getProperty("roborazzi.test.record.dir")
            ?: File(System.getProperty("user.dir")).resolve("image/roborazzi").path,
    ).also { directory ->
        directory.mkdirs()
    }

    @After
    fun tearDown() {
        composeRule.activityRule.scenario.recreate()
    }

    protected fun capture(name: String) {
        val target = snapshotDir.resolve("$name.png")
        composeRule.waitForIdle()
        composeRule.onRoot().captureRoboImage(target.path)
    }
}

internal object RoborazziSampleData {
    val sampleCategory = CategoryUIData(
        id = 1,
        name = "Food",
        icon = CategoryIcon.IC_HAMBURGER_SOLID,
    )

    val sampleTransactions = listOf(
        MoneyTransaction(
            id = 0,
            title = "Ice Cream",
            icon = CategoryIcon.IC_ICE_CREAM_SOLID,
            amount = 10.0,
            type = TransactionTypeUI.EXPENSE,
            milliseconds = 0,
            formattedDate = "12 July 2021",
        ),
        MoneyTransaction(
            id = 1,
            title = "Tip",
            icon = CategoryIcon.IC_MONEY_CHECK_ALT_SOLID,
            amount = 50.0,
            type = TransactionTypeUI.INCOME,
            milliseconds = 0,
            formattedDate = "12 July 2021",
        ),
    )
}
