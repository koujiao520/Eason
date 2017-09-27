package com.bao.iu

import cn.bmob.v3.BmobObject
import cn.bmob.v3.datatype.BmobFile
import java.util.*

/**
 * Created by Administrator on 2017/9/19.
 */
 class Article: BmobObject{
    var title: String? = null
    var contents: String? = null
    var status: Int? = 0
    var imgUrl: String? = null
    constructor()
    constructor( title: String? , contents: String? , status:Int?,imgUrl:String? ){
        this.title = title
        this.contents = contents
        this.status = status
        this.imgUrl = imgUrl
    }

}