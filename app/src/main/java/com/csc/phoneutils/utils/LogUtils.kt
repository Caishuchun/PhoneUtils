package com.csc.phoneutils.utils

import android.util.Log

/**
 * Author: 蔡小树
 * Time: 2019/11/15 16:49
 * Description:
 */

object LogUtils {
    private const val isShow = true

    fun d(TAG: String, message: Any) {
        if (isShow) {
            Log.d(TAG, message.toString())
        }
    }

    fun e(TAG: String, message: Any) {
        if (isShow) {
            Log.e(TAG, message.toString())
        }
    }

    fun i(TAG: String, message: Any) {
        if (isShow) {
            Log.i(TAG, message.toString())
        }
    }

    fun w(TAG: String, message: Any) {
        if (isShow) {
            Log.w(TAG, message.toString())
        }
    }

}