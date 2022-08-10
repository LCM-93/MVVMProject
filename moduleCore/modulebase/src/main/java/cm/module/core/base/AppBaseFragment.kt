package cm.module.core.base

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.databinding.ViewDataBinding
import cm.mvvm.core.base.BaseFragment
import cm.mvvm.core.base.BaseViewModel
import cm.mvvm.core.base.event.LoadingStatus
import cm.mvvm.core.utils.RxBus

/**
 * ****************************************************************
 * Author: LiChenMing.Chaman
 * Date: 2019/11/1 15:28
 * Desc:
 * *****************************************************************
 */
abstract class AppBaseFragment<DB : ViewDataBinding, VM : BaseViewModel> : BaseFragment<DB, VM>() {

    companion object {
        fun standOpen(activity: Activity, arguments: Bundle) {
            FragmentContainerActivity.open(activity, this::class.java.name, arguments)
        }
    }


    override fun initLoadingView() {
        Log.e("TAG", "初始化一个统一的LoadingView")
    }


    override fun handleLoadingStatus(loadingStatus: LoadingStatus?) {
        Log.e("TAG", "统一处理 Loading状态")
    }

    override fun registerEventBus() {
        RxBus.get().register(this)
    }

    override fun unRegisterEventBus() {
        RxBus.get().unregister(this)
    }
}