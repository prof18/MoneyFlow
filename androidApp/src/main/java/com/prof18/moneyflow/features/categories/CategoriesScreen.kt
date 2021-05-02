package com.prof18.moneyflow.features.categories

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.prof18.moneyflow.NavigationArguments
import com.prof18.moneyflow.features.categories.components.CategoryCard
import com.prof18.moneyflow.features.categories.data.toCategoryUIData
import com.prof18.moneyflow.ui.components.Loader
import com.prof18.moneyflow.ui.components.MFTopBar
import com.prof18.moneyflow.presentation.categories.CategoryModel
import org.koin.androidx.compose.getViewModel


@Composable
fun CategoriesScreen(
    navController: NavController,
    isFromAddTransaction: Boolean,
) {

    val viewModel = getViewModel<CategoriesViewModel>()

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
                actionEnabled = true
            )
        },
        content = {
            when (viewModel.categoryModel) {
                CategoryModel.Loading -> Loader()
                is CategoryModel.Error -> {
                    Text((viewModel.categoryModel as CategoryModel.Error).message)
                }
                is CategoryModel.CategoryState -> {

                    LazyColumn {

                        items((viewModel.categoryModel as CategoryModel.CategoryState).categories) {
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
                }
            }
        }
    )
}