package com.lcm.modulebase.plugins.binding

import android.os.Build
import android.text.Html
import android.text.Spanned

/**
 * ****************************************************************
 * Author: LiChenMing.Chaman
 * Date: 2019-06-29 12:21
 * Desc:
 * *****************************************************************
 */
object ViewBindUtil {

    @Suppress("DEPRECATION")
    @JvmStatic
    fun getRegisterText(): Spanned {
        val str = "新用户？<font color=\"#007AFF\">注册</font>"
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(str, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(str)
        }
    }

    @Suppress("DEPRECATION")
    @JvmStatic
    fun getRegisterDeal(): Spanned {
        val str = "同意<font color=\"#007AFF\">《服务协议》</font>"
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(str, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(str)
        }
    }


}