package com.prof18.moneyflow.features.categories

import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.prof18.moneyflow.NavigationArguments
import com.prof18.moneyflow.features.categories.components.CategoryCard
import com.prof18.moneyflow.features.categories.data.toCategoryUIData
import com.prof18.moneyflow.ui.components.MFTopBar
import domain.model.Category


@Composable
fun CategoriesScreen(
    navController: NavController,
    isFromAddTransaction: Boolean
) {

    Scaffold(
        topBar = {
            MFTopBar(
                topAppBarText = "Categories",
                actionTitle = "Add",
                onBackPressed = {
                    navController.popBackStack()
                },
                onActionClicked = {
                    // TODO
                },
                // TODO
                actionEnabled = true
            )
        },
        bodyContent = {
            LazyColumnFor(items = categories) {
                CategoryCard(
                    category = it,
                    onClick = { category ->
                        if (isFromAddTransaction) {
                            navController.previousBackStackEntry?.savedStateHandle?.set(
                                NavigationArguments.CATEGORY,
                                category.toCategoryUIData()
                            )
                            navController.popBackStack()
                        }
                    }
                )
                Divider()
            }
        }
    )


}

// TODO: just for mock
private val categories = listOf(
    Category(id = 0, name = "Salary"),
    Category(id = 1, name = "Eating Out"),
    Category(id = 2, name = "Bar"),
    Category(id = 3, name = "Electronics"),
    Category(id = 4, name = "Books"),
    Category(id = 5, name = "Travel"),
    Category(id = 6, name = "Hotel"),
    Category(id = 7, name = "Shopping"),
    Category(id = 8, name = "Software"),
    Category(id = 9, name = "Film"),
    Category(id = 10, name = "Music"),
    Category(id = 11, name = "Family"),
)