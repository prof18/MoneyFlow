package com.prof18.moneyflow.features.home

import androidx.lifecycle.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.getKoin
import com.prof18.moneyflow.presentation.home.HomeModel
import com.prof18.moneyflow.presentation.home.HomeUseCase

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

    fun deleteTransaction(id: Long) {
        useCase.deleteTransaction(id)
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