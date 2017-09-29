package com.bao.iu.pojo

import org.litepal.crud.DataSupport

/**
 * Created by Administrator on 2017/9/28.
 */
class Province : DataSupport() {
    // 省
    var id: Int? = 0;
    var provinceName: String? = null // 省名
    var provinceCode: Int? = 0 //省代号
}