package com.csc.phoneutils.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.csc.phoneutils.R
import com.csc.phoneutils.app.MyApp
import com.csc.phoneutils.data.UserInfo
import com.csc.phoneutils.utils.DialogUtils
import com.csc.phoneutils.utils.LogUtils
import com.csc.phoneutils.utils.SPUtils
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import kotlinx.android.synthetic.main.activity_info.*
import org.jetbrains.anko.toast
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class InfoActivity : AppCompatActivity() {

    private val TAG = "InfoActivity"
    private var headPath = ""
    private var name = ""
    private var tel = ""
    private var address = ""
    private var isAdd = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        iv_back.setOnClickListener {
            if (isAdd) {
                setResult(Activity.RESULT_OK)
            }
            finish()
        }

        iv_head.setOnClickListener {
            toGetHead()
        }

        tv_add.setOnClickListener {
            name = et_name.text.toString().trim()
            tel = et_tel.text.toString().trim()
            address = et_address.text.toString().trim()
            when {
                headPath.isEmpty() -> toast(R.string.add_no_picture)
                name.isEmpty() -> toast(R.string.add_no_name)
                tel.isEmpty() -> toast(R.string.add_no_tel)
                address.isEmpty() -> toast(R.string.add_no_address)
                else -> toAdd(headPath, name, tel, address)
            }
        }
    }

    /**
     * 添加数据
     */
    private fun toAdd(headPath: String, name: String, tel: String, address: String) {
        DialogUtils.showDialog(this, getString(R.string.dialog_add))
        Thread(Runnable {
            Thread.sleep(500)
            val userInfo = UserInfo(tel, name, headPath, address)
            val addUser = MyApp.userDB.userDao.addUser(userInfo)
            LogUtils.d(TAG, "addUser:$addUser")
            runOnUiThread {
                DialogUtils.dismissDialog()
                val current = SPUtils.getLong("currentSize")
                if (addUser - current > 0) {
                    SPUtils.putValue("currentSize", addUser)
                    toast(R.string.add_success)
                    isAdd = true
                    iv_head.setImageResource(R.mipmap.normal_picture)
                    et_name.setText("")
                    et_tel.setText("")
                    et_address.setText("")
                } else {
                    toast(R.string.add_fail)
                }
            }
        }).start()

    }

    /**
     * 获取头像
     */
    private fun toGetHead() {
        PictureSelector.create(this)
            .openGallery(PictureMimeType.ofImage())
            .maxSelectNum(1)
            .minSelectNum(1)
            .selectionMode(PictureConfig.MULTIPLE)
            .previewImage(true)
            .forResult(1001)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && 1001 == requestCode) {
            val multipleResult = PictureSelector.obtainMultipleResult(data)
            val path = multipleResult[0].path
            LogUtils.d(TAG, "path:$path")
            Thread(
                Runnable {
                    val appFilePath = toCopyFile(path)
                    if (appFilePath.isNotEmpty()) {
                        headPath = appFilePath
                        LogUtils.d(TAG, "nwePath:$appFilePath")
                        runOnUiThread(Runnable {
                            Glide.with(this@InfoActivity)
                                .load(appFilePath)
                                .error(R.mipmap.error_picture)
                                .into(iv_head)
                        })
                    } else {
                        headPath = ""
                        toast(R.string.add_error_picture)
                    }
                }
            ).start()
        }
    }

    /**
     * 将照片copy到APP目录下,防止照片删除后造成地址到不到
     */
    @Suppress("DEPRECATION")
    private fun toCopyFile(path: String): String {
        val file = File(path)
        if (!file.exists()) {
            return ""
        }
        val inputStream = FileInputStream(file)
        val bytes = ByteArray(1024)
        val dir = Environment.getExternalStorageDirectory()
        val fileName = path.substring(path.lastIndexOf("/") + 1)
        val newPath = File(dir, "A_Phone_utils")
        if (!newPath.exists()) {
            newPath.mkdirs()
        }
        val newFile = File(newPath, fileName)
        val outputStream = FileOutputStream(newFile)
        while (inputStream.read(bytes) != -1) {
            outputStream.write(bytes)
        }
        inputStream.close()
        outputStream.close()
        return (newFile.absolutePath)
    }


    override fun onBackPressed() {
        if (isAdd) {
            setResult(Activity.RESULT_OK)
        }
        finish()
    }
}
