package cm.module.core.base

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import cm.mvvm.core.base.lazy.loadRootFragment
import cm.mvvm.core.utils.StatusBarUtils
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.SizeUtils
import com.lcm.modulebase.R

class FragmentContainerActivity : AppCompatActivity() {

    companion object {
        fun open(activity: Activity, fragmentClassName: String, arguments: Bundle) {
            val intent = Intent(activity, FragmentContainerActivity::class.java).apply {
                putExtra("fragmentClassName", fragmentClassName)
            }
            intent.putExtras(arguments)
            activity.startActivity(intent)
        }
    }

    private var fragmentClassName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_container)
        setStatusBar()
        setStatusBarMode()
        intent?.let {
            fragmentClassName = it.getStringExtra("fragmentClassName")
        }
        if (fragmentClassName == null) {
            finish()
            return
        }
        val fragment: Fragment = Class.forName(fragmentClassName!!).newInstance() as Fragment
        fragment.arguments = intent.extras
        loadRootFragment(R.id.rootView, fragment)
    }


    /************************************状态栏相关*****************************************/
    /**
     * 设置状态栏
     */
    private fun setStatusBar() {
        if (useImmersiveStatusBar()) {
            if (fakeView() == null) {
                BarUtils.setStatusBarColor(this, Color.TRANSPARENT)
            } else {
                if (BarUtils.getStatusBarHeight() > SizeUtils.dp2px(20f)) {
                    val layoutParams = fakeView()!!.layoutParams
                    layoutParams.height = BarUtils.getStatusBarHeight()
                    fakeView()!!.layoutParams = layoutParams
                }
                BarUtils.setStatusBarColor(fakeView()!!, statusBarColor())
            }
        }
    }

    /**
     * 设置状态栏字体颜色
     */
    private fun setStatusBarMode() {
        if (useImmersiveStatusBar()) {
            StatusBarUtils.setStatusBarLightMode(this, statusBarIsDarkMode())
        }
    }

    private fun useImmersiveStatusBar(): Boolean = true
    private fun statusBarColor(): Int = Color.WHITE
    private fun fakeView(): View? = null
    private fun statusBarIsDarkMode(): Boolean = true

    /************************************状态栏相关*****************************************/
}