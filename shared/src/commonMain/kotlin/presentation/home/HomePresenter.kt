package presentation.home

import BasePresenter
import domain.repository.MoneyRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

class HomePresenter (
    private val moneyRepository: MoneyRepository,
    private val coroutineScope: CoroutineScope = MainScope() // we provide a default value for iOs.
): BasePresenter<HomeView>() {

    override fun onViewAttached(view: HomeView) {
        super.onViewAttached(view)
        // TODO: start some action?
    }



    override fun onViewDetached() {
        super.onViewDetached()
        coroutineScope.cancel()
    }

}

