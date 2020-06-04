package com.example.usermodule.login

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import cm.mvvm.core.base.event.LoadingStatus
import com.example.usermodule.ui.login.LoginViewModel
import com.example.usermodule.utils.LiveDataTestUtil
import com.example.usermodule.utils.MatcherLoadingStatus
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.*

/**
 * ****************************************************************
 * Author: LiChenMing.Chaman
 * Date: 2020/6/4 14:40
 * Desc:
 * *****************************************************************
 */
class LoginViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var loginViewModel: LoginViewModel

    @Before
    fun setupLoginViewModel() {
        loginViewModel = LoginViewModel()

        loginViewModel.userName.value = "啦啦啦哈哈哈"
        loginViewModel.password.value = "123456"
    }


    @Test
    fun login() {
        loginViewModel.login()
        assertThat(LiveDataTestUtil.getValue(loginViewModel.loadStatus), MatcherLoadingStatus(LoadingStatus.Status.LOADING))

    }


}