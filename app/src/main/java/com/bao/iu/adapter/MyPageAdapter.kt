package com.bao.iu.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.bao.iu.fragment.f_Article.QueryFragment
import com.bao.iu.fragment.f_Weather.WeatherFragment

/**
 * Created by Administrator on 2017/9/28.
 */

class MyPageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    private var title = arrayOf("天气", "日志")

    override fun getItem(position: Int): Fragment {
        var f:Fragment? = null
        when (position) {
            0 ->{
                f = WeatherFragment()
            }
            1 -> {
                f = QueryFragment()
            }
            else -> {
                f = QueryFragment()
            }
        }
        return f!!
    }

    override fun getCount(): Int {
        return title.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return title[position]
    }

}

