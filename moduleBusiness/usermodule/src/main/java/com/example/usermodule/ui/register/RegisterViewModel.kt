package com.example.usermodule.ui.register

import androidx.lifecycle.MutableLiveData
import cm.module.core.data.remote.UserApi
import cm.mvvm.core.base.BaseViewModel

/**
 * ****************************************************************
 * Author: LiChenMing.Chaman
 * Date: 2020/5/18 16:41
 * Desc:
 * *****************************************************************
 */
class RegisterViewModel : BaseViewModel() {

    var userName: MutableLiveData<String> = MutableLiveData()
    var password: MutableLiveData<String> = MutableLiveData()
    var repassword: MutableLiveData<String> = MutableLiveData()


    fun register() {
        launchUI({
            checkIsEmpty(userName, "用户名不能为空")
            checkIsEmpty(password, "密码不能为空")
            checkIsEmpty(repassword, "密码不能为空")
            val data = UserApi.instance.register(userName.value, password.value, repassword.value)
            showToast("注册成功！！")
            finishActivity()
        }, {
            showToast(it.message ?: "未知错误")
        })
    }
}