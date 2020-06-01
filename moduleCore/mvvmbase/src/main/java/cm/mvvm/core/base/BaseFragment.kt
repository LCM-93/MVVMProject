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
import cm.mvvm.core.utils.StatusBarUtils
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import java.lang.reflect.ParameterizedType

/**
 * ****************************************************************
 * Author: LiChenMing.Chaman
 * Date: 2019-06-24 17:14
 * Desc:
 * *****************************************************************
 */
abstract class BaseFragment<DB : ViewDataBinding, VM : BaseViewModel> : Fragment(), BaseVMFragment {

    lateinit var viewDataBinding: DB
    lateinit var viewModel: VM
    private var viewModelFactory: ViewModelProvider.NewInstanceFactory? = null
    val lifecycleScopeProvider: AndroidLifecycleScopeProvider by lazy {
        AndroidLifecycleScopeProvider.from(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (needEventBus()) registerEventBus()
        viewModel = viewModel()
        viewModel.lifecycleScopeProvider = AndroidLifecycleScopeProvider.from(this)
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


    override fun baseObserve() {
        viewModel.vmEvent.observe(this, Observer {
            handleVMEvent(it.getContentIfNotHandled())
        })
        viewModel.loadStatus.observe(this, Observer {
            handleLoadingStatus(it.getContentIfNotHandled())
        })
        viewModel.toastMsg.observe(this, Observer {
            it.getContentIfNotHandled()?.let { msg ->
                showToast(msg)
            }
        })
        viewModel.openPage.observe(this, Observer {
            it.getContentIfNotHandled()?.let { pair ->
                openPage(pair.first, pair.second)
            }
        })
        viewModel.openDialog.observe(this, Observer {
            it.getContentIfNotHandled()?.let { pair ->
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
            ViewModelProvider(activity!!).get(getVMClass())
        } else {
            ViewModelProvider(activity!!, viewModelFactory!!).get(getVMClass())
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


    override fun initLoadingView() {}
    override fun setListener() {}
    override fun observe() {}
    override fun openPage(page: String, param: Any?) {}
    override fun openDialog(dialog: String, param: Any?) {}
    override fun handleVMEvent(any: Any?) {}
    override fun handleLoadingStatus(loadingStatus: LoadingStatus?) {}
    override fun needEventBus(): Boolean = false

    override fun showToast(msg: String, duration: Int) {
        Toast.makeText(activity?.applicationContext, msg, duration).show()
    }

    override fun onDestroy() {
        if (needEventBus()) unRegisterEventBus()
        super.onDestroy()
        BaseApplication.refWatcher?.watch(this)
    }


    /************************************状态栏相关*****************************************/
    private fun setStatusBar() {
        if (useImmersiveStatusBar()) {
            if (fakeView() == null) {
                BarUtils.setStatusBarColor(activity!!, Color.TRANSPARENT)
            } else {
                BarUtils.setStatusBarColor(fakeView()!!, statusBarColor())
            }
        }
    }

    private fun setStatusBarMode() {
        if (useImmersiveStatusBar()) {
            StatusBarUtils.setStatusBarLightMode(activity!!, statusBarIsDarkMode())
        }
    }

    override fun useImmersiveStatusBar(): Boolean = false
    override fun statusBarColor(): Int = Color.WHITE
    override fun fakeView(): View? = null
    override fun statusBarIsDarkMode(): Boolean = true

    /************************************状态栏相关*****************************************/

}