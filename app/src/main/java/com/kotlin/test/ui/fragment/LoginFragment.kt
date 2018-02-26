package com.kotlin.test.ui.fragment

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.google.gson.Gson
import com.kotlin.test.R
import com.kotlin.test.http.HttpClient
import com.kotlin.test.ui.activity.BaseActivity
import com.kotlin.test.ui.activity.MainActivity
import com.kotlin.test.util.ActivityCollector
import com.kotlin.test.util.Preference
import com.kotlin.test.util.RsaUtil
import com.kotlin.test.util.RxJavaUtil
import com.kotlin.test.widget.ClearEditText
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_login.*
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.HttpException
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit


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
        var token: String by Preference(context, "token", "")
        if (!token.isNullOrEmpty()) {
            startActivity(Intent(context, MainActivity::class.java))
            ActivityCollector.removeActivity(context)
            (context as Activity).finish()

        }
        // 自动写入常用登录名
        var accountText by Preference(context, "account", "");
        if (!accountText.isNullOrEmpty()) {
            (R.id.id_et_account as TextView).text = accountText
        }

        id_tv_register.setOnClickListener(this)
        RxJavaUtil.clickView(id_btn_login).throttleFirst(1000, TimeUnit.MILLISECONDS).subscribe {
            initLogin()
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.id_tv_register -> {
                (context as BaseActivity).addFragment(R.id.id_container, RegisterFragment())
            }
            else -> {

            }
        }
    }

    /**
     * 登录
     */
    private fun initLogin() {
        var account = findId<ClearEditText>(R.id.id_et_account)
        var password = findId<EditText>(R.id.id_et_password)
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
        map["account"] = account.text.toString()
        map["password"] = password.text.toString()
        var data = RsaUtil.encryptWithRSA(Gson().toJson(map))
        var body: RequestBody = RequestBody.create(MediaType.parse("text/plain"), data)
        HttpClient().mApi.login(body).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({ t ->
            Preference(context, "account", map["account"])
            // 遍历实体类 取得实体类属性和类型，属性值
            for (field in t.data.javaClass.declaredFields) {
                field.isAccessible = true
                Log.d(field.name, field.get(t.data).toString())
                Preference(context, field.name, field.get(t.data))
            }
            startActivity(Intent(context, MainActivity::class.java))
            ActivityCollector.removeActivity(context)
            (context as Activity).finish()
        }, { t ->
            t.printStackTrace()
            if (t is HttpException) {
                var e: HttpException = t
                var msg = e.response().errorBody()!!.string()
                Log.i("测试", msg)
                toast(JSONObject(msg)["msg"] as String)
            }

        })
    }

    fun setAccount(text: String) {
        var account = findId<ClearEditText>(R.id.id_et_account)
        account.setText(text)
    }
}