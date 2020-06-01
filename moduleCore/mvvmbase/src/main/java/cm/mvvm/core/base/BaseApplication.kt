package cm.mvvm.core.base

import androidx.multidex.MultiDexApplication
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.Utils
import com.lcm.mvvmbase.BuildConfig
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import com.tencent.mmkv.MMKV
import me.jessyan.autosize.AutoSizeConfig

/**
 * ****************************************************************
 * Author: LiChenMing.Chaman
 * Date: 2019-07-18 10:45
 * Desc:
 * *****************************************************************
 */
open class BaseApplication : MultiDexApplication() {
    companion object{
        var refWatcher:RefWatcher? = null
    }

    override fun onCreate() {
        super.onCreate()

        Utils.init(this)
        MMKV.initialize(this)
        refWatcher = LeakCanary.install(this)

        AutoSizeConfig.getInstance().isCustomFragment = true

        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this)
    }
}