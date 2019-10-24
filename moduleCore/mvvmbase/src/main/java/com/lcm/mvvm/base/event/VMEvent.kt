package com.lcm.mvvm.base.event

/**
 * ****************************************************************
 * Author: LiChenMing.Chaman
 * Date: 2019-06-22 21:49
 * Desc: 负责传递ViewModel到View的事件
 * *****************************************************************
 */
class VMEvent<T>(content: T) {
    private var mContent: T? = content
    var hasBeenHandled: Boolean = false

    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            mContent
        }
    }
}