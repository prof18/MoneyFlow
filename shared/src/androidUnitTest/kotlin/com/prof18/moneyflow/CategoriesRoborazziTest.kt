package com.prof18.moneyflow

import androidx.compose.foundation.layout.PaddingValues
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.prof18.moneyflow.domain.entities.Category
import com.prof18.moneyflow.presentation.categories.CategoriesScreen
import com.prof18.moneyflow.presentation.categories.CategoryModel
import com.prof18.moneyflow.presentation.model.CategoryIcon
import com.prof18.moneyflow.ui.style.MoneyFlowTheme
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(AndroidJUnit4::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [33])
class CategoriesRoborazziTest : RoborazziTestBase() {

    @Test
    fun captureCategoriesScreen() {
        composeRule.setContent {
            MoneyFlowTheme {
                CategoriesScreen(
                    navigateUp = {},
                    sendCategoryBack = {},
                    isFromAddTransaction = true,
                    categoryModel = CategoryModel.CategoryState(
                        categories = listOf(
                            Category(
                                id = 0,
                                name = "Food",
                                icon = CategoryIcon.IC_HAMBURGER_SOLID,
                            ),
                            Category(
                                id = 1,
                                name = "Drinks",
                                icon = CategoryIcon.IC_COCKTAIL_SOLID,
                            ),
                        ),
                    ),
                    paddingValues = PaddingValues(),
                )
            }
        }

        capture("categories_screen")
    }
}
