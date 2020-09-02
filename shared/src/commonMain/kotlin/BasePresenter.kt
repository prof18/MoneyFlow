abstract class BasePresenter<T> {

    protected var view: T? = null

    fun attachView(view: T) {
        this.view = view
        onViewAttached(view)
    }

    protected open fun onViewAttached(view: T) {}

    fun detachView() {
        view = null
        onViewDetached()
    }

    protected open fun onViewDetached() {}

}