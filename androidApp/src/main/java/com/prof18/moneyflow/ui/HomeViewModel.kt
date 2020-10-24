package com.prof18.moneyflow.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.db.DatabaseSource
import di.recreateDatabaseScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.getKoin
import presentation.home.HomeModel
import presentation.home.HomeUseCase
import presentation.home.HomeUseCaseImpl

class HomeViewModel(
    private var useCase: HomeUseCase
) : ViewModel() {

    // move to constructor when introducing koin
//    private val useCase: HomeUseCase = HomeUseCaseImpl()

    private val _homeLiveData = MutableLiveData<HomeModel>()
    val homeLiveData: LiveData<HomeModel>
        get() = _homeLiveData

    init {
        observeHomeModel()
        viewModelScope.launch {
            useCase.computeHomeDataSuspendable()
        }
    }

    private fun observeHomeModel() {
        viewModelScope.launch {
            useCase.observeHomeModel().collect {
                _homeLiveData.postValue(it)
            }
        }
    }

    fun refreshData() {
        viewModelScope.launch {

            getKoin().recreateDatabaseScope()
            val driverScope = getKoin().getOrCreateScope<DatabaseSource>("databaseScope")
            useCase = driverScope.get()

            useCase.refreshData()
        }
    }

}