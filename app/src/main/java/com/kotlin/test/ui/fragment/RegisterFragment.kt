package com.kotlin.test.ui.fragment

import android.text.TextUtils
import android.view.View
import com.google.gson.Gson
import com.kotlin.test.R
import com.kotlin.test.http.HttpClient
import com.kotlin.test.widget.ClearEditText
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_register.*
import okhttp3.MediaType
import okhttp3.RequestBody

/**
 * @Title RegisterFragment
 * @Description:
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

    fun submit() {
        var account = findId<ClearEditText>(R.id.id_et_account)
        var password = findId<ClearEditText>(R.id.id_et_password)
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
        var body: RequestBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), Gson().toJson(map))
        HttpClient().mApi.login(body).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
            t ->
            toast(t)
        }, { t -> t.printStackTrace() })
    }
}