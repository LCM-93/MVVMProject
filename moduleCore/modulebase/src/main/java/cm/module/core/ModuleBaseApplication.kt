package cm.module.core

import cm.module.core.utils.ActivityLifecycleCallbacksImp
import cm.mvvm.core.base.BaseApplication

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