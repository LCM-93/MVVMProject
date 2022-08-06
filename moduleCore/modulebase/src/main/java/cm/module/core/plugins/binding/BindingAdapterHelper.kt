package cm.module.core.plugins.binding

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import cm.module.core.utils.GlideRoundedCornersTransform
import com.lcm.modulebase.R
import cm.module.core.utils.GlideUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
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
    @BindingAdapter("bindGifResName")
    fun bindGif(imageView: ImageView, resName: String) {
        val imageResId: Int = imageView.context.resources.getIdentifier(
            resName,
            "drawable",
            imageView.context.packageName
        )
        Glide.with(imageView.context).asGif().load(imageResId)
            .into(imageView)
    }

    @JvmStatic
    @SuppressWarnings("unchecked")
    @BindingAdapter("bindGifUrl")
    fun bindGifUrl(imageView: ImageView, gifUrl: String?){
        Glide.with(imageView.context).asGif().load(gifUrl)
            .into(imageView)
    }

    @JvmStatic
    @SuppressWarnings("unchecked")
    @BindingAdapter("bindCircle")
    fun bindCircleImg(imageView: ImageView, url: String?) {
        url?.let {
            Glide.with(imageView.context).load(it)
                .apply(RequestOptions.bitmapTransform(CircleCrop()))
                .into(imageView)
        }
    }

    @JvmStatic
    @SuppressWarnings("unchecked")
    @BindingAdapter("roundImg", "corner", requireAll = true)
    fun roundImg(imageView: ImageView, url: String?, corner: Int?) {
        if (url == null) return
        Glide.with(imageView.context).load(url)
            .apply(
                RequestOptions().optionalTransform(
                    GlideRoundedCornersTransform(
                        (corner ?: 0).toFloat(),
                        GlideRoundedCornersTransform.CornerType.ALL
                    )
                )
            )
            .into(imageView)
    }


    @JvmStatic
    @SuppressWarnings("unchecked")
    @BindingAdapter("notifyItemChanged")
    fun notifyItemChanged(recyclerView: RecyclerView, index: Int?) {
        index?.let {
            recyclerView.adapter?.notifyItemChanged(index)
        }
    }


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



