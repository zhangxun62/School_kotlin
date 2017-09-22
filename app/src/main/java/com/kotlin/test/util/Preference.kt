package com.kotlin.test.util

import android.content.Context
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * @Title Preference
 * @Description:
 * @Author: alvin
 * @Date: 2017/9/19.14:06
 * @E-mail: 49467306@qq.com
 */
class Preference<T>(val context: Context, private val key: String, private val default: T) : ReadWriteProperty<Any?, T> {
    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return findPreference(key, default)
    }


    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        putPreference(key, default)
    }

    private val prefs by lazy { context.getSharedPreferences("default", Context.MODE_PRIVATE) }
    private fun <U> findPreference(key: String, default: U): U = with(prefs) {
        val res: Any = when (default) {
            is Long -> getLong(key, default)
            is String -> getString(key, default)
            is Int -> getInt(key, default)
            is Boolean -> getBoolean(key, default)
            is Float -> getFloat(key, default)
            else -> throw IllegalArgumentException("This type can be saved into Preferences")
        }
        res as U
    }

    private fun <U> putPreference(key: String, default: U) = with(prefs.edit()) {
        when (default) {
            is Long -> putLong(key, default)
            is String -> putString(key, default)
            is Int -> putInt(key, default)
            is Boolean -> putBoolean(key, default)
            is Float -> putFloat(key, default)
            else -> throw IllegalArgumentException("This type can be saved into Preferences")
        }.apply()
    }

}