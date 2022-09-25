package cm.module.core.plugins.arouter

import android.app.Activity
import android.os.Parcelable
import cm.module.core.plugins.manager.ActivityManager
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.launcher.ARouter
import com.lcm.modulebase.R
import java.io.Serializable

/**
 * ****************************************************************
 * Author: Chaman
 * Date: 2022/9/17 10:34
 * Desc:
 * *****************************************************************
 */
object ARouterHelper {

    fun inject(any: Any) {
        ARouter.getInstance().inject(any)
    }
}

object ARouterBuilder {

    lateinit var postcard: Postcard

    fun build(path: String): ARouterBuilder {
        postcard = ARouter.getInstance().build(path)
        return this
    }

    fun withString(key: String?, value: String?): ARouterBuilder {
        postcard.withString(key, value)
        return this
    }

    fun withInt(key: String?, value: Int?): ARouterBuilder {
        postcard.withInt(key, value ?: 0)
        return this
    }

    fun withLong(key: String?, value: Long?): ARouterBuilder {
        postcard.withLong(key, value ?: 0)
        return this
    }

    fun withBoolean(key: String?, value: Boolean): ARouterBuilder {
        postcard.withBoolean(key, value)
        return this
    }

    fun withParcelable(key: String?, value: Parcelable?): ARouterBuilder {
        postcard.withParcelable(key, value)
        return this
    }

    fun withSerializable(key: String?, value: Serializable?): ARouterBuilder {
        postcard.withSerializable(key, value)
        return this
    }

    fun navigation(activity: Activity? = null, needAnim: Boolean = true): Any? {
        if (needAnim) {
            postcard.withTransition(R.anim.page_right_in, 0)
        }
        if (activity != null) {
            return postcard.navigation(activity)
        }
        if (ActivityManager.instance.currentActivity() != null) {
            return postcard.navigation(ActivityManager.instance.currentActivity())
        }
        return postcard.navigation()
    }
}