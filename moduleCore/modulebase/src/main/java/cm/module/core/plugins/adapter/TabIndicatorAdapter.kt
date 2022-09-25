//package cm.module.core.plugins.adapter
//
//import android.content.Context
//import android.graphics.Color
//import com.blankj.utilcode.util.Utils
//import me.jessyan.autosize.utils.AutoSizeUtils
//import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
//import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
//import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
//import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
//import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView
//
//
///**
// * ****************************************************************
// * Author: Chaman
// * Date: 2022/8/10 20:25
// * Desc:
// * *****************************************************************
// */
//class TabIndicatorAdapter : CommonNavigatorAdapter {
//
//    var selectedTextSize: Float = 18f
//    var normalTextSize: Float = 16f
//    var selectedTextColor: Int = Color.parseColor("#444444")
//    var normalTextColor: Int = Color.parseColor("#444444")
//    var indicatorColor:Int = Color.parseColor("#FFFF784D")
//    var titleList: MutableList<String> = mutableListOf()
//    var indicatorTapClickListener: OnTapClickListener? = null
//
//    constructor(
//        titleList: MutableList<String>,
//        indicatorTapClickListener: OnTapClickListener?
//    ) : super() {
//        this.titleList = titleList
//        this.indicatorTapClickListener = indicatorTapClickListener
//    }
//
//    constructor() : super()
//
//
//    override fun getCount(): Int = titleList.size
//
//    override fun getTitleView(context: Context?, index: Int): IPagerTitleView {
//        val colorTransitionPagerTitleView = ColorTransitionPagerTitleView(context)
//        colorTransitionPagerTitleView.normalColor = normalTextColor //普通状态
//        colorTransitionPagerTitleView.selectedColor = selectedTextColor//选中时状态
//        colorTransitionPagerTitleView.textSize =
//            AutoSizeUtils.dp2px(Utils.getApp(), normalTextSize).toFloat()
//        colorTransitionPagerTitleView.selectTextSize =
//            AutoSizeUtils.dp2px(Utils.getApp(), selectedTextSize)
//        colorTransitionPagerTitleView.unSelectTextSize =
//            AutoSizeUtils.dp2px(Utils.getApp(), normalTextSize)
//        colorTransitionPagerTitleView.text = titleList[index]
//        colorTransitionPagerTitleView.setOnClickListener {
//            indicatorTapClickListener?.onTabClick(index)
//        }
//        return colorTransitionPagerTitleView
//    }
//
//    override fun getIndicator(context: Context?): IPagerIndicator {
//        val linePagerIndicator = LinePagerIndicator(context)
//        linePagerIndicator.mode = LinePagerIndicator.MODE_EXACTLY
//        linePagerIndicator.lineWidth = AutoSizeUtils.dp2px(Utils.getApp(), 18f).toFloat()
//        linePagerIndicator.lineHeight = AutoSizeUtils.dp2px(Utils.getApp(), 5f).toFloat()
//        linePagerIndicator.setColors(indicatorColor)
//        linePagerIndicator.roundRadius = 90f
//        return linePagerIndicator
//    }
//
//    interface OnTapClickListener {
//        fun onTabClick(index: Int)
//    }
//}