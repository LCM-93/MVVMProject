package cm.mvvm.core.base

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
import cm.mvvm.core.base.base.BaseVMFragment
import com.blankj.utilcode.util.BarUtils
import cm.mvvm.core.base.event.LoadingStatus
import cm.mvvm.core.base.lazy.LazyFragment
import cm.mvvm.core.utils.StatusBarUtils
import kotlinx.coroutines.*
import java.lang.reflect.ParameterizedType

/**
 * ****************************************************************
 * Author: LiChenMing.Chaman
 * Date: 2019-06-24 17:14
 * Desc:
 * *****************************************************************
 */
abstract class BaseFragment<DB : ViewDataBinding, VM : BaseViewModel> : LazyFragment(),
    BaseVMFragment {

    lateinit var viewDataBinding: DB
    lateinit var viewModel: VM
    private var viewModelFactory: ViewModelProvider.NewInstanceFactory? = null
    private val mainScope by lazy { MainScope() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (needEventBus()) registerEventBus()
        viewModel = viewModel()
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

    override fun lazyInit() {
        setStatusBar()
        setStatusBarMode()
        initView()
        initLoadingView()
        setListener()
        baseObserve()
        observe()
        initData()
    }


    override fun baseObserve() {
        viewModel.vmEvent.observe(viewLifecycleOwner, Observer {
            handleVMEvent(it.getContentIfNotHandled())
        })
        viewModel.loadStatus.observe(viewLifecycleOwner, Observer {
            handleLoadingStatus(it.getContentIfNotHandled())
        })
        viewModel.toastMsg.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { msg ->
                showToast(msg)
            }
        })
        viewModel.openPage.observe(viewLifecycleOwner, Observer {
            val pair = it.getContentIfNotHandled()
            if (pair != null) {
                openPage(pair.first, pair.second)
            }
        })

        viewModel.openDialog.observe(viewLifecycleOwner, Observer {
            val pair = it.getContentIfNotHandled()
            if (pair != null) {
                openDialog(pair.first, pair.second)
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
            ViewModelProvider(activity!!).let {
                if (viewModelTag() == null) {
                    it.get(getVMClass())
                } else {
                    it.get("BaseFragment : $tag : ${viewModelTag()}", getVMClass())
                }
            }
        } else {
            ViewModelProvider(activity!!, viewModelFactory!!).let {
                if (viewModelTag() == null) {
                    it.get(getVMClass())
                } else {
                    it.get("BaseFragment : $tag : ${viewModelTag()}", getVMClass())
                }
            }
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


    override fun showToast(msg: String, duration: Int) {
        Toast.makeText(activity?.applicationContext, msg, duration).show()
    }

    override fun needEventBus(): Boolean = false
    override fun initLoadingView() {}
    override fun setListener() {}
    override fun observe() {}
    override fun openPage(page: String, param: Any?) {}
    override fun openDialog(dialog: String, param: Any?) {}
    override fun handleVMEvent(any: Any?) {}
    override fun handleLoadingStatus(loadingStatus: LoadingStatus?) {}
    override fun viewModelTag(): String? = null

    override fun onDestroy() {
        if (needEventBus()) unRegisterEventBus()
        mainScope.cancel()
        super.onDestroy()
        BaseApplication.refWatcher?.watch(this)
    }


    /**************************状态栏相关*****************************/
    /**
     * 设置状态栏
     */
    private fun setStatusBar() {
        if (useImmersiveStatusBar()) {
            if (fakeView() == null) {
                BarUtils.setStatusBarColor(activity!!, Color.TRANSPARENT)
            } else {
                BarUtils.setStatusBarColor(fakeView()!!, statusBarColor())
            }
        }
    }

    /**
     * 设置状态栏字体颜色
     */
    private fun setStatusBarMode() {
        if (useImmersiveStatusBar()) {
            StatusBarUtils.setStatusBarLightMode(activity!!, statusBarIsDarkMode())
        }
    }

    override fun useImmersiveStatusBar(): Boolean = false
    override fun statusBarColor(): Int = Color.WHITE
    override fun fakeView(): View? = null
    override fun statusBarIsDarkMode(): Boolean = true

    /**************************状态栏相关*****************************/


    /**************************协程相关******************************/

    fun launchUI(
        block: suspend CoroutineScope.() -> Unit,
        error: suspend CoroutineScope.(Exception) -> Unit = {},
        complete: suspend CoroutineScope.() -> Unit = {},
        showLoading: Boolean = false
    ) {
        mainScope.launch {
            handleException({
                if (showLoading) viewModel.showLoading()
                block()
                if (showLoading) viewModel.hideLoading(true)
            }, {
                error(it)
                if (showLoading) viewModel.hideLoading(false)
            }, { complete() })
        }
    }

    private suspend fun handleException(
        block: suspend CoroutineScope.() -> Unit,
        error: suspend CoroutineScope.(Exception) -> Unit,
        complete: suspend CoroutineScope.() -> Unit
    ) {
        coroutineScope {
            try {
                block()
            } catch (ex: Exception) {
                error(ex)
            } finally {
                complete()
            }
        }
    }
    /**************************协程相关******************************/
}