package com.prof18.moneyflow.features.categories

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
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
import com.prof18.moneyflow.presentation.categories.CategoryModel
import com.prof18.moneyflow.presentation.model.CategoryIcon
import com.prof18.moneyflow.presentation.model.UIErrorMessage
import com.prof18.moneyflow.ui.components.ErrorView
import com.prof18.moneyflow.ui.components.Loader
import com.prof18.moneyflow.ui.components.MFTopBar
import com.prof18.moneyflow.ui.style.MoneyFlowTheme
import org.koin.androidx.compose.getViewModel

internal class CategoriesScreenFactory(
    private val categoryState: MutableState<CategoryUIData?>,
) : ComposeNavigationFactory {
    override fun create(navGraphBuilder: NavGraphBuilder, navController: NavController) {
        navGraphBuilder.composable(
            route = Screen.CategoriesScreen.route + "/{${NavigationArguments.FromAddTransaction.key}}",
            arguments = listOf(
                navArgument(NavigationArguments.FromAddTransaction.key) {
                    type = NavType.BoolType
                },
            ),
        ) { backStackEntry ->

            val viewModel = getViewModel<CategoriesViewModel>()

            CategoriesScreen(
                navigateUp = { navController.popBackStack() },
                sendCategoryBack = { categoryData ->
                    categoryState.value = categoryData
                },
                isFromAddTransaction = backStackEntry.arguments?.getBoolean(
                    NavigationArguments.FromAddTransaction.key,
                ) ?: false,
                categoryModel = viewModel.categoryModel,
            )
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
internal fun CategoriesScreen(
    navigateUp: () -> Unit,
    sendCategoryBack: (CategoryUIData) -> Unit,
    isFromAddTransaction: Boolean,
    categoryModel: CategoryModel,
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
                actionEnabled = true,
            )
        },
        content = {
            when (categoryModel) {
                CategoryModel.Loading -> Loader()
                is CategoryModel.Error -> {
                    ErrorView(uiErrorMessage = categoryModel.uiErrorMessage)
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
                                },
                            )
                            Divider()
                        }
                    }
                }
            }
        },
    )
}

@Preview(name = "CategoriesScreen Light")
@Preview(name = "CategoriesScreen Night", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun CategoriesScreenPreview() {
    MoneyFlowTheme {
        Surface {
            CategoriesScreen(
                navigateUp = { },
                sendCategoryBack = { },
                isFromAddTransaction = true,
                categoryModel = CategoryModel.CategoryState(
                    categories = listOf(
                        Category(
                            id = 0,
                            name = "Food",
                            icon = CategoryIcon.IC_HAMBURGER_SOLID,
                        ),
                        Category(
                            id = 0,
                            name = "Drinks",
                            icon = CategoryIcon.IC_COCKTAIL_SOLID,
                        ),
                    ),
                ),
            )
        }
    }
}

@Preview(name = "CategoriesScreenError Light")
@Preview(name = "CategoriesScreenError Night", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun CategoriesScreenErrorPreview() {
    MoneyFlowTheme {
        Surface {
            CategoriesScreen(
                navigateUp = { },
                sendCategoryBack = { },
                isFromAddTransaction = true,
                categoryModel = CategoryModel.Error(
                    uiErrorMessage = UIErrorMessage(
                        message = "An error happened :(",
                        nerdMessage = "Error code: 101",
                    ),
                ),
            )
        }
    }
}
