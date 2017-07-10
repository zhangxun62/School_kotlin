package com.kotlin.test.widget.mdtablayout

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.kotlin.test.R


/**
 * @Title RippleButton
 * *
 * @Description:
 * *
 * @Author: alvin
 * *
 * @Date: 2016/10/26.15:10
 * *
 * @E-mail: 49467306@qq.com
 */
class RippleButton : AppCompatTextView {

    /**
     * 波纹画笔
     */
    private val mPaint: Paint
    /**
     * 波纹变化幅度
     */
    private var mStepSize: Int = 0
    /**
     * 波纹从多少开始变化
     */
    private val mMinRadius = 0
    /**
     * 波纹当前大小
     */
    private var mCurrentRadius = 0
    /**
     * 波纹最大值
     */
    private var mMaxRadius: Int = 0
    /**
     * 波纹中心坐标
     */
    private var mCenterX: Int = 0
    private var mCenterY: Int = 0
    /**
     * 判断是否正在进行动画
     */
    private var isAnimating: Boolean = false

    /**
     * 自定义点击之前的监听事件
     */
    private var mListener: OnBeforeClickedListener? = null

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet? = null) : this(context, attrs, 0)


    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        if (attrs != null)
            resolveAttrs(context, attrs, defStyleAttr)
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mMaxRadius = Math.max(measuredWidth, measuredHeight) / 2
        mCenterX = measuredWidth / 2
        mCenterY = measuredHeight / 2
        mStepSize = mMaxRadius / 20
    }

    private fun resolveAttrs(context: Context, attrs: AttributeSet, defStyle: Int) {
        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.MDTabLayout, defStyle, 0)
        mPaint.color = typedArray.getColor(R.styleable.MDTabLayout_rippleColor, Color.GREEN)
        typedArray.recycle()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            if (null != mListener)
                mListener!!.onBeforeClicked(this)
            mCurrentRadius = mMinRadius
            isAnimating = true
            postInvalidate()
        }
        return true
    }

    override fun onDraw(canvas: Canvas) {
        if (!isAnimating) {
            super.onDraw(canvas)
            return
        }
        /**
         * 如果当前波纹半径大于最大半径则停止重绘波纹
         * 并执行onclick事件
         */
        if (isAnimating && mCurrentRadius > mMaxRadius) {
            isAnimating = false
            mCurrentRadius = mMinRadius
            performClick()
            super.onDraw(canvas)
            return
        }
        /**
         * 当前波纹小于最大半径,则进行重绘
         */
        mCurrentRadius = mCurrentRadius + mStepSize
        canvas.drawCircle(mCenterX.toFloat(), mCenterY.toFloat(), mCurrentRadius.toFloat(), mPaint)
        super.onDraw(canvas)
        postInvalidate()
    }

    fun setOnBeforeClickedListener(li: OnBeforeClickedListener) {
        mListener = li
    }

    interface OnBeforeClickedListener {
        fun onBeforeClicked(view: View)
    }

    fun setRippleColor(color: Int) {
        mPaint.color = color
    }

    fun cancel() {
        isAnimating = false
    }
}
