package com.lcm.modulebase.plugins.manager

import android.app.Activity
import java.util.*

/**
 * ****************************************************************
 * Author: LiChenMing.Chaman
 * Date: 2019-09-22 10:58
 * Desc:
 * *****************************************************************
 */
class ActivityManager private constructor() {
    private var activityStack: Stack<Activity>? = null

    companion object {
        val instance: ActivityManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { ActivityManager() }
        val TAG: String = ActivityManager::class.java.simpleName
    }

    fun addActivity(activity: Activity?) {
        if (activityStack == null) activityStack = Stack()
        activityStack?.add(activity)
    }

    fun finishActivity(activity: Activity?) {
        activityStack?.let {
            if (it.contains(activity)) {
                it.remove(activity)
                activity?.finish()
            }
        }
    }

    fun finishActivity(clazz: Class<*>) {
        activityStack?.let {
            for (activity in it) {
                if (activity.javaClass == clazz) {
                    finishActivity(activity)
                    break
                }
            }
        }
    }

    fun currentActivity(): Activity? {
        activityStack?.let {
            return it.lastElement()
        }
        return null
    }

    fun finishAllActivity() {
        activityStack?.let {
            for (activity in it) {
                activity?.finish()
            }
            it.clear()
        }
    }




}