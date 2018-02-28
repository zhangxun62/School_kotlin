package com.kotlin.test.base

import android.annotation.SuppressLint
import android.content.Intent
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
    protected abstract fun getContentView(): View?

    /**
     * 通过资源id来获取View
     * @return Int
     */
    protected abstract fun getContentViewLayoutId(): Int

    /**
     *
     */
    protected abstract fun initViews()

    protected abstract fun initData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var rooView = getContentView()
        if (rooView == null) {
            setContentView(getContentViewLayoutId())
        } else {
            setContentView(rooView)
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

    override fun onDestroy() {
        super.onDestroy()
        ActivityCollector.removeActivity(this)
    }


    /**
     * 添加Fragment
     * @param containerId
     * @param fragment
     */
    @SuppressLint("RestrictedApi")
    fun addFragment(containerId: Int, fragment: Fragment) {
        var transaction = supportFragmentManager.beginTransaction()
        var fragments = supportFragmentManager.fragments
        if (fragments != null) {
            fragments.filter { it.id == fragment.id }.map { transaction.hide(it) }
        }
        if (!fragment.isAdded) {
            transaction.add(containerId, fragment, fragment.javaClass.simpleName)
        }
        transaction.show(fragment)
        transaction.commit()
    }

    fun startActivity(targetActivity: Class<*>) {
        startActivity(Intent(this, targetActivity))
    }

    fun startActivity(targetActivity: Class<*>, bundle: Bundle?) {
        startActivity(Intent(this, targetActivity).putExtras(bundle))
    }

    fun startActivityForResult(cls: Class<*>, bundle: Bundle?, requestCode: Int) {
        val intent = Intent()
        intent.setClass(this, cls)
        intent.putExtras(bundle)
        startActivityForResult(intent, requestCode)
    }


}