package cm.mvvm.core.base.rxjava

import cm.mvvm.core.base.BaseViewModel
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import retrofit2.HttpException
import java.io.IOException

/**
 * *****************************************************************
 * @author: LiChenMing
 * @date: 2022/9/19 15:07
 * @description: BaseObserver.kt
 * *****************************************************************
 */
class BaseObserver<T> : Observer<T> {
    private var viewModel: BaseViewModel? = null
    private var isShowLoading: Boolean = true
    private var isShowToast: Boolean = true
    private var callback: IObserverCallback<T>? = null
    private var disposable: Disposable? = null

    constructor(
        viewModel: BaseViewModel?,
        isShowLoading: Boolean = true,
        isShowToast: Boolean = true,
        callback: IObserverCallback<T>? = null
    ) {
        this.viewModel = viewModel
        this.isShowLoading = isShowLoading
        this.isShowToast = isShowToast
        this.callback = callback
    }

    constructor(viewModel: BaseViewModel?, callback: IObserverCallback<T>? = null) : this(
        viewModel,
        true,
        true,
        callback
    )


    override fun onSubscribe(d: Disposable) {
        disposable = d
        if (isShowLoading) {
            viewModel?.showLoading()
        }
    }

    override fun onNext(t: T) {
        if (isShowLoading) {
            viewModel?.hideLoading(true)
        }
        callback?.result(t)
    }

    override fun onError(throwable: Throwable) {
        if (isShowLoading) {
            viewModel?.hideLoading(false)
        }
        var errorMsg: String = ""
        if (throwable is HttpException) {
            try {
                errorMsg = throwable.response()?.errorBody()?.string() ?: ""
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } else {
            errorMsg = throwable.message ?: "未知错误"
        }
        if (isShowToast) {
            viewModel?.showToast(errorMsg)
        }
        callback?.error(throwable, errorMsg)
        disposable?.dispose()
    }

    override fun onComplete() {
        disposable?.dispose()
    }
}