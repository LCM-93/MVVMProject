package com.example.usermodule.ui.login

import androidx.lifecycle.MutableLiveData
import com.lcm.modulebase.data.entity.LoginData
import com.lcm.modulebase.data.remote.UserApi
import com.lcm.mvvm.base.BaseViewModel
import com.lcm.mvvm.base.event.VMEvent
import com.uber.autodispose.autoDisposable
import io.reactivex.Observable
import java.lang.Exception

/**
 * ****************************************************************
 * Author: LiChenMing.Chaman
 * Date: 2019/10/24 16:21
 * Desc:
 * *****************************************************************
 */
class LoginViewModel : BaseViewModel() {

    var userName: MutableLiveData<String> = MutableLiveData()
    var password: MutableLiveData<String> = MutableLiveData()
    var loginData:MutableLiveData<LoginData> = MutableLiveData()


    fun login() {
        showLoading()
        Observable.just(true)
                .flatMap {
                    if (userName.value == null || userName.value!!.isEmpty()) {
                        Observable.error(Exception("用户名不能为空"))
                    } else if (password.value == null || password.value!!.isEmpty()) {
                        Observable.error(Exception("密码不能为空"))
                    } else {
                        Observable.just(true)
                    }
                }
                .flatMap { UserApi.instance.login(userName.value, password.value) }
                .doFinally { hideLoading(true) }
                .autoDisposable(lifecycleScopeProvider)
                .subscribe({
                    showToast("登录成功！！")
                    loginData.value = it
                    vmEvent.value = VMEvent(1)
                }, {
                    showToast(it.message ?: "未知错误")
                })
    }
}