package com.lcm.modulebase.plugins.view

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.lcm.modulebase.R

/**
 * ****************************************************************
 * Author: LiChenMing.Chaman
 * Date: 2019-07-02 11:23
 * Desc:
 * *****************************************************************
 */
class HeadView : RelativeLayout {
    var title: String? = null
        set(value) {
            field = value
            tvTitle?.text = value
        }
    var showLine: Boolean? = true
    var backClickListener: BackClickListener? = null
    var showRightBtn: Boolean? = false
    var rightBtnStr: String? = "完成"
        set(value) {
            field = value
            tvRight?.text = value
        }
    var rightBtnClickListener: RightBtnClickListener? = null
    var backDrawable: Drawable? = null
    var titleColor: Int? = Color.parseColor("#333333")
    var blackBack: Boolean? = true
    var rightStrColor: Int? = Color.parseColor("#666666")

    var tvTitle: TextView? = null
    var ivBack: ImageView? = null
    var line: View? = null
    var tvRight: TextView? = null
    var rootView: RelativeLayout? = null


    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
            context,
            attrs,
            defStyleAttr
    ) {
        init(context, attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?) : this(context, null, 0)


    private fun init(context: Context?, attrs: AttributeSet?) {
        val a = context!!.obtainStyledAttributes(attrs, R.styleable.HeadView)

        for (i in 0 until a.indexCount) {
            when (a.getIndex(i)) {
                R.styleable.HeadView_title -> title = a.getString(R.styleable.HeadView_title)

                R.styleable.HeadView_rightBtnStr -> rightBtnStr =
                        a.getString(R.styleable.HeadView_rightBtnStr)

                R.styleable.HeadView_showRightBtn -> showRightBtn =
                        a.getBoolean(R.styleable.HeadView_showRightBtn, false)

                R.styleable.HeadView_backColor -> backDrawable =
                        a.getDrawable(R.styleable.HeadView_backColor)

                R.styleable.HeadView_showLine -> showLine =
                        a.getBoolean(R.styleable.HeadView_showLine, true)

                R.styleable.HeadView_titleColor -> titleColor =
                        a.getColor(R.styleable.HeadView_titleColor, Color.parseColor("#333333"))

                R.styleable.HeadView_blackBack -> blackBack =
                        a.getBoolean(R.styleable.HeadView_blackBack, true)

                R.styleable.HeadView_rightBtnColor -> rightStrColor =
                        a.getColor(R.styleable.HeadView_rightBtnColor, Color.parseColor("#666666"))
            }
        }

        LayoutInflater.from(context).inflate(R.layout.layout_head, this, true)
        a.recycle()
    }

    @Suppress("DEPRECATION")
    override fun onFinishInflate() {
        super.onFinishInflate()
        tvTitle = findViewById(R.id.tv_title)
        ivBack = findViewById(R.id.iv_back)
        line = findViewById(R.id.line)
        tvRight = findViewById(R.id.tv_right)
        rootView = findViewById(R.id.rootView)

        line?.visibility = if (showLine!!) View.VISIBLE else View.GONE
        tvRight?.visibility = if (showRightBtn!!) View.VISIBLE else View.GONE

        title?.let {
            tvTitle?.text = title
        }
        titleColor?.let {
            tvTitle?.setTextColor(it)
        }

        rightBtnStr?.let {
            tvRight?.text = rightBtnStr
        }
        rightStrColor?.let {
            tvRight?.setTextColor(it)
        }

        backDrawable?.let {
            rootView?.setBackgroundDrawable(it)
        }
        ivBack?.setImageResource(if (blackBack!!) R.mipmap.ic_head_back_black else R.mipmap.ic_head_back_white)
        ivBack?.setOnClickListener {
            if (backClickListener == null) {
                if (context is Activity) (context as Activity).onBackPressed()
            } else {
                backClickListener?.onBackClick()
            }
        }
        tvRight?.setOnClickListener {
            rightBtnClickListener?.onBtnClick()
        }
    }

    fun showRightbtn(show:Boolean){
        tvRight?.visibility = if(show) View.VISIBLE else View.GONE
    }

    fun setTitleStr(title:String?){
        tvTitle?.text = title
    }


    interface BackClickListener {
        fun onBackClick()
    }

    interface RightBtnClickListener {
        fun onBtnClick()
    }
}