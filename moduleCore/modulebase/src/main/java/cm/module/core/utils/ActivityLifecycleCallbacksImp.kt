package cm.module.core.utils

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentActivity
import cm.module.core.plugins.manager.ActivityManager

/**
 * ****************************************************************
 * Author: LiChenMing.Chaman
 * Date: 2019-09-22 11:16
 * Desc:
 * *****************************************************************
 */
class ActivityLifecycleCallbacksImp : Application.ActivityLifecycleCallbacks {
    companion object {
        val TAG: String = ActivityLifecycleCallbacksImp::class.java.simpleName
    }

    override fun onActivityPaused(p0: Activity?) {
    }

    override fun onActivityResumed(p0: Activity?) {
        Log.i(TAG, "${p0?.javaClass?.simpleName} ---->onActivityResumed")
    }

    override fun onActivityStarted(p0: Activity?) {
    }

    override fun onActivityDestroyed(p0: Activity?) {
        Log.i(TAG, "${p0?.javaClass?.simpleName} ---->onActivityDestroyed")
        ActivityManager.instance.finishActivity(p0)
    }

    override fun onActivitySaveInstanceState(p0: Activity?, p1: Bundle?) {
    }

    override fun onActivityStopped(p0: Activity?) {
        Log.i(TAG, "${p0?.javaClass?.simpleName} ---->onActivityStopped")
    }

    override fun onActivityCreated(p0: Activity?, p1: Bundle?) {
        Log.i(TAG, "${p0?.javaClass?.simpleName} ---->onActivityCreated")
        ActivityManager.instance.addActivity(p0)
        if(p0 is FragmentActivity){
            p0.supportFragmentManager.registerFragmentLifecycleCallbacks(FragmentLifecycleCallbacksImp(),true)
        }
    }
}