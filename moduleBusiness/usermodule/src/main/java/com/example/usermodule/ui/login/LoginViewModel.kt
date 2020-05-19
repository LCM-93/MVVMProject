package com.example.usermodule.ui.login

import androidx.lifecycle.MutableLiveData
import cm.module.core.data.entity.LoginData
import cm.module.core.data.remote.UserApi
import cm.mvvm.core.base.BaseViewModel
import com.example.usermodule.config.EventConfig
import com.uber.autodispose.autoDispose
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
    var loginData: MutableLiveData<LoginData> = MutableLiveData()


    fun login() {
        showLoading()
        Observable.just(true)
            .flatMap {
                when {
                    isEmpty(userName) -> Observable.error(Exception("用户名不能为空"))
                    isEmpty(password) -> Observable.error(Exception("密码不能为空"))
                    else -> Observable.just(true)
                }
            }
            .flatMap { UserApi.instance.login(userName.value, password.value) }
            .doFinally { hideLoading(true) }
            .autoDispose(lifecycleScopeProvider)
            .subscribe({
                showToast("登录成功！！")
                loginData.value = it
                openDialog(EventConfig.LOGIN_SUCCESS_DIALOG)
            }, {
                showToast(it.message ?: "未知错误")
            })
    }
}