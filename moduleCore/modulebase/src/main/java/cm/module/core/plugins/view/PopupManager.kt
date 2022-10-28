package cm.module.core.plugins.view

import android.annotation.SuppressLint
import android.app.Activity
import android.view.Gravity
import android.widget.TextView
import com.lcm.modulebase.R
import com.zyyoona7.popup.EasyPopup
import me.jessyan.autosize.utils.AutoSizeUtils

/**
 * ****************************************************************
 * Author: LiChenMing.Chaman
 * Date: 2019-07-12 15:03
 * Desc:
 * *****************************************************************
 */
object PopupManager {
    @SuppressLint("StaticFieldLeak")
    private var dialogPopup: EasyPopup? = null

    fun closeAllDialog() {
        dialogPopup?.dismiss()
    }

    fun showSimpleDialog(
        activity: Activity,
        msg: String,
        ensure_text: String? = "确认",
        cancel_text: String? = "取消",
        ensureListener: DialogEnsureListener? = null,
        cancelListener: DialogCancelListener? = null
    ) {
        dialogPopup = EasyPopup.create()
            .setContentView(R.layout.layout_popup_simple_dialog)
            .setContext(activity)
            .setWidth(AutoSizeUtils.dp2px(activity.applicationContext, 270f))
            .setDimValue(0.8f)
            .setBackgroundDimEnable(true)
            .setAnimationStyle(R.style.scale_in_out_style)
            .apply()
        dialogPopup?.findViewById<TextView>(R.id.tv_msg)?.text = msg
        dialogPopup?.findViewById<TextView>(R.id.tv_cancel)?.also { it.text = cancel_text }
            ?.setOnClickListener {
                dialogPopup?.dismiss()
                cancelListener?.onCancel()
            }
        dialogPopup?.findViewById<TextView>(R.id.tv_ensure)?.also { it.text = ensure_text }
            ?.setOnClickListener {
                dialogPopup?.dismiss()
                ensureListener?.onEnsure()
            }
        dialogPopup?.showAtLocation(activity.window.decorView, Gravity.CENTER, 0, 0)
    }

    interface DialogCancelListener {
        fun onCancel()
    }

    interface DialogEnsureListener {
        fun onEnsure()
    }

}