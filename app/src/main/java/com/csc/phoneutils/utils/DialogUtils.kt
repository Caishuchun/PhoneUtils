package com.csc.phoneutils.utils

import android.app.Activity
import android.app.ProgressDialog

/**
 * Author: 蔡小树
 * Time: 2019/11/15 18:26
 * Description:
 */

object DialogUtils {
    private var mDialog: ProgressDialog? = null

    fun showDialog(activity: Activity, message: Any) {
        mDialog = ProgressDialog(activity)
        mDialog!!.setMessage(message.toString())
        mDialog!!.show()
    }

    fun dismissDialog() {
        mDialog!!.dismiss()
    }
}