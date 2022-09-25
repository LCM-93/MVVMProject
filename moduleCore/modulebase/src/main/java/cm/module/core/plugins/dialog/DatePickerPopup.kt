package cm.module.core.plugins.dialog

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.view.Gravity
import android.widget.TextView
import com.aigestudio.wheelpicker.widgets.WheelDatePicker
import com.blankj.utilcode.util.ScreenUtils
import com.lcm.modulebase.R
import com.zyyoona7.popup.EasyPopup
import me.jessyan.autosize.utils.AutoSizeUtils
import java.util.*

/**
 * ****************************************************************
 * Author: LiChenMing.Chaman
 * Date: 2019-07-15 15:22
 * Desc:
 * *****************************************************************
 */
@SuppressLint("StaticFieldLeak")
object DatePickerPopup {

    private var datePickerPopup: EasyPopup? = null
    private var tvCancel: TextView? = null
    private var tvEnsure: TextView? = null
    private var datePickerWheel: WheelDatePicker? = null
    private var datePickerListener: DatePickerListener? = null

    fun show(
        activity: Activity,
        title: String? = null,
        datePickerListener: DatePickerListener? = null
    ) {
        DatePickerPopup.datePickerListener = datePickerListener
        initPopup(activity, title)
        datePickerPopup?.showAtLocation(activity.window.decorView, Gravity.BOTTOM, 0, 0)
    }

    private fun initPopup(activity: Activity, title: String?) {
        datePickerPopup = EasyPopup.create()
            .setContentView(R.layout.layout_popup_datepicker)
            .setContext(activity)
            .setWidth(ScreenUtils.getAppScreenWidth())
            .setHeight(AutoSizeUtils.dp2px(activity.applicationContext, 310f))
            .setBackgroundDimEnable(true)
            .setDimValue(0.4f)
            .setFocusAndOutsideEnable(true)
            .setAnimationStyle(R.style.popup_bottom_in_out_style)
            .apply()

        tvCancel = datePickerPopup?.findViewById(R.id.tv_cancel)
        tvEnsure = datePickerPopup?.findViewById(R.id.tv_ensure)
        title?.let {
            datePickerPopup?.findViewById<TextView>(R.id.tv_title)?.text = it
        }
        datePickerWheel = datePickerPopup?.findViewById(R.id.datePicker)
        initDatePicker(activity)

        datePickerPopup?.setOnDismissListener {
            tvCancel = null
            tvEnsure = null
            datePickerWheel = null
            datePickerPopup = null
        }

        tvEnsure?.setOnClickListener {
            datePickerListener?.onSelect(
                datePickerWheel?.currentDate, datePickerWheel?.currentYear,
                datePickerWheel?.currentMonth, datePickerWheel?.currentDay
            )
            datePickerPopup?.dismiss()
        }

        tvCancel?.setOnClickListener {
            datePickerPopup?.dismiss()
        }
    }

    private fun initDatePicker(activity: Activity) {
        datePickerWheel?.visibleItemCount = 3
        datePickerWheel?.selectedItemTextColor = Color.parseColor("#666666")
        datePickerWheel?.itemTextColor = Color.parseColor("#bbbbbb")
        datePickerWheel?.itemTextSize = AutoSizeUtils.dp2px(activity.applicationContext, 20f)
        datePickerWheel?.isCyclic = true
        datePickerWheel?.itemSpace = AutoSizeUtils.dp2px(activity.applicationContext, 50f)
        datePickerWheel?.setIndicator(true)
        datePickerWheel?.indicatorColor = Color.parseColor("#EEEEEE")
        datePickerWheel?.indicatorSize = AutoSizeUtils.dp2px(activity.applicationContext, 1f)
        datePickerWheel?.setAtmospheric(true)
        datePickerWheel?.isCurved = true
        datePickerWheel?.textViewYear?.setTextColor(Color.parseColor("#666666"))
        datePickerWheel?.textViewYear?.textSize = 20f
        datePickerWheel?.textViewDay?.setTextColor(Color.parseColor("#666666"))
        datePickerWheel?.textViewDay?.textSize = 20f
        datePickerWheel?.textViewMonth?.setTextColor(Color.parseColor("#666666"))
        datePickerWheel?.textViewMonth?.textSize = 20f

        val calendar = Calendar.getInstance()
        datePickerWheel?.selectedYear = calendar.get(Calendar.YEAR)
        datePickerWheel?.selectedMonth = calendar.get(Calendar.MONTH) + 1
        datePickerWheel?.selectedDay = calendar.get(Calendar.DATE)

    }

    interface DatePickerListener {
        fun onSelect(date: Date? = null, year: Int? = 0, month: Int? = 0, day: Int? = 0)
    }
}