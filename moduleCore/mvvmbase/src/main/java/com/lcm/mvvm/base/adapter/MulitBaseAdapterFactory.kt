package com.lcm.mvvm.base.adapter

import android.view.ViewGroup

/**
 * ****************************************************************
 * Author: LiChenMing.Chaman
 * Date: 2019-06-27 15:14
 * Desc:
 * *****************************************************************
 */
abstract class MulitBaseAdapterFactory<T> {


    abstract fun createViewHolder(parent: ViewGroup, viewType: Int): MulitBaseViewHolder<T>?

    open fun getViewType(t: T): Int  = 0

    abstract fun getLayoutId(viewType: Int): Int
}