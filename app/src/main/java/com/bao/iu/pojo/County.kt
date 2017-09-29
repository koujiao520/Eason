package com.bao.iu.pojo

import org.litepal.crud.DataSupport

/**
 * Created by Administrator on 2017/9/28.
 */
class County: DataSupport() {
    //县或者市下区
    var id: Int? = 0
    var countyName: String? = null //县名称
    var weatherId: Int? = 0 //天气id
    var cityId: Int? = 0 //城市id
}