package com.lcm.mvvm.base.adapter

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * ****************************************************************
 * Author: LiChenMing.Chaman
 * Date: 2019-06-27 15:05
 * Desc:
 * *****************************************************************
 */
abstract class MulitBaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var binding: ViewDataBinding? = null

    constructor(binding: ViewDataBinding?, itemView: View) : this(itemView) {
        this.binding = binding
    }

    abstract fun bindData(t: T)

}