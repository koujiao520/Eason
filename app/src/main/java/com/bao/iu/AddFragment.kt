package com.bao.iu

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import cn.bmob.v3.datatype.BmobFile
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.SaveListener
import cn.bmob.v3.listener.UploadFileListener
import cn.finalteam.galleryfinal.GalleryFinal
import cn.finalteam.galleryfinal.model.PhotoInfo
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_add.*
import java.io.File

/**
 * Created by Administrator on 2017/9/22.
 */
class AddFragment : Fragment(), View.OnClickListener {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater?.inflate(R.layout.fragment_add, container, false)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        add_a_img.setOnClickListener(this)
        add_fab.setOnClickListener(this)
        add_ok.setOnClickListener(this)
        add_exit.setOnClickListener(this)
        //新建悬浮按钮隐藏
        activity.myFab.visibility = View.GONE
    }

    //******************************
    private val CHOOSE_PHOTO = 2
    private var imgUrl: String? = null
    private var handler = object : Handler() {
        override fun handleMessage(msg: Message?) {
            when (msg?.what) {
                1 -> {
                    //上传数据及文件
                    //获取网络连接状态
                    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                    val nt = cm.activeNetworkInfo
                    var flagNetwork = (nt != null && nt.isAvailable)
                    if (flagNetwork) {
                        //有网络
                        val imageFile = BmobFile(File(imgUrl))
                        imageFile.uploadblock(object : UploadFileListener() {
                            override fun done(e: BmobException?) {
                                if (e == null) {
                                    val a = Article(add_a_title.text.toString(), add_a_contents.text.toString(), 1, imageFile.url)
                                    a.save(object : SaveListener<String>() {
                                        override fun done(str: String?, e1: BmobException?) {
                                            if (e1 == null) {
                                                activity.onBackPressed()
                                            } else {
                                                Toast.makeText(activity, "上传失败 message ${e1.message} ", Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                    })
                                } else {
                                    Toast.makeText(activity, "上传文件失败 message ${e.message} ", Toast.LENGTH_SHORT).show()
                                }
                            }
                        })
                    }else{
                        Toast.makeText(activity,"请打开网络。。。",Toast.LENGTH_SHORT).show()
                    }
                }
                else -> {
                }
            }
        }
    }
    var mOnHanlderResultCallback = object : GalleryFinal.OnHanlderResultCallback{
        override fun onHanlderSuccess(reqeustCode: Int, resultList: MutableList<PhotoInfo>?) {
            when (reqeustCode) {
                CHOOSE_PHOTO->{
                    imgUrl = resultList?.get(0)?.photoPath
                    Glide.with(activity).load(imgUrl).into(add_a_img)
                }
                else -> {
                }
            }
        }

        override fun onHanlderFailure(requestCode: Int, errorMsg: String?) {

        }
    }


    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.add_a_img -> {
                GalleryFinal.openGallerySingle(CHOOSE_PHOTO, mOnHanlderResultCallback);
            }
            R.id.add_fab,R.id.add_ok -> {
                //悬浮按钮
                if (imgUrl == null) {
                    Toast.makeText(activity, "请选择图片", Toast.LENGTH_SHORT).show()
                    return
                }
                Thread(object : Runnable {
                    override fun run() {
                        val message = Message()
                        message.what = 1
                        message.obj = view
                        handler.sendMessage(message)
                    }
                }).start()
            }
            R.id.add_exit -> {
                //back
                activity.onBackPressed()
            }
            else -> {

            }
        }
    }
}