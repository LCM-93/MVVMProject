package com.example.usermodule.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cm.module.core.config.ARouterPath
import cm.module.core.data.entity.LoginData
import cm.module.core.data.remote.UserApi
import cm.mvvm.core.base.BaseViewModel
import com.alibaba.android.arouter.launcher.ARouter
import kotlinx.coroutines.launch
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


//    fun login() {
//        showLoading()
//        Observable.just(true)
//            .flatMap {
//                if (userName.value == null || userName.value!!.isEmpty()) {
//                    Observable.error(Exception("用户名不能为空"))
//                } else if (password.value == null || password.value!!.isEmpty()) {
//                    Observable.error(Exception("密码不能为空"))
//                } else {
//                    Observable.just(true)
//                }
//            }
//            .flatMap { UserApi.instance.login(userName.value, password.value) }
//            .doFinally { hideLoading(true) }
//            .autoDispose(lifecycleScopeProvider)
//            .subscribe({
//                showToast("登录成功！！")
//                loginData.value = it
//                vmEvent.value = VMEvent(1)
//            }, {
//                showToast(it.message ?: "未知错误")
//            })
//    }

    fun login() {
        viewModelScope.launch {
            try {
                showLoading()
                checkIsEmpty(userName, "用户名不能为空")
                checkIsEmpty(password, "密码不能为空")
                val data = UserApi.instance.login(userName.value, password.value)
                loginData.value = data
                showToast("登录成功")
                hideLoading(true)
            } catch (ex: Exception) {
                showToast(ex.message ?: "未知错误")
                hideLoading(false)
            }
        }
    }

    fun clickRegister() {
        ARouter.getInstance().build(ARouterPath.User.REGISTER).navigation()
    }
}