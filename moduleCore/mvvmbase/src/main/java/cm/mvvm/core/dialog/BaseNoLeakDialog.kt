package cm.mvvm.core.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Message
import androidx.fragment.app.DialogFragment
import java.lang.ref.WeakReference

/**
 * 解决DialogFragment中产生的内存泄露问题
 *  https://blog.csdn.net/u012944685/article/details/112257568
 */
class BaseNoLeakDialog(context: Context, themeResId: Int) : Dialog(context, themeResId) {
    private var hostFragmentReference: WeakReference<DialogFragment>? = null


    fun setHostFragmentReference(hostFragment: DialogFragment){
        this.hostFragmentReference = WeakReference(hostFragment)
    }

    override fun setOnCancelListener(listener: DialogInterface.OnCancelListener?) {

    }

    override fun setOnDismissListener(listener: DialogInterface.OnDismissListener?) {

    }

    override fun setOnShowListener(listener: DialogInterface.OnShowListener?) {

    }

    override fun setCancelMessage(msg: Message?) {
    }

    override fun setDismissMessage(msg: Message?) {

    }

    override fun dismiss() {
        super.dismiss()
        hostFragmentReference?.get()?.dismissAllowingStateLoss()
    }

}