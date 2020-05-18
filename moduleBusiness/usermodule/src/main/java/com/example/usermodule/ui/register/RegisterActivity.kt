package com.example.usermodule.ui.register

import android.os.Bundle
import cm.module.core.base.AppBaseActivity
import cm.module.core.config.ARouterPath
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.usermodule.R
import com.example.usermodule.databinding.ActivityRegisterBinding

/**
 * ****************************************************************
 * Author: LiChenMing.Chaman
 * Date: 2020/5/18 16:45
 * Desc:
 * *****************************************************************
 */
@Route(path = ARouterPath.User.REGISTER)
class RegisterActivity:AppBaseActivity<ActivityRegisterBinding,RegisterViewModel>() {
    override fun layoutId(): Int  = R.layout.activity_register

    override fun initView() {
    }

    override fun initData(savedInstanceState: Bundle?) {
        viewDataBinding.viewModel = viewModel
    }
}