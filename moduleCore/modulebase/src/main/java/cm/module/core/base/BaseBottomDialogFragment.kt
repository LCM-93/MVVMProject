package cm.module.core.base

import android.view.Gravity
import androidx.databinding.ViewDataBinding
import cm.mvvm.core.base.BaseDialogFragment
import cm.mvvm.core.base.BaseViewModel
import com.lcm.modulebase.R

/**
 * ****************************************************************
 * Author: Chaman
 * Date: 2022/7/27 20:23
 * Desc:
 * *****************************************************************
 */
abstract class BaseBottomDialogFragment<DB : ViewDataBinding, VM : BaseViewModel> :
    BaseDialogFragment<DB, VM>() {

    override fun gravity(): Int {
        return Gravity.BOTTOM
    }

    override fun windowAnimation(): Int  = R.style.bottom_in_out_style

}