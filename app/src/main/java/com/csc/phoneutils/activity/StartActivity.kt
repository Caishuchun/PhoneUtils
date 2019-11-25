package com.csc.phoneutils.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.csc.phoneutils.R
import kotlinx.android.synthetic.main.activity_start.*
import org.jetbrains.anko.custom.async
import org.jetbrains.anko.uiThread

class StartActivity : AppCompatActivity() {

    val TAG = javaClass.name
    private var count = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        val storage =
            ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val phone = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
        val camera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        if (storage == PackageManager.PERMISSION_GRANTED
            && phone == PackageManager.PERMISSION_GRANTED
            && camera == PackageManager.PERMISSION_GRANTED
        ) {
            //三个都申请了的话
            tv_jump.setText(R.string.jump3)
            toShow()
        } else {
            //没有全部申请
            tv_jump.setText(R.string.get_permission)
            tv_jump.setOnClickListener {
                toGetStoragePermission()
            }
        }
    }


    /**
     * 获取权限
     */
    private fun toGetStoragePermission() {
        val checkSelfPermission =
            ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (checkSelfPermission != PackageManager.PERMISSION_GRANTED) {
            //如果没用同意权限,则去申请
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                1001
            )
        } else {
            toGetPhonePermission()
        }
    }

    private fun toGetPhonePermission() {
        val checkSelfPermission =
            ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
        if (checkSelfPermission != PackageManager.PERMISSION_GRANTED) {
            //如果没用同意权限,则去申请
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CALL_PHONE),
                1002
            )
        } else {
            toGetCameraPermission()
        }
    }

    private fun toGetCameraPermission() {
        val checkSelfPermission =
            ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        if (checkSelfPermission != PackageManager.PERMISSION_GRANTED) {
            //如果没用同意权限,则去申请
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                1003
            )
        } else {
            tv_jump.setText(R.string.jump3)
            toShow()
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (1001 == requestCode) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                toGetPhonePermission()
            } else {
                if (count == 0) {
                    finish()
                } else {
                    count--
                    toGetStoragePermission()
                }
            }
        }
        if (1002 == requestCode) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                toGetCameraPermission()
            } else {
                if (count == 0) {
                    finish()
                } else {
                    count--
                    toGetPhonePermission()
                }
            }
        }
        if (1003 == requestCode) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                tv_jump.setText(R.string.jump3)
                toShow()
            } else {
                if (count == 0) {
                    finish()
                } else {
                    count--
                    toGetCameraPermission()
                }
            }
        }

    }

    private fun toShow() {
        async {
            Thread.sleep(1000)
            uiThread {
                tv_jump.text = getString(R.string.jump2)
            }
            Thread.sleep(1000)
            uiThread {
                tv_jump.text = getString(R.string.jump1)
            }
            Thread.sleep(1000)
            uiThread {
                tv_jump.text = getString(R.string.jump)
                startActivity(Intent(this@StartActivity, MainActivity::class.java))
                finish()
            }
        }
    }


}
