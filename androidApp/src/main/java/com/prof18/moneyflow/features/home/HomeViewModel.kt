package com.prof18.moneyflow.features.home

import androidx.lifecycle.*
import data.db.DatabaseSource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.getKoin
import presentation.home.HomeModel
import presentation.home.HomeUseCase

class HomeViewModel(
    private var useCase: HomeUseCase
) : ViewModel() {

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
}

class HomeViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(getKoin().get()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}