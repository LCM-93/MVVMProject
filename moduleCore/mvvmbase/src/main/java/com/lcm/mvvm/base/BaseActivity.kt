package com.lcm.mvvm.base

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.blankj.utilcode.util.BarUtils
import com.lcm.mvvm.base.event.LoadingStatus
import com.lcm.mvvm.utils.RxBus
import com.lcm.mvvm.utils.StatusBarUtils
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import java.lang.reflect.ParameterizedType


/**
 * ****************************************************************
 * Author: LiChenMing.Chaman
 * Date: 2019-06-24 16:06
 * Desc:
 * *****************************************************************
 */
abstract class BaseActivity<DB : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity() {

    lateinit var viewDataBinding: DB
    lateinit var viewModel: VM
    private var viewModelFactory: ViewModelProvider.NewInstanceFactory? = null
    val lifecycleScopeProvider: AndroidLifecycleScopeProvider by lazy { AndroidLifecycleScopeProvider.from(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RxBus.get().register(this)
        viewDataBinding = DataBindingUtil.setContentView(this, layoutId())
        viewModel = viewModel()
        viewDataBinding.lifecycleOwner = this
        viewModel.lifecycleScopeProvider = AndroidLifecycleScopeProvider.from(this)
        setStatusBar()
        setStatusBarMode()
        initView()
        initLoadingView()
        setListener()
        baseObserve()
        observe()
        initData(savedInstanceState)
    }

    private fun baseObserve() {
        viewModel.vmEvent.observe(this, Observer {
            handleVMEvent(it.getContentIfNotHandled())
        })
        viewModel.finishActivityEvent.observe(this, Observer {
            finishActivity(it.getContentIfNotHandled())
        })
        viewModel.loadStatus.observe(this, Observer {
            handleLoadingStatus(it.getContentIfNotHandled())
        })
        viewModel.toastMsg.observe(this, Observer {
            it.getContentIfNotHandled()?.let {msg->
                showToast(msg)
            }
        })
        viewModel.openPage.observe(this, Observer {
            val pair = it.getContentIfNotHandled()
            if(pair != null) {
                openPage(pair.first,pair.second)
            }
        })
    }


    abstract fun layoutId(): Int
    abstract fun initView()
    open fun initLoadingView(){}
    open fun setListener() {}
    open fun observe() {}
    abstract fun initData(savedInstanceState: Bundle?)
    open fun openPage(page:String,param:Any?){}


    /**
     * 处理ViewModel中的事件
     */
    open fun handleVMEvent(any: Any?) {}

    /**
     * 处理加载事件
     */
    open fun handleLoadingStatus(loadingStatus: LoadingStatus?) {}

    /**
     * 关闭Activity
     * 若关闭Activity时需要传递参数到前一个页面，需要重写该方法
     */
    open fun finishActivity(result: Any?) {
        finish()
    }

    /**
     *设置ViewModelFactory
     */
    fun createViewModelFactory() {
        viewModelFactory = null
    }

    /**
     * 获取ViewModel
     */
    private fun viewModel(): VM {
        viewModel = if (viewModelFactory == null) {
            ViewModelProviders.of(this).get(getVMClass())
        } else {
            ViewModelProviders.of(this, viewModelFactory).get(getVMClass())
        }
        return viewModel
    }


    /**
     * 获取ViewModel的class类型
     */
    @Suppress("UNCHECKED_CAST")
    private fun getVMClass(): Class<VM> {
        val type: ParameterizedType = this.javaClass.genericSuperclass as ParameterizedType
        return type.actualTypeArguments[1] as Class<VM>
    }

    /**
     * 是否使用沉浸式状态栏
     */
    open fun useImmersiveStatusBar(): Boolean = true

    /**
     * 状态栏背景色
     */
    open fun statusBarColor(): Int = Color.WHITE

    /**
     * 当状态栏不是纯色时，可在布局中添加View，通过该方法设置该View的高度与状态栏一致
     */
    open fun fakeView(): View? = null

    /**
     * 设置状态栏
     */
    private fun setStatusBar() {
        if (useImmersiveStatusBar()) {
            if (fakeView() == null) {
                BarUtils.setStatusBarColor(this, Color.TRANSPARENT)
            } else {
                BarUtils.setStatusBarColor(fakeView()!!, statusBarColor())
            }
        }
    }

    /**
     * 设置状态栏的字体颜色 true 黑色  false 白色
     */
    open fun statusBarIsDarkMode(): Boolean = true

    /**
     * 设置状态栏颜色
     */
    private fun setStatusBarMode() {
        if (useImmersiveStatusBar()) {
            StatusBarUtils.setStatusBarLightMode(this, statusBarIsDarkMode())
        }
    }


    fun showToast(msg: String) {
        Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
    }


    override fun onDestroy() {
        super.onDestroy()
        RxBus.get().unregister(this)
    }
}