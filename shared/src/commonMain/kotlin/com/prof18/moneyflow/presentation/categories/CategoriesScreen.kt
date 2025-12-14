package com.prof18.moneyflow.presentation.categories

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.prof18.moneyflow.database.model.TransactionType
import com.prof18.moneyflow.domain.entities.Category
import com.prof18.moneyflow.presentation.categories.components.CategoryCard
import com.prof18.moneyflow.presentation.categories.data.CategoryUIData
import com.prof18.moneyflow.presentation.categories.data.toCategoryUIData
import com.prof18.moneyflow.presentation.model.CategoryIcon
import com.prof18.moneyflow.presentation.model.UIErrorMessage
import com.prof18.moneyflow.ui.components.ErrorView
import com.prof18.moneyflow.ui.components.Loader
import com.prof18.moneyflow.ui.components.MFTopBar
import com.prof18.moneyflow.ui.style.MoneyFlowTheme
import money_flow.shared.generated.resources.Res
import money_flow.shared.generated.resources.add
import money_flow.shared.generated.resources.categories_screen
import money_flow.shared.generated.resources.error_get_categories_message
import money_flow.shared.generated.resources.error_nerd_message
import org.jetbrains.compose.resources.stringResource

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
                topAppBarText = stringResource(Res.string.categories_screen),
                actionTitle = stringResource(Res.string.add),
                onBackPressed = { navigateUp() },
                onActionClicked = {
                    // TODO: open a new screen to add a new category
                },
                actionEnabled = true,
            )
        },
        content = { innerPadding ->
            when (categoryModel) {
                CategoryModel.Loading -> Loader()
                is CategoryModel.Error -> {
                    ErrorView(uiErrorMessage = categoryModel.uiErrorMessage)
                }
                is CategoryModel.CategoryState -> {
                    LazyColumn(modifier = Modifier.padding(innerPadding)) {
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
                            HorizontalDivider()
                        }
                    }
                }
            }
        },
    )
}

@Preview(name = "CategoriesScreen Light")
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
                            type = TransactionType.OUTCOME,
                            createdAtMillis = 1,
                        ),
                        Category(
                            id = 0,
                            name = "Drinks",
                            icon = CategoryIcon.IC_COCKTAIL_SOLID,
                            type = TransactionType.OUTCOME,
                            createdAtMillis = 1,
                        ),
                    ),
                ),
            )
        }
    }
}

@Preview(name = "CategoriesScreenError Light")
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
                        message = Res.string.error_get_categories_message,
                        messageKey = "error_get_categories_message",
                        nerdMessage = Res.string.error_nerd_message,
                        nerdMessageKey = "error_nerd_message",
                        nerdMessageArgs = listOf("101"),
                    ),
                ),
            )
        }
    }
}
