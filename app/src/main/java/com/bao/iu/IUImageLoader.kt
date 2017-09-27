package com.bao.iu

import android.app.Activity
import android.graphics.drawable.Drawable
import cn.finalteam.galleryfinal.widget.GFImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.target.ImageViewTarget

/**
 * Created by Administrator on 2017/9/26.
 */

class IUImageLoader : cn.finalteam.galleryfinal.ImageLoader{
    override fun displayImage(activity: Activity?, path: String?, imageView: GFImageView?, defaultDrawable: Drawable?, width: Int, height: Int) {
        Glide.with(activity)
                .load("file://" + path)
                .placeholder(defaultDrawable)
                .error(defaultDrawable)
                .override(width, height)
                .diskCacheStrategy(DiskCacheStrategy.NONE) //不缓存到SD卡
                .skipMemoryCache(true)
                .into(object: ImageViewTarget<GlideDrawable>(imageView){
                    override fun setResource(resource: GlideDrawable?) {
                        imageView?.setImageDrawable(resource)
                    }
                })
    }

    override fun clearMemoryCache() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
