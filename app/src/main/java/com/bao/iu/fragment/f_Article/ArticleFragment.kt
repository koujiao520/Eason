package com.bao.iu.fragment.f_Article

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.UpdateListener
import com.bao.iu.pojo.Article
import com.bao.iu.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_article.*

/**
 * Created by Administrator on 2017/9/21.
 */
class ArticleFragment : Fragment() ,View.OnClickListener{

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_article,container,false)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         title = arguments.getString("title")
        val imgUrl = arguments.getString("imgUrl")
        val contents = arguments.getString("contents")
        objectId = arguments.getString("objectId")
        Glide.with(this).load(imgUrl).into(new_a_img)
        new_a_title.setText(title)
        new_a_contents.setText(contents)
        new_fab.setOnClickListener(this)

    }
    var title: String? = null
    var objectId: String? = null
    var handler = object: Handler(){
        override fun handleMessage(msg: Message?) {
            when (msg?.what) {
                2 -> {
                    //删除
                    //获取网络连接
                    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                    val nt = cm.activeNetworkInfo
                    var flagNetwork = (nt != null && nt.isAvailable)
                    if (flagNetwork) {
                        //有网
                        var a = Article()
                        a.code = 0
                        Log.i("@@@","执行删除")
                        a.update(objectId,object: UpdateListener(){
                            override fun done(e: BmobException?) {
                                if(e == null){
                                    Toast.makeText(activity,"删除成功",Toast.LENGTH_SHORT).show()
                                    activity.onBackPressed()
                                }else{
                                    Toast.makeText(activity,"删除成功 message: ${e.message}",Toast.LENGTH_SHORT).show()
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

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.new_fab -> {
                var dialog = AlertDialog.Builder(activity)
                dialog.setTitle("是否要删除 ${title}").setCancelable(false)
                        .setPositiveButton("删除",object:DialogInterface.OnClickListener{
                            override fun onClick(p0: DialogInterface?, p1: Int) {
                                var message = Message()
                                message.what = 2
                                handler.sendMessage(message)
                            }
                        })
                        .setNegativeButton("取消",object: DialogInterface.OnClickListener{
                            override fun onClick(p0: DialogInterface?, p1: Int) {
                                Toast.makeText(activity,"谢谢你放过我-。-",Toast.LENGTH_SHORT).show()
                            }
                        })
                        .show()
            }
            else -> {
            }
        }
    }
}