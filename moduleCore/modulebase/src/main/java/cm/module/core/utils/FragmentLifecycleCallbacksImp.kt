package cm.module.core.utils

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

/**
 * ****************************************************************
 * Author: LiChenMing.Chaman
 * Date: 2019/12/20 17:22
 * Desc:
 * *****************************************************************
 */
class FragmentLifecycleCallbacksImp : FragmentManager.FragmentLifecycleCallbacks() {
    companion object {
        val TAG: String = FragmentLifecycleCallbacksImp::class.java.simpleName
    }

    override fun onFragmentCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
        super.onFragmentCreated(fm, f, savedInstanceState)
        Log.i(TAG, "${f.javaClass.simpleName} ---->onFragmentCreated")
    }

    override fun onFragmentAttached(fm: FragmentManager, f: Fragment, context: Context) {
        super.onFragmentAttached(fm, f, context)
        Log.i(TAG, "${f.javaClass.simpleName} ---->onFragmentAttached")
    }

    override fun onFragmentResumed(fm: FragmentManager, f: Fragment) {
        super.onFragmentResumed(fm, f)
        Log.i(TAG, "${f.javaClass.simpleName} ---->onFragmentResumed")
    }

    override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) {
        super.onFragmentDestroyed(fm, f)
        Log.i(TAG, "${f.javaClass.simpleName} ---->onFragmentDestroyed")
    }



}