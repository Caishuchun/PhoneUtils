package com.csc.phoneutils.activity

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.csc.phoneutils.R
import com.csc.phoneutils.app.MyApp
import com.csc.phoneutils.utils.DialogUtils
import com.csc.phoneutils.utils.LogUtils
import com.csc.phoneutils.utils.SPUtils
import kotlinx.android.synthetic.main.activity_delete.*
import org.jetbrains.anko.toast

class DeleteActivity : AppCompatActivity() {

    private val TAG = javaClass.name
    private var isDelete = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete)

        iv_back.setOnClickListener {
            if (isDelete) {
                setResult(Activity.RESULT_OK)
            }
            finish()
        }

        tv_delete.setOnClickListener {
            val tel = et_tel.text.toString().trim()
            toDelete(tel)
        }
    }

    private fun toDelete(tel: String) {
        DialogUtils.showDialog(this, getString(R.string.dialog_delete))
        Thread(Runnable {
            Thread.sleep(500)
            val userDao = MyApp.userDB.userDao
            val delete = userDao.deleteUser(tel)
            LogUtils.d(TAG, "delete:$delete")
            runOnUiThread {
                DialogUtils.dismissDialog()
                if (delete == 1) {
                    isDelete = true
                    toast(R.string.delete_success)
                    et_tel.setText("")
                    SPUtils.putValue("currentSize", SPUtils.getLong("currentSize") - 1L)
                } else {
                    toast(R.string.delete_fail)
                }
            }
        }).start()
    }

    override fun onBackPressed() {
        if (isDelete) {
            setResult(Activity.RESULT_OK)
        }
        finish()
    }
}
