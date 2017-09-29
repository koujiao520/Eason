package com.bao.iu.pojo

import cn.bmob.v3.BmobObject

/**
 * Created by Administrator on 2017/9/19.
 */
 class Article: BmobObject{
    var title: String? = null // 标题
    var contents: String? = null // 内容
    var code: Int? = 0 // 状态码
    var imgUrl: String? = null //图片地址
    constructor()
    constructor(title: String?, contents: String?, code:Int?, imgUrl:String? ){
        this.title = title
        this.contents = contents
        this.code = code
        this.imgUrl = imgUrl
    }

}