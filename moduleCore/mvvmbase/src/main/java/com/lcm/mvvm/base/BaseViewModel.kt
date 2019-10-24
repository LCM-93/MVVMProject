package com.lcm.mvvm.base

import androidx.lifecycle.*
import com.lcm.mvvm.base.event.LoadingStatus
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.lcm.mvvm.base.event.VMEvent

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

    fun finishActivity(result: Any? = null) {
        finishActivityEvent.value = VMEvent(result)
    }

    fun showToast(msg: String) {
        toastMsg.value = VMEvent(msg)
    }

    fun showLoading(msg: String? = "", tag: Any? = "") {
        loadStatus.value = VMEvent(LoadingStatus(LoadingStatus.Status.LOADING, msg, tag))
    }

    fun hideLoading(success: Boolean, msg: String? = "", tag: Any? = "") {
        loadStatus.value = VMEvent(LoadingStatus(if (success) LoadingStatus.Status.LOAD_SUCCESS else LoadingStatus.Status.LOAD_FAILED, msg, tag))
    }

}