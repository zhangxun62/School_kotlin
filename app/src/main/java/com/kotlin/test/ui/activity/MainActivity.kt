package com.kotlin.test.ui.activity

import android.graphics.drawable.Drawable
import android.support.v4.app.Fragment
import android.view.KeyEvent
import android.view.View
import com.kotlin.test.R
import com.kotlin.test.base.BaseActivity
import com.kotlin.test.ui.fragment.HomeFragment
import com.kotlin.test.widget.mdtablayout.MDTabLayout
import com.kotlin.test.widget.mdtablayout.adapter.TabAdapter
import kotlinx.android.synthetic.main.activity_main.*

/**
 * 主界面
 */
class MainActivity : BaseActivity(), MDTabLayout.ItemCheckedListener {
    private var mFragments = arrayListOf<Fragment>()
    override fun onItemChecked(position: Int, view: View) {
        addFragment(R.id.id_container, mFragments[position])
    }


    override fun getContentView(): View? {
        return null
    }

    override fun getContentViewLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initViews() {

    }

    override fun initData() {
        for (mMenu in mMenus) {
            mFragments.add(HomeFragment.newInstance(mMenu))
        }
        addFragment(R.id.id_container, mFragments[0])
        id_tab_layout.setAdapter(MyAdapter(mMenus.size))
        id_tab_layout.setItemChecked(0)
        id_tab_layout.setItemCheckedListener(this)
    }

    /**
     * 控件的单击监听
     * @param v
     */
    override fun onClick(v: View) {
        when (v.id) {

        }
    }

    internal inner class MyAdapter(override val itemCount: Int) : TabAdapter() {
        override fun getImage(position: Int): Drawable {
            val res = resources.getIdentifier("icon" + position, "drawable", packageName)
            return resources.getDrawable(res)
        }

        override fun getText(position: Int): CharSequence {
            return mMenus[position]
        }
    }


    private var exitTime: Long = 0

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - exitTime > 2000) {
                toast("再按一次退出程序")
                exitTime = System.currentTimeMillis()
            } else {
                finish()
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    companion object {

        private val mMenus = arrayOf("首页", "提问", "个人")
    }

}
