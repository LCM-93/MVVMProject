package cm.mvvm.core.base.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * ****************************************************************
 * Author: LiChenMing.Chaman
 * Date: 2019-06-26 14:30
 * Desc:
 * *****************************************************************
 */
abstract class BaseAdapter<BD : ViewDataBinding, T : Any> :
    RecyclerView.Adapter<BaseAdapter.BaseViewHolder<BD>> {

    var datas: MutableList<T>? = null
    lateinit var dataBinding: BD

    constructor(datas: MutableList<T>?) {
        this.datas = datas
    }

    abstract fun layoutId(): Int

    abstract fun bindData(data: T, binding: BD,position: Int)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<BD> {
        dataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), layoutId(), parent, false)
        return BaseViewHolder(dataBinding)
    }

    override fun getItemCount(): Int = datas?.size ?: 0

    override fun onBindViewHolder(holder: BaseViewHolder<BD>, position: Int) {
        val data: T = datas!![position]
        bindData(data, holder.binding,position)
        holder.binding.executePendingBindings()
    }

    fun setItemData(datas: MutableList<T>) {
        this.datas = datas
        notifyDataSetChanged()
    }


    class BaseViewHolder<BD : ViewDataBinding>(itemView: View, var binding: BD) : RecyclerView.ViewHolder(itemView) {
        constructor(binding: BD) : this(binding.root, binding)
    }
}