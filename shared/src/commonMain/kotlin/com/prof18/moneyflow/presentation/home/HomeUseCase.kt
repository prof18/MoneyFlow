package com.prof18.moneyflow.presentation.home

import com.prof18.moneyflow.domain.entities.MoneyFlowError
import com.prof18.moneyflow.domain.entities.MoneyFlowResult
import com.prof18.moneyflow.domain.repository.MoneyRepository
import com.prof18.moneyflow.domain.repository.SettingsRepository
import com.prof18.moneyflow.presentation.MoneyFlowErrorMapper
import com.prof18.moneyflow.utils.logError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlin.native.HiddenFromObjC
import kotlin.native.ObjCName

@ObjCName("_HomeUseCase")
class HomeUseCase(
    private val moneyRepository: MoneyRepository,
    private val settingsRepository: SettingsRepository,
    private val errorMapper: MoneyFlowErrorMapper,
) {

    @HiddenFromObjC
    val hideSensibleDataState: StateFlow<Boolean> = settingsRepository.hideSensibleDataState

    @HiddenFromObjC
    fun observeHomeModel(): Flow<HomeModel> =
        moneyRepository.getMoneySummary().map {
            HomeModel.HomeState(
                balanceRecap = it.balanceRecap,
                latestTransactions = it.latestTransactions,
            )
        }

    @HiddenFromObjC
    fun toggleHideSensitiveData(status: Boolean) {
        settingsRepository.setHideSensitiveData(status)
    }

    @HiddenFromObjC
    suspend fun deleteTransaction(transactionId: Long): MoneyFlowResult<Unit> {
        return try {
            moneyRepository.deleteTransaction(transactionId)
            MoneyFlowResult.Success(Unit)
        } catch (throwable: Throwable) {
            val error = MoneyFlowError.DeleteTransaction(throwable)
            throwable.logError(error)
            val errorMessage = errorMapper.getUIErrorMessage(error)
            MoneyFlowResult.Error(errorMessage)
        }
    }
}
