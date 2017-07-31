package com.kotlin.test.widget


import android.content.Context
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.support.v4.widget.TextViewCompat
import android.support.v7.widget.AppCompatEditText
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

import com.kotlin.test.R


/**
 * @Title ClearEditText
 * *
 * @Description: 带消除的EditText
 * *
 * @Author: alvin
 * *
 * @Date: 2017/4/20.15:17
 * *
 * @E-mail: 49467306@qq.com
 */

class ClearEditText @JvmOverloads constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int = android.R.attr.editTextStyle) : AppCompatEditText(context, attrs, defStyleAttr), TextWatcher, View.OnFocusChangeListener {
    /**
     * 右边清除图标
     */
    private var mRightDrawable: Drawable? = null
    private var mHasFouse: Boolean = false

    init {
        addTextChangedListener(this)
        onFocusChangeListener = this
        init()

    }

    /**
     * 初始化
     */
    private fun init() {
        //如果没有设置drawRight,则使用默认值
        mRightDrawable = if (compoundDrawables[2] == null)
            resources.getDrawable(R.drawable.ic_cancel)
        else
            compoundDrawables[2]

        mRightDrawable!!.colorFilter = PorterDuffColorFilter(resources.getColor(android.R.color.darker_gray), PorterDuff.Mode.SRC_IN)

    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        if (mHasFouse) {
            setClearIconVisible(length() > 0)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        //判断单击的位置 是否在清除图标上
        val touchable = event.x > width - totalPaddingRight && event.x < width - paddingRight
        when (event.action) {
            MotionEvent.ACTION_DOWN -> if (touchable) {
                mRightDrawable!!.colorFilter = PorterDuffColorFilter(resources.getColor(R.color.theme_color), PorterDuff.Mode.SRC_IN)
                TextViewCompat.setCompoundDrawablesRelativeWithIntrinsicBounds(this, compoundDrawables[0],
                        compoundDrawables[1], mRightDrawable, compoundDrawables[3])
            }
            MotionEvent.ACTION_UP -> {
                mRightDrawable!!.colorFilter = PorterDuffColorFilter(resources.getColor(android.R.color.darker_gray), PorterDuff.Mode.SRC_IN)
                if (mRightDrawable != null) {
                    if (touchable) {
                        text = null
                    }
                }
            }
            else -> {
            }
        }

        return super.onTouchEvent(event)
    }

    override fun afterTextChanged(s: Editable) {

    }

    /*
     * 设置清除图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去
     *
     * @param visible
     */
    protected fun setClearIconVisible(visible: Boolean) {
        val right = if (visible) mRightDrawable else null
        TextViewCompat.setCompoundDrawablesRelativeWithIntrinsicBounds(this, compoundDrawables[0],
                compoundDrawables[1], right, compoundDrawables[3])
    }

    override fun onFocusChange(v: View, hasFocus: Boolean) {
        mHasFouse = hasFocus
        if (mHasFouse) {
            setClearIconVisible(length() > 0)
        } else {
            setClearIconVisible(mHasFouse)
        }
    }
}
