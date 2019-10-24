package com.lcm.mvvm.base

import androidx.multidex.MultiDexApplication
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.Utils
import com.lcm.mvvmbase.BuildConfig
import com.squareup.leakcanary.LeakCanary
import com.tencent.mmkv.MMKV
import me.jessyan.autosize.AutoSizeConfig

/**
 * ****************************************************************
 * Author: LiChenMing.Chaman
 * Date: 2019-07-18 10:45
 * Desc:
 * *****************************************************************
 */
open class BaseApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        Utils.init(this)
        MMKV.initialize(this)
        LeakCanary.install(this)

        AutoSizeConfig.getInstance().isCustomFragment = true

        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this)
    }
}