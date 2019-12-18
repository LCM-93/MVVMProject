package cm.module.core.base

import android.util.Log
import androidx.databinding.ViewDataBinding
import cm.mvvm.core.base.BaseFragment
import cm.mvvm.core.base.BaseViewModel
import cm.mvvm.core.base.event.LoadingStatus

/**
 * ****************************************************************
 * Author: LiChenMing.Chaman
 * Date: 2019/11/1 15:28
 * Desc:
 * *****************************************************************
 */
abstract class AppBaseFragment<DB : ViewDataBinding, VM : BaseViewModel> : BaseFragment<DB,VM>() {

    override fun initLoadingView() {
        Log.e("TAG","初始化一个统一的LoadingView")
    }


    override fun handleLoadingStatus(loadingStatus: LoadingStatus?) {
        Log.e("TAG","统一处理 Loading状态")
    }

}