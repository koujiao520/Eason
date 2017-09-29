package com.bao.iu.pojo

import org.litepal.crud.DataSupport

/**
 * Created by Administrator on 2017/9/28.
 */
class City: DataSupport() {
    //城市
    var id: Int? = 0
    var cityName: String? = null //城市名称
    var cityCode: Int? = 0 // 城市编号
    var provinceId: Int? = 0 //省编号
}