package com.kotlin.test.ui.fragment

import android.util.Log
import android.view.View
import com.google.gson.Gson
import com.kotlin.test.R
import com.kotlin.test.http.HttpClient
import com.kotlin.test.ui.activity.BaseActivity
import com.kotlin.test.util.RsaUtil
import com.kotlin.test.widget.ClearEditText
import com.tencent.bugly.beta.global.e
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_login.*
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.Response
import retrofit2.HttpException

/**
 * @Title LoginFragment
 * @Description:
 * @Author: alvin
 * @Date: 2017/7/31.16:10
 * @E-mail: 49467306@qq.com
 */
class LoginFragment : BaseFragment() {
    override fun getFragmentView(): View? {
        return null
    }

    override fun getFragmentViewByLayoutId(): Int {
        return R.layout.fragment_login
    }

    override fun initData() {
        id_tv_register.setOnClickListener(this)
        id_btn_login.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.id_tv_register -> {
                (context as BaseActivity).addFragment(R.id.id_container, RegisterFragment())
            }
            R.id.id_btn_login -> {
                initLogin()
            }
            else -> {

            }
        }
    }

    fun initLogin() {
        var account = findId<ClearEditText>(R.id.id_et_account)
        var password = findId<ClearEditText>(R.id.id_et_password)
        if (account.text.isEmpty()) {
            account.error = "用户名不能为空"
            account.requestFocus()
            return
        }
        if (password.text.isEmpty()) {
            password.error = "密码不能为空"
            password.requestFocus()
            return
        }
        var map = HashMap<String, String>()
        map.put("account", account.text.toString())
        map.put("password", password.text.toString())
        Log.i("测试", RsaUtil.encryptWithRSA(Gson().toJson(map)))
        var body: RequestBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), Gson().toJson(map))
        HttpClient().mApi.register(body).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
            t ->
            toast(t)
            Log.i("测试", t)
        }, { t ->
            t.printStackTrace()
            var e: HttpException = t as HttpException

            Log.i("测试", e.response().errorBody()!!.string())

        })
    }
}