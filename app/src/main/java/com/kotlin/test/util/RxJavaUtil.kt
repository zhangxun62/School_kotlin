package com.kotlin.test.util

import android.view.View
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposables
import android.os.Looper
import io.reactivex.android.MainThreadDisposable


/**
 * @Title RxJavaUtil
 * @Description:
 * @Author: alvin
 * @Date: 2017/9/19.10:16
 * @E-mail: 49467306@qq.com
 */
class RxJavaUtil {
    companion object {
        fun clickView(view: View): Observable<Object> {
            if (view == null)
                throw NullPointerException("view is null")
            return ViewClickObservable(view)
        }

        private class ViewClickObservable : Observable<Object> {
            var mView: View? = null

            constructor(v: View) {
                mView = v
            }

            override fun subscribeActual(p0: Observer<in Object>?) {
                if (!checkMainThread(p0!!))
                    return
                val listener = Listener(mView!!, p0 as Observer<in Any>)
                p0.onSubscribe(listener)
                mView!!.setOnClickListener(listener)

            }
        }

        fun checkMainThread(observer: Observer<*>): Boolean {
            if (Looper.myLooper() != Looper.getMainLooper()) {
                observer.onSubscribe(Disposables.empty())
                observer.onError(IllegalStateException(
                        "Expected to be called on the main thread but was " + Thread.currentThread().name))
                return false
            }
            return true
        }


    }

    internal class Listener(private val view: View, private val observer: Observer<in Any>) : MainThreadDisposable(), View.OnClickListener {

        override fun onClick(v: View) {
            if (!isDisposed) {
                observer.onNext(0)
            }
        }

        override fun onDispose() {
            view.setOnClickListener(null)
        }
    }

}