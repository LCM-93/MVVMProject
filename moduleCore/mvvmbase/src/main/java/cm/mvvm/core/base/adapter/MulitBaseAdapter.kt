package cm.mvvm.core.base.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * ****************************************************************
 * Author: LiChenMing.Chaman
 * Date: 2019-06-27 15:19
 * Desc:
 * *****************************************************************
 */
class MulitBaseAdapter<T, VH : MulitBaseViewHolder<T>> : RecyclerView.Adapter<VH> {

    var datas: MutableList<T>? = null
    var mFactory: MulitBaseAdapterFactory<T>? = null

    constructor(datas: MutableList<T>?, mFactory: MulitBaseAdapterFactory<T>?) : super() {
        this.datas = datas
        this.mFactory = mFactory
    }

    override fun getItemCount(): Int = datas?.size ?: 0

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bindData(datas!![position])
        holder.binding!!.executePendingBindings()
    }

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return mFactory?.createViewHolder(parent, viewType) as VH
    }

    override fun getItemViewType(position: Int): Int =
            mFactory?.getViewType(datas!![position])!!

}