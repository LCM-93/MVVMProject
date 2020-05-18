package cm.mvvm.core.base

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import cm.mvvm.core.base.event.LoadingStatus
import com.lcm.mvvmbase.R
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import java.lang.reflect.ParameterizedType

/**
 * ****************************************************************
 * Author: LiChenMing.Chaman
 * Date: 2019/11/1 14:56
 * Desc:
 * *****************************************************************
 */
abstract class BaseDialogFragment<DB : ViewDataBinding, VM : BaseViewModel> : DialogFragment() {

    lateinit var viewDataBinding: DB
    lateinit var viewModel: VM
    private var instance: Any? = null

    private var viewModelFactory: ViewModelProvider.NewInstanceFactory? = null
    val lifecycleScopeProvider: AndroidLifecycleScopeProvider by lazy {
        AndroidLifecycleScopeProvider.from(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, R.style.BASE_ThemeDialog)
        viewModel = viewModel()
        viewModel.lifecycleScopeProvider = AndroidLifecycleScopeProvider.from(this)
    }


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        if (instance == null) {
            return null
        }
        viewDataBinding = DataBindingUtil.inflate(inflater, layoutId(), container, false)
        viewDataBinding.setLifecycleOwner(this)
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        initLoadingView()
        setListener()
        baseObserve()
        observe()
        initData(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        if (instance == null) {
            dismissAllowingStateLoss()
        }
    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return object : Dialog(activity, theme) {
            override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
                return super.dispatchTouchEvent(ev)
            }
        }
    }


    abstract fun layoutId(): Int
    abstract fun initView()
    open fun initLoadingView() {}
    abstract fun initData(savedInstanceState: Bundle?)
    open fun setListener() {}
    open fun observe() {}
    open fun openPage(page: String, param: Any?) {}
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
            handleEvent(it?.getContentIfNotHandled())
        })
        viewModel.loadStatus.observe(this, Observer {
            handleEvent(it?.getContentIfNotHandled())
        })
        viewModel.toastMsg.observe(this, Observer {
            it?.getContentIfNotHandled()?.let { msg ->
                showToast(msg)
            }
        })
        viewModel.openPage.observe(this, Observer {
            it?.getContentIfNotHandled()?.let { pair ->
                openPage(pair.first, pair.second)
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
            ViewModelProvider(activity!!,viewModelFactory!!).get(getVMClass())
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


    fun showToast(msg: String) {
        Toast.makeText(activity?.applicationContext, msg, Toast.LENGTH_SHORT).show()
    }


    fun fixedShow(activity: FragmentActivity) {
        fixedShow(activity, this.toString())
    }

    fun fixedShow(activity: FragmentActivity?, tag: String) {
        if (activity == null || activity.isFinishing) {
            Log.d("BaseDialogFragment", String.format("activity [%s] is null or is finishing!", activity))
            return
        }
        instance = Any()
        val ft = activity.supportFragmentManager.beginTransaction()
        ft.add(this, tag)
        ft.commitAllowingStateLoss()
    }

    /**
     * 此方法使用commit会出现异常 java.lang.IllegalStateException: Can not perform this
     * action after onSaveInstanceState
     */
    @Deprecated("此方法使用commit会出现异常", ReplaceWith("fixedShow(activity, tag)"))
    override fun show(manager: FragmentManager, tag: String?) {
        super.show(manager, tag)
    }

    /**
     * 同 [.show]
     */
    @Deprecated("此方法使用commit会出现异常", ReplaceWith("fixedShow(activity, tag)"))
    override fun show(transaction: FragmentTransaction, tag: String?): Int {
        return super.show(transaction, tag)
    }

}