package com.kotlin.test.widget.mdtablayout

import android.content.Context
import android.database.DataSetObserver
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutCompat
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import com.kotlin.test.R
import com.kotlin.test.widget.mdtablayout.adapter.TabAdapter


/**
 * @Title TabLayout
 * *
 * @Description:
 * *
 * @Author: alvin
 * *
 * @Date: 2016/10/26.16:14
 * *
 * @E-mail: 49467306@qq.com
 */
class MDTabLayout : LinearLayoutCompat, RippleButton.OnBeforeClickedListener {


    private var mAdapter: TabAdapter? = null
    /**
     * 选中颜色
     */
    private var mCheckedItemColor: Int = 0
    /**
     * 默认颜色
     */
    private var mNormalItemColor: Int = 0

    /**
     * 选中监听事件
     */
    private var mItemCheckedListener: ItemCheckedListener? = null
    /**
     * 选中的位置
     */
    private var mCheckedPosition: Int = 0
    /**
     * 字体放大百分比
     */
    private val mCheckedSizePercent: Float

    /**
     * 字体大小
     */
    private val mTextSize: Float

    /**
     * 子控件top和bottom的间隔
     */
    private val mTabPadding: Int

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet? = null) : this(context, attrs, 0)


    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        orientation = LinearLayout.HORIZONTAL
        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.MDTabLayout, defStyleAttr, 0)
        mCheckedItemColor = typedArray.getColor(R.styleable.MDTabLayout_checkedItemColor, resources.getColor(android.R.color.holo_orange_light))
        mNormalItemColor = typedArray.getColor(R.styleable.MDTabLayout_normalItemColor, 0xFF444444.toInt())
        mCheckedSizePercent = typedArray.getFraction(R.styleable.MDTabLayout_checked_percent, 1, 1, 1f)
        mTextSize = typedArray.getDimensionPixelSize(R.styleable.MDTabLayout_android_textSize, 15).toFloat()
        mTabPadding = typedArray.getDimensionPixelSize(R.styleable.MDTabLayout_tab_padding, 0)
        typedArray.recycle()
    }


    fun setAdapter(adapter: TabAdapter) {
        mAdapter = adapter
        mAdapter!!.registerObserver(mObserver)
        mAdapter!!.notifyDataSetChanged()

    }

    fun setCheckedItemColor(checkedItemColor: Int) {
        mCheckedItemColor = checkedItemColor
    }

    fun setNormalItemColor(normalItemColor: Int) {
        mNormalItemColor = normalItemColor
    }

    fun setItemCheckedListener(itemCheckedListener: ItemCheckedListener) {
        mItemCheckedListener = itemCheckedListener
    }

    private val mObserver = object : DataSetObserver() {
        override fun onChanged() {
            onInvalidated()
            if (null == mAdapter)
                return
            val itemCount = mAdapter!!.itemCount

            for (i in 0..itemCount - 1) {
                addView(buildRipple(i))
            }
        }

        override fun onInvalidated() {
            removeAllViews()
        }
    }


    /**
     * 创建子控件

     * @param pos
     * *
     * @return
     */
    private fun buildRipple(pos: Int): RippleButton {
        val ripple = RippleButton(context)
        ripple.gravity = Gravity.CENTER
        ripple.setRippleColor(Color.argb(34, 225, 64, 129))
        ripple.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize)
        ripple.setPadding(0, mTabPadding, 0, mTabPadding)
        ripple.setTextColor(mNormalItemColor)
        ripple.text = mAdapter!!.getText(pos)
        ripple.tag = pos
        var params: LinearLayoutCompat.LayoutParams = LinearLayoutCompat.LayoutParams(0, LayoutParams.MATCH_PARENT)
        params.weight = 1f
        ripple.layoutParams = params
        ripple.setCompoundDrawablesWithIntrinsicBounds(null, mAdapter!!.getImage(pos), null, null)
        ripple.setOnBeforeClickedListener(this)



        return ripple
    }

    override fun onBeforeClicked(view: View) {
        var pos: Int = view.tag as Int
        if (mItemCheckedListener != null && pos != mCheckedPosition) {
            mItemCheckedListener!!.onItemChecked(pos, getChildAt(pos))
        }
        itemChecked(pos)
    }

    /**
     * 子控件被选中

     * @param pos
     */
    private fun itemChecked(pos: Int) {
        mCheckedPosition = pos
        val itemCount = childCount
        var rippleButton: RippleButton
        var drawable: Drawable?
        for (i in 0..itemCount - 1) {
            rippleButton = getChildAt(i) as RippleButton
            /**
             * getCompoundDrawables()方法.
             * Returns drawables for the left, top, right, and bottom borders.
             * 该方法返回包含控件左,上,右,下四个位置的Drawable的数组
             */
            rippleButton.cancel()
            drawable = rippleButton.compoundDrawables[1]
            if (i == mCheckedPosition) {
                rippleButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, mCheckedSizePercent * mTextSize)
                rippleButton.setTextColor(mCheckedItemColor)
                if (null != drawable) {
                    drawable.colorFilter = PorterDuffColorFilter(mCheckedItemColor,
                            PorterDuff.Mode.SRC_IN)
                    //                    drawable.setColorFilter();
                }
                continue
            }
            rippleButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize)
            rippleButton.setTextColor(mNormalItemColor)
            if (drawable != null) {
                drawable.clearColorFilter()
            }

        }
    }

    /**
     * 设置默认选中的子控件

     * @param itemChecked
     */
    fun setItemChecked(itemChecked: Int) {
        itemChecked(itemChecked)
    }

    interface ItemCheckedListener {
        fun onItemChecked(position: Int, view: View)
    }

    /**
     * 与viewpager绑定

     * @param pager
     */
    fun setupWithViewPager(pager: ViewPager) {
        pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                itemChecked(position)
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
    }


}
