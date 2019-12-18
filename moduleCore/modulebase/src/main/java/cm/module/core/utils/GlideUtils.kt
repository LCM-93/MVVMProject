package cm.module.core.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.lcm.modulebase.R


/**
 * ****************************************************************
 * Author: LiChenMing.Chaman
 * Date: 2019/9/23 10:44
 * Desc:
 * *****************************************************************
 */
object GlideUtils {

    fun loadCircleImage(view: ImageView, path: String?, default: Int?=null) {
        Glide.with(view)
            .load(path)
            .error(default?: R.drawable.ic_place_holder)
            .placeholder(default?: R.drawable.ic_place_holder)
            .apply(RequestOptions.bitmapTransform(CircleCrop()))
            .dontAnimate()
            .into(view)
    }

    fun loadCornerImage(view: ImageView, path: String?, default: Int? = null) {
        Glide.with(view)
            .load(path)
            .error(default?: R.drawable.ic_place_holder)
            .placeholder(default?: R.drawable.ic_place_holder)
            .centerCrop()
            .apply(RequestOptions.bitmapTransform(
                GlideRoundedCornersTransform(
                    4f,
                    GlideRoundedCornersTransform.CornerType.ALL
                )
            ))
            .dontAnimate()
            .into(view)
    }
}