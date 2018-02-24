package com.kotlin.test.ui.activity

import android.view.View
import com.kotlin.test.R
import com.kotlin.test.ui.fragment.LoginFragment

class LoginActivity : BaseActivity() {
    override fun getContentView(): View? {
        return null
    }

    override fun getContentViewLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun initViews() {
    }

    override fun initData() {
        addFragment(R.id.id_container, LoginFragment())
    }

    override fun onClick(v: View?) {
    }

    fun reLogin(account: String) {
        var fragment: LoginFragment = supportFragmentManager.findFragmentByTag(LoginFragment::class.java.simpleName) as LoginFragment
        fragment.setAccount(account)
        addFragment(R.id.id_container, fragment)
    }

}
