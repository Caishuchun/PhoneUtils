package com.csc.phoneutils.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.csc.phoneutils.R
import com.csc.phoneutils.adapter.VpAdapter
import com.csc.phoneutils.app.MyApp
import com.csc.phoneutils.impl.ZoomOutPageTransformer
import com.csc.phoneutils.utils.DialogUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val TAG = javaClass.name
    private val ADD_CODE = 1001
    private val DELETE_CODE = 1002

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        iv_add.setOnLongClickListener {
            val intent = Intent(this, InfoActivity::class.java)
            startActivityForResult(intent, ADD_CODE)
            return@setOnLongClickListener true
        }
        iv_item.setOnLongClickListener {
            val intent = Intent(this, DeleteActivity::class.java)
            startActivityForResult(intent, DELETE_CODE)
            return@setOnLongClickListener true
        }
        toGetData()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                ADD_CODE, DELETE_CODE -> {
                    toGetData()
                }
            }
        }
    }


    /**
     * 获取数据
     */
    private fun toGetData() {
        DialogUtils.showDialog(this, getString(R.string.dialog_getInfo))
        Thread(Runnable {
            Thread.sleep(500)
            val userDao = MyApp.userDB.userDao
            val infos = userDao.getInfoes()
            runOnUiThread {
                DialogUtils.dismissDialog()
                if (infos!!.isEmpty()) {
                    vp_show.visibility = View.GONE
                    tv_nothing.visibility = View.VISIBLE
                } else {
                    vp_show.visibility = View.VISIBLE
                    tv_nothing.visibility = View.GONE
                    vp_show.adapter = VpAdapter(this, infos)
                    vp_show.pageMargin = 20
                    vp_show.offscreenPageLimit = infos.size
                    vp_show.setPageTransformer(true, ZoomOutPageTransformer())
                    vp_show.currentItem = 1
                    rl_main.setOnTouchListener { _, event -> vp_show.dispatchTouchEvent(event) }
                }
            }
        }).start()
    }
}
