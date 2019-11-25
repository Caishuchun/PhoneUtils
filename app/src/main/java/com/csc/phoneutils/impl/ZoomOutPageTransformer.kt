package com.csc.phoneutils.impl

import android.view.View
import androidx.viewpager.widget.ViewPager
import kotlin.math.abs

/**
 * Author: 蔡小树
 * Time: 2019/11/22 15:09
 * Description:
 */
class ZoomOutPageTransformer : ViewPager.PageTransformer {

    private val MAX_SCALE = 1f
    private val MIN_SCALE = 0.8f

    override fun transformPage(page: View, position: Float) {
        if (abs(position) <= 1) {
            val fl = MAX_SCALE + (1 - abs(position)) * (MAX_SCALE - MIN_SCALE)
            page.scaleX = fl
            page.scaleY = fl
            if (position > 0) {
                page.translationX = -fl * 2
            } else if (position < 0) {
                page.translationX = fl * 2
            }
        } else {
            page.scaleX = MIN_SCALE
            page.scaleY = MIN_SCALE
        }
    }
}