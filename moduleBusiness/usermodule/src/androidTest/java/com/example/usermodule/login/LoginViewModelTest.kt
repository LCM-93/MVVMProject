package com.example.usermodule.login

import android.content.Intent
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ApplicationProvider
import androidx.test.rule.ActivityTestRule
import cm.module.core.data.entity.LoginData
import com.example.usermodule.ui.login.LoginActivity
import com.example.usermodule.ui.login.LoginViewModel
import com.example.usermodule.utils.ImmediateSchedulerRule
import com.example.usermodule.utils.LiveDataTestUtil
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import io.reactivex.Observable
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.robolectric.RobolectricTestRunner

/**
 * ****************************************************************
 * Author: LiChenMing.Chaman
 * Date: 2020/6/4 14:40
 * Desc:
 * *****************************************************************
 */
@RunWith(RobolectricTestRunner::class)
class LoginViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    val immediateSchedulerRule = ImmediateSchedulerRule()

    @get:Rule
    var loginActivityRule = ActivityTestRule(LoginActivity::class.java, false, false)


    private lateinit var loginViewModel: LoginViewModel


    @Before
    fun setupLoginViewModel() {
        launchNewTaskActivity()
        loginViewModel = LoginViewModel()
        loginViewModel.lifecycleScopeProvider =
            AndroidLifecycleScopeProvider.from(loginActivityRule.activity, Lifecycle.Event.ON_DESTROY)

        loginViewModel.userName.value = "啦啦啦哈哈哈"
        loginViewModel.password.value = "123456"

    }


    @Test
    fun login() {
        loginViewModel.login()
//        assertThat(LiveDataTestUtil.getValue(loginViewModel.loadStatus), MatcherLoadingStatus(LoadingStatus.Status.LOADING))
    }


    /**
     * @param taskId is null if used to add a new task, otherwise it edits the task.
     */
    private fun launchNewTaskActivity() {
        val intent = Intent(
            ApplicationProvider.getApplicationContext(),
            LoginActivity::class.java
        )
        loginActivityRule.launchActivity(intent)
    }


}