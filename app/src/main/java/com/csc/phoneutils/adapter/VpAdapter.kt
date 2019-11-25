package com.csc.phoneutils.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.csc.phoneutils.R
import com.csc.phoneutils.data.UserInfo
import com.csc.phoneutils.utils.LogUtils
import kotlinx.android.synthetic.main.viewpager_item.view.*

/**
 * Author: 蔡小树
 * Time: 2019/11/22 14:33
 * Description:
 */
class VpAdapter(private val context: Context, private val userInfoLists: List<UserInfo>) :
    PagerAdapter() {

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return userInfoLists.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view =
            LayoutInflater.from(context).inflate(R.layout.viewpager_item, container, false)
        val userInfo = userInfoLists[position]
        Glide.with(context)
            .load(userInfo.picture)
            .into(view.iv_head)
        view.tv_name.text = "${context.getString(R.string.item_name)}${userInfo.name}"
        view.tv_tel.text = "${context.getString(R.string.item_tel)}${userInfo.tel}"
        view.tv_address.text = "${context.getString(R.string.item_address)}${userInfo.address}"
        view.setOnLongClickListener {
            LogUtils.d(javaClass.name, "我被点击了:${userInfo.name}")
            val intent = Intent() // 意图对象：动作 + 数据
            intent.action = Intent.ACTION_CALL // 设置动作
            val data = Uri.parse("tel:" + userInfo.tel) // 设置数据
            intent.data = data
            context.startActivity(intent) // 激活Activity组件
            return@setOnLongClickListener true
        }
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}