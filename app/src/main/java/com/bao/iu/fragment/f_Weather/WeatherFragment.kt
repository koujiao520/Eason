package com.bao.iu.fragment.f_Weather

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bao.iu.R

/**
 * Created by Administrator on 2017/9/28.
 */
class WeatherFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_weather,container,false)
        return view
    }
}