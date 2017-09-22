package com.kotlin.test.ui.fragment

import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.EditText
import com.google.gson.Gson
import com.kotlin.test.R
import com.kotlin.test.http.HttpClient
import com.kotlin.test.util.RsaUtil
import com.kotlin.test.widget.ClearEditText
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_register.*
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.HttpException

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
        id_btn_register.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.id_btn_register -> {
                submit()
            }
            else -> {
            }
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
            Log.i("测试", Gson().toJson(t))
        }, { t ->
            t.printStackTrace()
            if (t is HttpException) {
                var e: HttpException = t
                Log.i("测试", e.response().errorBody()!!.string())
            }
        })
    }
}