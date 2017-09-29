package com.bao.iu.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import com.bao.iu.fragment.f_Article.QueryFragment
import com.bao.iu.fragment.f_Weather.WeatherFragment

/**
 * Created by Administrator on 2017/9/28.
 */

class MyPageAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    private var title = arrayOf("天气", "日志")
    private var f1: Fragment? = null
    private var f2: Fragment? = null
    override fun getItem(position: Int): Fragment {
        when (position) {
            0 ->{
                if(f1 == null) return  WeatherFragment()
                else return f1 as Fragment
            }
            1 -> {
                if(f2 == null) return QueryFragment()
                else return f2 as Fragment
            }
            else -> {
                return QueryFragment()
            }
        }
    }

    override fun getCount(): Int {
        return title.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return title[position]
    }

    override fun getItemPosition(`object`: Any?): Int {
        return PagerAdapter.POSITION_NONE
    }

    fun updateFragment(f1: Fragment?,f2: Fragment?){
        this.f1 = f1
        this.f2 = f2
        notifyDataSetChanged()
    }

}

