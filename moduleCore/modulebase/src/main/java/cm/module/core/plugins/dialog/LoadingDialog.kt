package cm.module.core.plugins.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.lcm.modulebase.R

/**
 * ****************************************************************
 * Author: Chaman
 * Date: 2022/8/22 20:16
 * Desc:
 * *****************************************************************
 */
class LoadingDialog : Dialog {

    constructor(context: Context, themeResId: Int) : super(context, themeResId)

    constructor(context: Context) : this(context, R.style.MyDialog)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_loading)
    }
}