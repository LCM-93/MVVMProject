package com.lcm.mvvm.base

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
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
 * Date: 2019-06-24 17:14
 * Desc:
 * *****************************************************************
 */
abstract class BaseFragment<DB : ViewDataBinding, VM : BaseViewModel> : Fragment() {

    lateinit var viewDataBinding: DB
    lateinit var viewModel: VM
    private var viewModelFactory: ViewModelProvider.NewInstanceFactory? = null
    val lifecycleScopeProvider: AndroidLifecycleScopeProvider by lazy {
        AndroidLifecycleScopeProvider.from(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RxBus.get().register(this)
        viewModel = viewModel()
        viewModel.lifecycleScopeProvider = AndroidLifecycleScopeProvider.from(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        RxBus.get().unregister(this)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater, layoutId(), container, false)
        viewDataBinding.lifecycleOwner = this
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setStatusBar()
        setStatusBarMode()
        initView()
        initLoadingView()
        setListener()
        baseObserve()
        observe()
        initData(savedInstanceState)
    }


    abstract fun layoutId(): Int
    abstract fun initView()
    open fun initLoadingView(){}
    abstract fun initData(savedInstanceState: Bundle?)
    open fun setListener() {}
    open fun observe() {}
    /**
     * 处理ViewModel中的事件
     */
    open fun handleEvent(any: Any?) {}

    /**
     * 处理加载事件
     */
    open fun handleLoadingStatus(loadingStatus: LoadingStatus?) {}

    private fun baseObserve() {
        viewModel.vmEvent.observe(this, Observer {
            handleEvent(it.getContentIfNotHandled())
        })
        viewModel.loadStatus.observe(this, Observer {
            handleEvent(it.getContentIfNotHandled())
        })
        viewModel.toastMsg.observe(this, Observer {
            it.getContentIfNotHandled()?.let { msg ->
                showToast(msg)
            }
        })
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
            ViewModelProviders.of(activity!!).get(getVMClass())
        } else {
            ViewModelProviders.of(activity!!, viewModelFactory).get(getVMClass())
        }
        return viewModel
    }

    /**
     * 获取ViewModel的class类型
     */
    @Suppress("UNCHECKED_CAST")
    private fun getVMClass(): Class<VM> {
        val type = this.javaClass.genericSuperclass as ParameterizedType
        return type.actualTypeArguments[1] as Class<VM>//<T>
    }

    open fun useImmersiveStatusBar(): Boolean = false

    open fun statusBarColor(): Int = Color.WHITE

    open fun fakeView(): View? = null

    private fun setStatusBar() {
        if (useImmersiveStatusBar()) {
            if (fakeView() == null) {
                BarUtils.setStatusBarColor(activity!!, Color.TRANSPARENT)
            } else {
                BarUtils.setStatusBarColor(fakeView()!!, statusBarColor())
            }
        }
    }

    open fun statusBarIsDarkMode(): Boolean = true

    private fun setStatusBarMode() {
        if (useImmersiveStatusBar()) {
            StatusBarUtils.setStatusBarLightMode(activity!!, statusBarIsDarkMode())
        }
    }


    fun showToast(msg: String) {
        Toast.makeText(activity?.applicationContext, msg, Toast.LENGTH_SHORT).show()
    }

}