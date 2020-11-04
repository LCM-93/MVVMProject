package cm.mvvm.core.base

import androidx.lifecycle.*
import cm.mvvm.core.base.event.LoadingStatus
import cm.mvvm.core.base.event.VMEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

/**
 * ****************************************************************
 * Author: LiChenMing.Chaman
 * Date: 2019-06-24 17:06
 * Desc:
 * *****************************************************************
 */
abstract class BaseViewModel : ViewModel() {
    var vmEvent: MutableLiveData<VMEvent<Any>> = MutableLiveData()
    var finishActivityEvent: MutableLiveData<VMEvent<Any?>> = MutableLiveData()
    var loadStatus: MutableLiveData<VMEvent<LoadingStatus>> = MutableLiveData()
    var toastMsg: MutableLiveData<VMEvent<String>> = MutableLiveData()
    var openPage: MutableLiveData<VMEvent<Pair<String, Any?>>> = MutableLiveData()
    var openDialog: MutableLiveData<VMEvent<Pair<String, Any?>>> = MutableLiveData()

    fun finishActivity(result: Any? = null) {
        finishActivityEvent.value = VMEvent(result)
    }

    fun openPage(page: String, param: Any? = null) {
        openPage.value = VMEvent(Pair(page, param))
    }

    fun openDialog(dialog: String, param: Any? = null) {
        openDialog.value = VMEvent(Pair(dialog, param))
    }

    fun showToast(msg: String) {
        toastMsg.value = VMEvent(msg)
    }

    fun showLoading(msg: String? = "", tag: Any? = "") {
        loadStatus.value = VMEvent(LoadingStatus(LoadingStatus.Status.LOADING, msg, tag))
    }

    fun hideLoading(success: Boolean, msg: String? = "", tag: Any? = "") {
        loadStatus.value = VMEvent(
            LoadingStatus(
                if (success) LoadingStatus.Status.LOAD_SUCCESS else LoadingStatus.Status.LOAD_FAILED,
                msg,
                tag
            )
        )
    }

    fun launchUI(
        block: suspend CoroutineScope.() -> Unit,
        error: suspend CoroutineScope.(Exception) -> Unit = {},
        complete: suspend CoroutineScope.() -> Unit = {},
        showLoading: Boolean = true,
        showErrorToast: Boolean = true
    ) {
        viewModelScope.launch {
            handleException({
                if (showLoading) showLoading()
                block()
                if (showLoading) hideLoading(true)
            }, {
                if (showErrorToast) showToast(it.message ?: "未知错误")
                error(it)
                if (showLoading) hideLoading(false)
            }, { complete() })
        }
    }


    private suspend fun handleException(
        block: suspend CoroutineScope.() -> Unit,
        error: suspend CoroutineScope.(Exception) -> Unit,
        complete: suspend CoroutineScope.() -> Unit
    ) {
        coroutineScope {
            try {
                block()
            } catch (ex: Exception) {
                error(ex)
            } finally {
                complete()
            }
        }
    }

    fun isEmpty(value: MutableLiveData<*>?): Boolean {
        if (value == null || value.value == null) return true
        if (value.value is String) {
            return (value.value as String).isEmpty()
        }
        return false
    }

    fun checkIsEmpty(value: MutableLiveData<*>?, errMsg: String? = "参数为空"): Boolean {
        if (isEmpty(value)) throw Exception(errMsg)
        return false
    }
}