package cm.mvvm.core.base.base


/**
 * ****************************************************************
 * Author: LiChenMing.Chaman
 * Date: 2020/5/19 10:28
 * Desc:
 * *****************************************************************
 */
interface BaseVMFragment : BaseVMView {

    fun initData()

    fun viewModelTag():String?  //创建ViewModel时添加TAG，防止Fragment复用时仅创建一个ViewModel
}