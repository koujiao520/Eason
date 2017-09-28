package com.bao.iu

import cn.bmob.v3.BmobObject

/**
 * Created by Administrator on 2017/9/19.
 */
 class Article: BmobObject{
    var title: String? = null
    var contents: String? = null
    var code: Int? = 0
    var imgUrl: String? = null
    constructor()
    constructor(title: String?, contents: String?, code:Int?, imgUrl:String? ){
        this.title = title
        this.contents = contents
        this.code = code
        this.imgUrl = imgUrl
    }

}