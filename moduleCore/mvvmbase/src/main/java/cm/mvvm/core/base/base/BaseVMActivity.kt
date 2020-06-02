package cm.mvvm.core.base.base

import android.os.Bundle

/**
 * ****************************************************************
 * Author: LiChenMing.Chaman
 * Date: 2020/5/19 10:08
 * Desc:
 * *****************************************************************
 */
interface BaseVMActivity : BaseVMView {
    fun initData(savedInstanceState: Bundle?)

    fun finishActivity(result: Any?)
}