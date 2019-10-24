package com.example.usermodule.ui.login

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.usermodule.R
import com.example.usermodule.databinding.ActivityLoginBinding
import com.lcm.modulebase.config.ARouterPath
import com.lcm.modulebase.plugins.view.PopupManager
import com.lcm.mvvm.base.BaseActivity

/**
 * ****************************************************************
 * Author: LiChenMing.Chaman
 * Date: 2019/10/24 16:42
 * Desc:
 * *****************************************************************
 */
@Route(path = ARouterPath.User.LOGIN)
class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>() {
    override fun initView(): Int = R.layout.activity_login

    override fun initData(savedInstanceState: Bundle?) {
        viewDataBinding.viewModel = viewModel


    }

    override fun handleVMEvent(any: Any?) {
        when (any) {
            1 -> showDialog()
        }
    }

    private fun showDialog() {
        PopupManager.showSimpleDialog(this, "登录成功！！${viewModel.loginData.value?.toString()}")
    }
}