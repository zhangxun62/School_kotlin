package com.kotlin.test.ui.activity

import android.R
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.kotlin.test.util.ActivityCollector

/**
 * @Title BaseActivity
 * @Description:
 * @Author: alvin
 * @Date: 2017/6/8.16:03
 * @E-mail: 49467306@qq.com
 */
abstract class BaseActivity : AppCompatActivity(), View.OnClickListener {
    /**
     * 获取内容View
     * @return View
     */
    abstract protected fun getContentView(): View?

    /**
     * 通过资源id来获取View
     * @return Int
     */
    abstract protected fun getContentViewLayoutId(): Int


    abstract protected fun initViews()

    abstract protected fun initData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (getContentView() != null) {
            setContentView(getContentView())
        }
        else {
            setContentView(getContentViewLayoutId())
        }
        ActivityCollector.addActivity(this)

    }

    override fun onStart() {
        super.onStart()
        initViews()
        initData()
    }

    /**
     * 显示Toast提示框
     * @param msg
     */
    fun toast(msg: String, flag: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, msg, flag).show()
    }

    inline fun <reified T> debug(log: Any) {
        Log.d(T::class.simpleName, log.toString())
    }

    /**
     * 添加Fragment
     * @param containerId
     * @param fragment
     */
    fun addFragment(containerId: Int, fragment: Fragment) {
        var fragments: List<Fragment>? = supportFragmentManager.fragments
        if (fragment.isAdded) {
            if (fragments != null) {
                for (fragmentOld in fragments) {
                    supportFragmentManager.beginTransaction().hide(fragmentOld).commitAllowingStateLoss()
                }
            }
            supportFragmentManager.beginTransaction().show(fragment).commitAllowingStateLoss()
        } else {
            if (fragments != null) {
                for (fragmentOld in fragments) {
                    supportFragmentManager.beginTransaction().hide(fragmentOld).commitAllowingStateLoss()
                }
            }
            supportFragmentManager.beginTransaction().add(containerId, fragment, fragment.javaClass.simpleName).show(fragment).commitNowAllowingStateLoss()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityCollector.removeActivity(this)
    }
}