package com.lcm.modulebase

import com.lcm.modulebase.utils.ActivityLifecycleCallbacksImp
import com.lcm.mvvm.base.BaseApplication

/**
 * ****************************************************************
 * Author: LiChenMing.Chaman
 * Date: 2019/10/24 15:03
 * Desc:
 * *****************************************************************
 */
class ModuleBaseApplication:BaseApplication() {

    override fun onCreate() {
        super.onCreate()

        registerActivityLifecycleCallbacks(ActivityLifecycleCallbacksImp())
    }
}