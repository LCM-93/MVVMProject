package cm.module.core.base

import android.annotation.SuppressLint
import android.util.Log
import androidx.databinding.ViewDataBinding
import cm.module.core.plugins.dialog.LoadingDialog
import cm.mvvm.core.base.BaseFragment
import cm.mvvm.core.base.BaseViewModel
import cm.mvvm.core.base.event.LoadingStatus
import cm.mvvm.core.utils.RxBus

/**
 * ****************************************************************
 * Author: Chaman
 * Date: 2019/11/1 15:28
 * Desc:
 * *****************************************************************
 */
@SuppressLint("UsingALog")
abstract class AppBaseFragment<DB : ViewDataBinding, VM : BaseViewModel> : BaseFragment<DB, VM>() {
    private var loadingDialog: LoadingDialog? = null

    override fun initLoadingView() {
        Log.e("TAG", "初始化一个统一的LoadingView")
        if (loadingDialog == null) {
            loadingDialog = if (activity is AppBaseActivity<*, *>) {
                (activity as AppBaseActivity<*, *>).loadingDialog
            } else {
                LoadingDialog(requireActivity())
            }
        }
    }

    override fun handleLoadingStatus(loadingStatus: LoadingStatus?) {
        Log.e("TAG", "统一处理 Loading状态")
        when (loadingStatus?.status) {
            LoadingStatus.Status.LOADING -> loadingDialog?.show()
            else -> loadingDialog?.dismiss()
        }
    }

    override fun registerEventBus() {
        RxBus.get().register(this)
    }

    override fun unRegisterEventBus() {
        RxBus.get().unregister(this)
    }

}