package cm.module.core.plugins.dialog

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import cm.module.core.plugins.manager.ActivityManager
import cm.mvvm.core.base.BaseDialogFragment
import cm.mvvm.core.base.BaseViewModel
import com.bumptech.glide.Glide
import com.lcm.modulebase.R
import com.lcm.modulebase.databinding.DialogFullImageBinding

/**
 * ****************************************************************
 * Author: Chaman
 * Date: 2022/10/27 9:30
 * Desc:
 * *****************************************************************
 */
class FullImageDialog : BaseDialogFragment<DialogFullImageBinding, FullImageViewModel>() {

    companion object {
        fun open(imageUrl: String?, width: Int = 0, height: Int = 0) {
            if (imageUrl == null) return

            val fragment = FullImageDialog().apply {
                arguments = Bundle().apply {
                    putString("imageUrl", imageUrl)
                    putInt("width", width)
                    putInt("height", height)
                }
            }
            fragment.fixedShow(ActivityManager.instance.currentActivity() as FragmentActivity)
        }
    }


    override fun screenType(): ScreenType = ScreenType.FULL_SCREEN

    override fun layoutId(): Int = R.layout.dialog_full_image

    private var imageUrl: String? = null
    private var width: Int = 0
    private var height: Int = 0

    override fun initView() {
        arguments?.let {
            imageUrl = it.getString("imageUrl")
            width = it.getInt("width")
            height = it.getInt("height")
        }

    }

    override fun initData() {
        Glide.with(requireContext())
            .load(imageUrl)
            .dontAnimate()
            .into(viewDataBinding.ivImage)
    }
}


class FullImageViewModel : BaseViewModel() {

}