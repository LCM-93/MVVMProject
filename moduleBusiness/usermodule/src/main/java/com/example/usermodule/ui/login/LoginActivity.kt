package com.example.usermodule.ui.login

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.usermodule.R
import com.example.usermodule.databinding.ActivityLoginBinding
import cm.module.core.base.AppBaseActivity
import cm.module.core.config.ARouterPath
import cm.module.core.plugins.view.PopupManager
import com.example.usermodule.config.EventConfig

/**
 * ****************************************************************
 * Author: LiChenMing.Chaman
 * Date: 2019/10/24 16:42
 * Desc:
 * *****************************************************************
 */
@Route(path = ARouterPath.User.LOGIN)
class LoginActivity : AppBaseActivity<ActivityLoginBinding, LoginViewModel>() {

    override fun layoutId(): Int = R.layout.activity_login

    override fun initView() {
        viewDataBinding.viewModel = viewModel
    }

    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun openDialog(dialog: String, param: Any?) {
        when (dialog) {
            EventConfig.LOGIN_SUCCESS_DIALOG -> showDialog()
        }
    }

    private fun showDialog() {
        PopupManager.showSimpleDialog(this, "登录成功！！${viewModel.loginData.value?.toString()}")
    }
}