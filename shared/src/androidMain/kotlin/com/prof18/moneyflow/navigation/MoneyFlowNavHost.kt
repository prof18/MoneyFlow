package com.prof18.moneyflow.navigation

import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSerializable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.compose.serialization.serializers.SnapshotStateListSerializer
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import com.prof18.moneyflow.presentation.addtransaction.AddTransactionScreen
import com.prof18.moneyflow.features.addtransaction.AddTransactionViewModel
import com.prof18.moneyflow.presentation.alltransactions.AllTransactionsScreen
import com.prof18.moneyflow.features.alltransactions.AllTransactionsViewModel
import com.prof18.moneyflow.presentation.categories.CategoriesScreen
import com.prof18.moneyflow.features.categories.CategoriesViewModel
import com.prof18.moneyflow.presentation.categories.data.CategoryUIData
import com.prof18.moneyflow.presentation.home.HomeScreen
import com.prof18.moneyflow.features.home.HomeViewModel
import com.prof18.moneyflow.features.settings.SettingsScreen
import com.prof18.moneyflow.features.settings.SettingsViewModel
import com.prof18.moneyflow.ui.style.LightAppColors
import money_flow.shared.generated.resources.Res
import money_flow.shared.generated.resources.ic_cog_solid
import money_flow.shared.generated.resources.ic_home_solid
import money_flow.shared.generated.resources.home_screen
import money_flow.shared.generated.resources.settings_screen
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MoneyFlowNavHost() {
    val backStack = rememberSerializable(serializer = SnapshotStateListSerializer(AppRoute.serializer())) {
        mutableStateListOf<AppRoute>(HomeRoute)
    }
    val categoryState = remember { mutableStateOf<CategoryUIData?>(null) }

    Scaffold(
        bottomBar = {
            BottomBar(
                currentRoute = backStack.lastOrNull(),
                onNavigate = { destination ->
                    backStack.clear()
                    backStack.add(destination)
                },
            )
        },
    ) { paddingValues ->
        NavDisplay(
            backStack = backStack,
            entryProvider = entryProvider { screens(backStack, categoryState, paddingValues) },
            entryDecorators = listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator(),
            ),
        )
    }
}

@Composable
private fun BottomBar(
    currentRoute: AppRoute?,
    onNavigate: (AppRoute) -> Unit,
) {
    if (currentRoute !is HomeRoute && currentRoute !is SettingsRoute) return
    BottomNavigation {
        bottomNavigationItems.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(item.drawableRes),
                        contentDescription = null,
                        modifier = Modifier.size(22.dp),
                    )
                },
                label = { Text(stringResource(item.titleRes)) },
                selected = currentRoute::class == item.route::class,
                unselectedContentColor = LightAppColors.lightGrey.copy(alpha = 0.3f),
                onClick = { if (currentRoute::class != item.route::class) onNavigate(item.route) },
            )
        }
    }
}

private data class BottomNavigationItem(
    val route: AppRoute,
    val titleRes: org.jetbrains.compose.resources.StringResource,
    val drawableRes: DrawableResource,
)

private val bottomNavigationItems = listOf(
    BottomNavigationItem(
        route = HomeRoute,
        titleRes = Res.string.home_screen,
        drawableRes = Res.drawable.ic_home_solid,
    ),
    BottomNavigationItem(
        route = SettingsRoute,
        titleRes = Res.string.settings_screen,
        drawableRes = Res.drawable.ic_cog_solid,
    ),
)

private fun EntryProviderScope<AppRoute>.screens(
    backStack: MutableList<AppRoute>,
    categoryState: androidx.compose.runtime.MutableState<CategoryUIData?>,
    paddingValues: PaddingValues,
) {
    entry<HomeRoute> {
        val homeViewModel = koinViewModel<HomeViewModel>()
        val homeModel by homeViewModel.homeModel.collectAsState()
        val hideSensitiveData by homeViewModel.hideSensitiveDataState.collectAsState()

        HomeScreen(
            navigateToAddTransaction = { backStack.add(AddTransactionRoute) },
            paddingValues = paddingValues,
            deleteTransaction = { id -> homeViewModel.deleteTransaction(id) },
            homeModel = homeModel,
            hideSensitiveDataState = hideSensitiveData,
            changeSensitiveDataVisibility = { homeViewModel.changeSensitiveDataVisibility(it) },
            navigateToAllTransactions = { backStack.add(AllTransactionsRoute) },
        )
    }

    entry<AddTransactionRoute> {
        val viewModel = koinViewModel<AddTransactionViewModel>()
        val uiState by viewModel.uiState.collectAsState()

        AddTransactionScreen(
            categoryState = categoryState,
            navigateUp = { backStack.removeLastOrNull() },
            navigateToCategoryList = { backStack.add(CategoriesRoute(fromAddTransaction = true)) },
            addTransaction = viewModel::addTransaction,
            amountText = uiState.amountText,
            updateAmountText = viewModel::updateAmountText,
            descriptionText = uiState.descriptionText,
            updateDescriptionText = viewModel::updateDescriptionText,
            selectedTransactionType = uiState.selectedTransactionType,
            updateTransactionType = viewModel::updateTransactionType,
            updateYear = viewModel::setYearNumber,
            updateMonth = viewModel::setMonthNumber,
            updateDay = viewModel::setDayNumber,
            saveDate = viewModel::saveDate,
            dateLabel = uiState.dateLabel,
            addTransactionAction = uiState.addTransactionAction,
            resetAction = viewModel::resetAction,
        )
    }

    entry<CategoriesRoute> { route ->
        val viewModel = koinViewModel<CategoriesViewModel>()
        val categoryModel by viewModel.categories.collectAsState()

        CategoriesScreen(
            navigateUp = { backStack.removeLastOrNull() },
            sendCategoryBack = { categoryData ->
                if (route.fromAddTransaction) {
                    categoryState.value = categoryData
                    backStack.removeLastOrNull()
                }
            },
            isFromAddTransaction = route.fromAddTransaction,
            categoryModel = categoryModel,
        )
    }

    entry<AllTransactionsRoute> {
        val viewModel = koinViewModel<AllTransactionsViewModel>()
        AllTransactionsScreen(
            navigateUp = { backStack.removeLastOrNull() },
            stateFlow = viewModel.state,
            loadNextPage = viewModel::loadNextPage,
        )
    }

    entry<SettingsRoute> {
        val viewModel = koinViewModel<SettingsViewModel>()
        val hideDataState by viewModel.hideSensitiveDataState.collectAsState()
        val biometricState by viewModel.biometricState.collectAsState()

        SettingsScreen(
            performBackup = { uri -> viewModel.performBackup(com.prof18.moneyflow.features.settings.BackupRequest(uri)) },
            performRestore = { uri -> viewModel.performRestore(com.prof18.moneyflow.features.settings.BackupRequest(uri)) },
            biometricState = biometricState,
            onBiometricEnabled = viewModel::updateBiometricState,
            hideSensitiveDataState = hideDataState,
            onHideSensitiveDataEnabled = viewModel::updateHideSensitiveDataState,
        )
    }
}
