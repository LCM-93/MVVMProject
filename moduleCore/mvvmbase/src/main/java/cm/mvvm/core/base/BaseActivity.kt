package cm.mvvm.core.base

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import cm.mvvm.core.base.base.BaseVMActivity
import cm.mvvm.core.base.event.LoadingStatus
import cm.mvvm.core.utils.StatusBarUtils
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.SizeUtils
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import java.lang.reflect.ParameterizedType


/**
 * ****************************************************************
 * Author: LiChenMing.Chaman
 * Date: 2019-06-24 16:06
 * Desc:
 * *****************************************************************
 */
abstract class BaseActivity<DB : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity(),
    BaseVMActivity {

    lateinit var viewDataBinding: DB
    lateinit var viewModel: VM
    private var viewModelFactory: ViewModelProvider.NewInstanceFactory? = null
    val lifecycleScopeProvider: AndroidLifecycleScopeProvider by lazy {
        AndroidLifecycleScopeProvider.from(
            this, Lifecycle.Event.ON_DESTROY
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (needEventBus()) registerEventBus()
        viewDataBinding = DataBindingUtil.setContentView(this, layoutId())
        viewModel = viewModel()
        viewDataBinding.lifecycleOwner = this
        viewModel.lifecycleScopeProvider =
            AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)
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
        viewModel.finishActivityEvent.observe(this, Observer {
            finishActivity(it.getContentIfNotHandled())
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
            ViewModelProvider(this).get(getVMClass())
        } else {
            ViewModelProvider(this, viewModelFactory!!).get(getVMClass())
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


    override fun initLoadingView() {}
    override fun setListener() {}
    override fun observe() {}
    override fun openPage(page: String, param: Any?) {}
    override fun openDialog(dialog: String, param: Any?) {}
    override fun handleVMEvent(any: Any?) {}
    override fun handleLoadingStatus(loadingStatus: LoadingStatus?) {}
    override fun needEventBus(): Boolean = false
    override fun finishActivity(result: Any?) {
        finish()
    }

    override fun showToast(msg: String, duration: Int) {
        Toast.makeText(applicationContext, msg, duration).show()
    }

    override fun onDestroy() {
        if (needEventBus()) unRegisterEventBus()
        super.onDestroy()
    }

    /************************************状态栏相关*****************************************/
    /**
     * 设置状态栏
     */
    private fun setStatusBar() {
        if (useImmersiveStatusBar()) {
            if (fakeView() == null) {
                BarUtils.setStatusBarColor(this, Color.TRANSPARENT)
            } else {
                if (BarUtils.getStatusBarHeight() > SizeUtils.dp2px(20f)) {
                    val layoutParams = fakeView()!!.layoutParams
                    layoutParams.height = BarUtils.getStatusBarHeight()
                    fakeView()!!.layoutParams = layoutParams
                }
                BarUtils.setStatusBarColor(fakeView()!!, statusBarColor())
            }
        }
    }

    /**
     * 设置状态栏字体颜色
     */
    private fun setStatusBarMode() {
        if (useImmersiveStatusBar()) {
            StatusBarUtils.setStatusBarLightMode(this, statusBarIsDarkMode())
        }
    }

    override fun useImmersiveStatusBar(): Boolean = true
    override fun statusBarColor(): Int = Color.WHITE
    override fun fakeView(): View? = null
    override fun statusBarIsDarkMode(): Boolean = true

    /************************************状态栏相关*****************************************/
}