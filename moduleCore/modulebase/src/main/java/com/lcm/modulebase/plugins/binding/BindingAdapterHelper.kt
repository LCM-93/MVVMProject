package com.lcm.modulebase.plugins.binding

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lcm.modulebase.R
import com.lcm.modulebase.utils.GlideUtils
import kotlin.math.roundToInt

/**
 * ****************************************************************
 * Author: LiChenMing.Chaman
 * Date: 2019-06-26 16:39
 * Desc:
 * *****************************************************************
 */
object BindingAdapterHelper {



    @JvmStatic
    @SuppressWarnings("unchecked")
    @BindingAdapter("notifyItemChanged")
    fun notifyItemChanged(recyclerView: RecyclerView, index: Int?) {
        index?.let {
            recyclerView.adapter?.notifyItemChanged(index)
        }
    }

//    @JvmStatic
//    @SuppressWarnings("unchecked")
//    @BindingAdapter("rightBtnStr")
//    fun setRightStr(headView: HeadView, rightStr: String?) {
//        rightStr?.let {
//            headView.rightBtnStr = it
//        }
//    }
//
//    @JvmStatic
//    @SuppressWarnings("unchecked")
//    @BindingAdapter("title")
//    fun setTitle(headView: HeadView, title: String?) {
//        headView.setTitleStr(title)
//    }

    @JvmStatic
    @BindingAdapter("android:layout_marginBottom")
    fun setBottomMargin(view: View, bottomMargin: Float) {
        val layoutParams: ViewGroup.MarginLayoutParams =
            view.layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.setMargins(
            layoutParams.leftMargin, layoutParams.topMargin,
            layoutParams.rightMargin, bottomMargin.roundToInt()
        )
        view.layoutParams = layoutParams
    }

    @JvmStatic
    @SuppressWarnings("unchecked")
    @BindingAdapter("head_img_path")
    fun setHeadImage(imageView: ImageView, path: String?) {
        GlideUtils.loadCircleImage(imageView, path, R.drawable.ic_place_holder)
    }

    @JvmStatic
    @SuppressWarnings("unchecked")
    @BindingAdapter("img_path")
    fun setImagePic(imageView: ImageView, path: String?) {
        GlideUtils.loadCornerImage(imageView, path)
    }



}



