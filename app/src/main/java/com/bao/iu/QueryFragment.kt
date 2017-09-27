package com.bao.iu

import android.content.Context
import android.net.ConnectivityManager
import android.os.*
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_query.*
import java.util.concurrent.TimeUnit

/**
 * Created by Administrator on 2017/9/21.
 */
class QueryFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       //这里定义布局 但是这里定义 RecyclerView 的 layoutManager会报空指针异常
        val view = inflater?.inflate(R.layout.fragment_query,container,false)
        return view
    }
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        //onCreateView后执行
        super.onViewCreated(view, savedInstanceState)
        //设置布局
        myRecycler.layoutManager = GridLayoutManager(activity,1)
        //设置下拉刷新的颜色样式
        mySrl.setColorSchemeColors(R.color.colorPrimary)
        //设置刷新监听器
        mySrl.setOnRefreshListener(this)
        queryThread(4)
        //新建悬浮按钮可见
        activity.myFab.visibility = View.VISIBLE
    }
    val handler = object: Handler(){
       override fun handleMessage(msg: Message?) {
           when (msg?.what) {
               4 -> {
                   //获取网络连接状态
                   val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                   val nt = cm.activeNetworkInfo
                   var flagNetwork = (nt != null && nt.isAvailable)
                   //Bmob查询
                   var query = BmobQuery<Article>()
                   query.addWhereEqualTo("status",1)
                   query.order("-createdAt")
                   query.setLimit(100)
                   //有网络时可以联网缓存
                   if(flagNetwork){
                       query.cachePolicy = BmobQuery.CachePolicy.NETWORK_ONLY
                       query.maxCacheAge = TimeUnit.DAYS.toMillis(7)  //设置缓存为7天
                   }else{
                       //没网时 只从缓存中获取
                       query.cachePolicy = BmobQuery.CachePolicy.CACHE_ONLY
                   }
                   query.findObjects(object: FindListener<Article>() {
                       override fun done(list: List<Article>?, e: BmobException?) {
                           if(e==null){
                               val alist = list as ArrayList<Article>
                               myRecycler.adapter = ArticleAdapter(alist)
                           }else{
                               Toast.makeText(context," 没有更新消息 - ${e.message} - ", Toast.LENGTH_SHORT).show()
                           }
                       }
                   })
               }
               else -> {
               }
           }
       }
    }

    fun queryThread(num:Int){
        Thread(object: Runnable{
            override fun run() {
                var m = Message()
                m.what = num
                handler.sendMessage(m)
            }
        }).start()
    }
    override fun onRefresh() {
       queryThread(4)
        mySrl.isRefreshing = false
    }

}