package cm.mvvm.core.base

import androidx.lifecycle.*
import cm.mvvm.core.base.event.LoadingStatus
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import cm.mvvm.core.base.event.VMEvent

/**
 * ****************************************************************
 * Author: LiChenMing.Chaman
 * Date: 2019-06-24 17:06
 * Desc:
 * *****************************************************************
 */
abstract class BaseViewModel : ViewModel() {
    lateinit var lifecycleScopeProvider: AndroidLifecycleScopeProvider
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

    fun clearUiStatus(){

    }

}