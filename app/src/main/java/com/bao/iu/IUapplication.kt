package com.bao.iu

import android.app.Application
import cn.bmob.v3.Bmob
import cn.finalteam.galleryfinal.*

/**
 * Created by Administrator on 2017/9/26.
 */
class IUapplication: Application() {
    override fun onCreate() {
        //初始化Bmob f865b5dccb2e76437ec331bde634e553为Bmob的APPID
        Bmob.initialize(this,"f865b5dccb2e76437ec331bde634e553")
        //初始化GalleryFinal
        var theme = ThemeConfig.Builder()
                .setTitleBarBgColor(R.color.iu_back)//标题栏背景颜色//标题栏背景颜色
            .build()
        var functionCofing = FunctionConfig.Builder()
                .setEnableCamera(true)
                .setEnableEdit(true)
                .setEnableCrop(true)
                .setEnableRotate(true)
                .setCropSquare(true)
                .setEnablePreview(true)
                .build()
        var imageLoader = IUImageLoader()

        var coreConfig = CoreConfig.Builder(this,imageLoader,theme)
                .setDebug(BuildConfig.DEBUG)
                .setFunctionConfig(functionCofing)
                .build()
        GalleryFinal.init(coreConfig)
        super.onCreate()
    }
}