package cm.mvvm.core.base.base

import android.os.Bundle
import android.view.View
import android.widget.Toast
import cm.mvvm.core.base.event.LoadingStatus

/**
 * ****************************************************************
 * Author: LiChenMing.Chaman
 * Date: 2020/5/19 10:05
 * Desc:
 * *****************************************************************
 */
interface BaseVMView {

    fun layoutId(): Int

    fun initView()

    fun initData(savedInstanceState: Bundle?)

    fun initLoadingView() //初始化LoadingView

    fun setListener()

    fun baseObserve() //一些关于ViewModel的基础操作监听

    fun observe()

    fun handleVMEvent(any: Any?)  //处理ViewModel中的事件

    fun handleLoadingStatus(loadingStatus: LoadingStatus?) //处理Loading事件

    fun openPage(page: String, param: Any?)  //监听ViewModel中跳转页面标志

    fun showToast(msg: String, duration: Int = Toast.LENGTH_SHORT)

    fun needEventBus(): Boolean  //是否使用EventBus

    fun registerEventBus()  //EventBus注册

    fun unRegisterEventBus()  //EventBus取消注册

    fun useImmersiveStatusBar(): Boolean //是否使用沉浸式状态栏

    fun statusBarColor(): Int  //状态栏背景色

    fun fakeView(): View?  //当状态栏不是纯色时，可在布局中添加view，通过该方法将添加的view设置成与状态栏高度一致

    fun statusBarIsDarkMode(): Boolean  //配置状态栏字体颜色   true 黑色  false 白色


}