package com.csc.phoneutils.utils

import android.content.Context
import android.content.SharedPreferences
import com.csc.phoneutils.app.MyApp

/**
 * Author: 蔡小树
 * Time: 2019/11/18 11:05
 * Description:
 */

object SPUtils {
    private val name = "APP_Config"
    private val sp: SharedPreferences by lazy {
        MyApp.instance.applicationContext.getSharedPreferences(name, Context.MODE_PRIVATE)
    }

    @Suppress("UNCHECKED_CAST")
    fun getValue(key: String, default: Any): Any = with(sp) {
        return when (default) {
            is Int -> getInt(key, default)
            is String -> this.getString(key, default)!!
            is Long -> getLong(key, default)
            is Float -> getFloat(key, default)
            is Boolean -> getBoolean(key, default)
            else -> throw IllegalArgumentException("SharePreferences 类型错误")
        }
    }

    fun getInt(key: String, default: Int = 0) = getValue(key, default) as Int
    fun getString(key: String, default: String = "") = getValue(key, default) as String
    fun getLong(key: String, default: Long = 0) = getValue(key, default) as Long
    fun getFloat(key: String, default: Float = 0f) = getValue(key, default) as Float
    fun getBoolean(key: String, default: Boolean = false) = getValue(key, default) as Boolean

    fun putValue(key: String, value: Any) = with(sp.edit()) {
        when (value) {
            is Int -> putInt(key, value)
            is String -> putString(key, value)
            is Long -> putLong(key, value)
            is Float -> putFloat(key, value)
            is Boolean -> putBoolean(key, value)
            else -> throw IllegalArgumentException("SharePreferences 类型错误")
        }.apply()
    }

    fun remove(key: String) {
        sp.edit().remove(key).apply()
    }

    fun clear() {
        sp.edit().clear().apply()
    }

}