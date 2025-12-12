package com.prof18.moneyflow.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSerializable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.navigationevent.NavigationEvent
import androidx.savedstate.compose.serialization.serializers.SnapshotStateListSerializer
import com.prof18.moneyflow.features.addtransaction.AddTransactionViewModel
import com.prof18.moneyflow.features.alltransactions.AllTransactionsViewModel
import com.prof18.moneyflow.features.categories.CategoriesViewModel
import com.prof18.moneyflow.features.home.HomeViewModel
import com.prof18.moneyflow.features.settings.BiometricAvailabilityChecker
import com.prof18.moneyflow.features.settings.SettingsViewModel
import com.prof18.moneyflow.presentation.addtransaction.AddTransactionScreen
import com.prof18.moneyflow.presentation.alltransactions.AllTransactionsScreen
import com.prof18.moneyflow.presentation.categories.CategoriesScreen
import com.prof18.moneyflow.presentation.categories.data.CategoryUIData
import com.prof18.moneyflow.presentation.home.HomeScreen
import com.prof18.moneyflow.presentation.settings.SettingsScreen
import com.prof18.moneyflow.utils.LocalAppDensity
import com.prof18.moneyflow.utils.LocalAppLocale
import com.prof18.moneyflow.utils.LocalAppTheme
import com.prof18.moneyflow.utils.customAppDensity
import com.prof18.moneyflow.utils.customAppLocale
import com.prof18.moneyflow.utils.customAppThemeIsDark
import money_flow.shared.generated.resources.Res
import money_flow.shared.generated.resources.home_screen
import money_flow.shared.generated.resources.ic_cog_solid
import money_flow.shared.generated.resources.ic_home_solid
import money_flow.shared.generated.resources.settings_screen
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

private const val DEFAULT_ANIMATION_DURATION_MILLIS = 300

@Composable
fun MoneyFlowNavHost(modifier: Modifier = Modifier) {
    val backStack = rememberSerializable(serializer = SnapshotStateListSerializer(AppRoute.serializer())) {
        mutableStateListOf(HomeRoute)
    }
    val categoryState = remember { mutableStateOf<CategoryUIData?>(null) }

    CompositionLocalProvider(
        LocalAppLocale provides customAppLocale,
        LocalAppTheme provides customAppThemeIsDark,
        LocalAppDensity provides customAppDensity,
    ) {
        key(customAppLocale) {
            key(customAppThemeIsDark) {
                key(customAppDensity) {
                    Scaffold(
                        modifier = modifier,
                        contentWindowInsets = WindowInsets.safeDrawing.only(
                            WindowInsetsSides.Top + WindowInsetsSides.Horizontal,
                        ),
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
                            transitionSpec = {
                                slideInHorizontally(
                                    animationSpec = tween(DEFAULT_ANIMATION_DURATION_MILLIS),
                                    initialOffsetX = { it },
                                ) togetherWith slideOutHorizontally(
                                    animationSpec = tween(DEFAULT_ANIMATION_DURATION_MILLIS),
                                    targetOffsetX = { -it },
                                )
                            },
                            popTransitionSpec = {
                                slideInHorizontally(
                                    animationSpec = tween(DEFAULT_ANIMATION_DURATION_MILLIS),
                                    initialOffsetX = { -it },
                                ) togetherWith slideOutHorizontally(
                                    animationSpec = tween(DEFAULT_ANIMATION_DURATION_MILLIS),
                                    targetOffsetX = { it },
                                )
                            },
                            predictivePopTransitionSpec = { edge ->
                                slideInHorizontally(
                                    animationSpec = tween(DEFAULT_ANIMATION_DURATION_MILLIS),
                                    initialOffsetX = { -it },
                                ) togetherWith slideOutHorizontally(
                                    animationSpec = tween(DEFAULT_ANIMATION_DURATION_MILLIS),
                                    targetOffsetX = { it },
                                )
                            },
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun BottomBar(
    currentRoute: AppRoute?,
    onNavigate: (AppRoute) -> Unit,
) {
    if (currentRoute !is HomeRoute && currentRoute !is SettingsRoute) return
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
    ) {
        bottomNavigationItems.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(item.drawableRes),
                        contentDescription = null,
                        modifier = Modifier.size(22.dp),
                    )
                },
                label = { Text(stringResource(item.titleRes)) },
                selected = currentRoute::class == item.route::class,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f),
                    selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                    unselectedTextColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f),
                    indicatorColor = MaterialTheme.colorScheme.onPrimary,
                ),
                onClick = { if (currentRoute::class != item.route::class) onNavigate(item.route) },
            )
        }
    }
}

private data class BottomNavigationItem(
    val route: AppRoute,
    val titleRes: StringResource,
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
    categoryState: MutableState<CategoryUIData?>,
    paddingValues: PaddingValues,
) {
    entry<HomeRoute> {
        val homeViewModel = koinViewModel<HomeViewModel>()
        val homeModel by homeViewModel.homeModel.collectAsState()
        val hideSensitiveData by homeViewModel.hideSensitiveDataState.collectAsState()

        HomeScreen(
            homeModel = homeModel,
            hideSensitiveDataState = hideSensitiveData,
            navigateToAllTransactions = { backStack.add(AllTransactionsRoute) },
            paddingValues = paddingValues,
            navigateToAddTransaction = { backStack.add(AddTransactionRoute) },
            deleteTransaction = { id -> homeViewModel.deleteTransaction(id) },
            changeSensitiveDataVisibility = { homeViewModel.changeSensitiveDataVisibility(it) },
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
            stateFlow = viewModel.state,
            loadNextPage = viewModel::loadNextPage,
            navigateUp = { backStack.removeLastOrNull() },
        )
    }

    entry<SettingsRoute> {
        val viewModel = koinViewModel<SettingsViewModel>()
        val hideDataState by viewModel.hideSensitiveDataState.collectAsState()
        val biometricState by viewModel.biometricState.collectAsState()
        val biometricAvailabilityChecker: BiometricAvailabilityChecker = koinInject()

        SettingsScreen(
            biometricAvailabilityChecker = biometricAvailabilityChecker,
            biometricState = biometricState,
            onBiometricEnabled = viewModel::updateBiometricState,
            hideSensitiveDataState = hideDataState,
            onHideSensitiveDataEnabled = viewModel::updateHideSensitiveDataState,
            paddingValues = paddingValues,
        )
    }
}
