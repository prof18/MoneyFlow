package com.prof18.moneyflow.features.categories

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.prof18.moneyflow.ComposeNavigationFactory
import com.prof18.moneyflow.NavigationArguments
import com.prof18.moneyflow.R
import com.prof18.moneyflow.Screen
import com.prof18.moneyflow.domain.entities.Category
import com.prof18.moneyflow.features.categories.components.CategoryCard
import com.prof18.moneyflow.features.categories.data.CategoryUIData
import com.prof18.moneyflow.features.categories.data.toCategoryUIData
import com.prof18.moneyflow.presentation.CategoryIcon
import com.prof18.moneyflow.ui.components.Loader
import com.prof18.moneyflow.ui.components.MFTopBar
import com.prof18.moneyflow.presentation.categories.CategoryModel
import com.prof18.moneyflow.ui.style.MoneyFlowTheme
import org.koin.androidx.compose.getViewModel

class CategoriesScreenFactory(private val categoryState: MutableState<CategoryUIData?>): ComposeNavigationFactory {
    override fun create(navGraphBuilder: NavGraphBuilder, navController: NavController) {
        navGraphBuilder.composable(
            route = Screen.CategoriesScreen.route + "/{${NavigationArguments.FromAddTransaction.key}}",
            arguments = listOf(navArgument(NavigationArguments.FromAddTransaction.key) {
                type = NavType.BoolType
            })
        ) { backStackEntry ->

            val viewModel = getViewModel<CategoriesViewModel>()

            CategoriesScreen(
                navigateUp = { navController.popBackStack() },
                sendCategoryBack = { categoryData ->
                    categoryState.value = categoryData
                },
                isFromAddTransaction = backStackEntry.arguments?.getBoolean(
                    NavigationArguments.FromAddTransaction.key
                ) ?: false,
                categoryModel = viewModel.categoryModel
            )
        }
    }
}

@Composable
fun CategoriesScreen(
    navigateUp: () -> Unit,
    sendCategoryBack: (CategoryUIData) -> Unit,
    isFromAddTransaction: Boolean,
    categoryModel: CategoryModel
) {

    Scaffold(
        topBar = {
            MFTopBar(
                topAppBarText = stringResource(id = R.string.categories_screen),
                actionTitle = stringResource(R.string.add),
                onBackPressed = { navigateUp() },
                onActionClicked = {
                    // TODO: open a new screen to add a new category
                },
                actionEnabled = true
            )
        },
        content = {
            when (categoryModel) {
                CategoryModel.Loading -> Loader()
                is CategoryModel.Error -> {
                    Text(categoryModel.message)
                }
                is CategoryModel.CategoryState -> {
                    LazyColumn {
                        items(categoryModel.categories) {
                            CategoryCard(
                                category = it,
                                onClick = { category ->
                                    if (isFromAddTransaction) {
                                        sendCategoryBack(category.toCategoryUIData())
                                        navigateUp()
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

@Preview
@Composable
fun CategoriesScreenLightPreview() {
    MoneyFlowTheme {
        Surface {
            CategoriesScreen(
                navigateUp = { },
                sendCategoryBack = {  },
                isFromAddTransaction = true,
                categoryModel = CategoryModel.CategoryState(
                    categories = listOf(
                        Category(
                            id = 0,
                            name = "Food",
                            icon = CategoryIcon.IC_HAMBURGER_SOLID
                        ),
                        Category(
                            id = 0,
                            name = "Drinks",
                            icon = CategoryIcon.IC_COCKTAIL_SOLID
                        )
                    )
                )
            )
        }
    }
}

@Preview
@Composable
fun CategoriesScreenDarkPreview() {
    MoneyFlowTheme(darkTheme = true) {
        Surface {
            CategoriesScreen(
                navigateUp = { },
                sendCategoryBack = {  },
                isFromAddTransaction = true,
                categoryModel = CategoryModel.CategoryState(
                    categories = listOf(
                        Category(
                            id = 0,
                            name = "Food",
                            icon = CategoryIcon.IC_HAMBURGER_SOLID
                        ),
                        Category(
                            id = 0,
                            name = "Drinks",
                            icon = CategoryIcon.IC_COCKTAIL_SOLID
                        )
                    )
                )
            )
        }
    }
}