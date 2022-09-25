package cm.module.core.base

import android.annotation.SuppressLint
import android.util.Log
import androidx.databinding.ViewDataBinding
import cm.module.core.plugins.dialog.LoadingDialog
import cm.mvvm.core.base.BaseActivity
import cm.mvvm.core.base.BaseViewModel
import cm.mvvm.core.base.event.LoadingStatus
import cm.mvvm.core.utils.RxBus
import com.lcm.modulebase.R

/**
 * ****************************************************************
 * Author: Chaman
 * Date: 2019/11/1 15:26
 * Desc:
 * *****************************************************************
 */
@SuppressLint("UsingALog")
abstract class AppBaseActivity<DB : ViewDataBinding, VM : BaseViewModel> : BaseActivity<DB, VM>() {
    var loadingDialog: LoadingDialog? = null

    override fun initLoadingView() {
        Log.e("TAG", "初始化一个统一的LoadingView")
        if (loadingDialog == null) loadingDialog = LoadingDialog(this)
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

    override fun finish() {
        super.finish()
        if(needPendingAnim()) {
            overridePendingTransition(0, R.anim.page_right_out);//出场动画
        }
    }

    open fun needPendingAnim():Boolean = true
}