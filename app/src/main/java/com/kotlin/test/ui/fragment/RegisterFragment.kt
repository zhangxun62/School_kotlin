package com.kotlin.test.ui.fragment

import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.EditText
import com.google.gson.Gson
import com.kotlin.test.R
import com.kotlin.test.http.HttpClient
import com.kotlin.test.ui.activity.LoginActivity
import com.kotlin.test.util.RsaUtil
import com.kotlin.test.util.RxJavaUtil
import com.kotlin.test.widget.ClearEditText
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_register.*
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.HttpException
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * @Title RegisterFragment
 * @Description: 注册页面
 * @Author: alvin
 * @Date: 2017/8/1.13:57
 * @E-mail: 49467306@qq.com
 */
class RegisterFragment : BaseFragment() {
    override fun getFragmentViewByLayoutId(): Int {
        return R.layout.fragment_register
    }

    override fun getFragmentView(): View? {
        return null
    }

    override fun initData() {
        RxJavaUtil.clickView(id_btn_register).throttleFirst(1500, TimeUnit.MILLISECONDS).subscribe {
            submit()
        }
    }

    override fun onClick(v: View) {
        when (v.id) {

        }
    }

    private fun submit() {
        var account = findId<ClearEditText>(R.id.id_et_account)
        var password = findId<EditText>(R.id.id_et_password)
        if (TextUtils.isEmpty(account.text)) {
            account.error = "用户名不能为空"
            account.requestFocus()
            return
        }
        if (TextUtils.isEmpty(password.text)) {
            password.error = "密码不能为空"
            password.requestFocus()
            return
        }
        var map = HashMap<String, String>()
        map.put("account", account.text.toString())
        map.put("password", password.text.toString())
        var body: RequestBody = RequestBody.create(MediaType.parse("text/plain"), RsaUtil.encryptWithRSA(Gson().toJson(map)))
        HttpClient().mApi.register(body).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({ t ->
            toast(t.msg)
            (context as LoginActivity).reLogin(account.text.toString())
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
}