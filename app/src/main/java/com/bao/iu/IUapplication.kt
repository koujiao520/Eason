package com.bao.iu

import android.app.Application
import cn.bmob.v3.Bmob
import cn.bmob.v3.BmobConfig
import cn.finalteam.galleryfinal.*
import org.litepal.LitePalApplication

/**
 * Created by Administrator on 2017/9/26.
 */
class IUapplication: LitePalApplication() {
    override fun onCreate() {
//        //初始化Bmob f865b5dccb2e76437ec331bde634e553为Bmob的APPID
//        Bmob.initialize(this,"f865b5dccb2e76437ec331bde634e553")
        //第二：自v3.4.7版本开始,设置BmobConfig,允许设置请求超时时间、文件分片上传时每片的大小、文件的过期时间(单位为秒)，
        var config = BmobConfig.Builder(this)
        //设置appkey
        .setApplicationId("f865b5dccb2e76437ec331bde634e553")
        //请求超时时间（单位为秒）：默认15s
        .setConnectTimeout(30)
        //文件分片上传时每片的大小（单位字节），默认512*1024
        .setUploadBlockSize(1024*1024)
        //文件的过期时间(单位为秒)：默认1800s
        .setFileExpiration(2500)
        .build();
        Bmob.initialize(config);
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