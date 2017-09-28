package com.bao.iu

import android.content.*
import android.net.ConnectivityManager
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.provider.MediaStore
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.datatype.BmobFile
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import cn.bmob.v3.listener.UpdateListener
import cn.bmob.v3.listener.UploadFileListener
import cn.finalteam.galleryfinal.GalleryFinal
import cn.finalteam.galleryfinal.model.PhotoInfo
import com.bao.iu.adapter.MyPageAdapter
import com.bao.iu.fragment.f_Article.AddFragment
import com.bao.iu.fragment.f_Article.QueryFragment
import com.bao.iu.pojo.Article
import com.bao.iu.receiver.NetWorkChangeReceiver
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_header.view.*
import java.io.File
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    val networkChangeReceiver = NetWorkChangeReceiver()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 初始化控件和监听器
        initViewAndListener()
        //打开广播监听器
        openReceiver()
        //查询碎片
        replaceFragment(QueryFragment())
        //头像
        bmobThread(5)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu) //设置菜单布局
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        //注销广播监听器
        unregisterReceiver(networkChangeReceiver)
    }

    var imgUrl: String? = null
    val CHOOSE_PHOTO = 2
    var imgView: CircleImageView? = null
    var handler = object : Handler() {
        override fun handleMessage(msg: Message?) {
            when (msg?.what) {
                5 -> {
                    //下拉图片
                    //获取网络连接状态
                    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                    val nt = cm.activeNetworkInfo
                    var flagNetwork = (nt != null && nt.isAvailable)
                    //新建查询
                    var query = BmobQuery<Article>()
                    query.addWhereEqualTo("objectId", "xOjVGGGO")
                    if (flagNetwork) {
                        query.cachePolicy = BmobQuery.CachePolicy.NETWORK_ONLY
                        query.maxCacheAge = TimeUnit.DAYS.toMillis(7)  //设置缓存为7天
                    } else {
                        //没网时 只从缓存中获取
                        query.cachePolicy = BmobQuery.CachePolicy.CACHE_ONLY
                    }
                    query.findObjects(object : FindListener<Article>() {
                        override fun done(list: List<Article>?, e: BmobException?) {
                            if (e == null) {
                                val alist = list as ArrayList<Article>
                                val a = alist[0]
                                myusername.text = a.title
                                Glide.with(this@MainActivity).load(a.imgUrl).into(imgView)
                                Glide.with(this@MainActivity).load(a.imgUrl).into(myheader)
                            } else {
                                Toast.makeText(this@MainActivity, " 头像没有更新 - ${e.message} - ", Toast.LENGTH_LONG).show()
                            }
                        }
                    })

                }
                6 -> {
                    //上传图片
                    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                    val nt = cm.activeNetworkInfo
                    var flagNetwork = (nt != null && nt.isAvailable)
                    if (flagNetwork) {
                        val img = BmobFile(File(imgUrl))
                        img.uploadblock(object : UploadFileListener() {
                            override fun done(e: BmobException?) {
                                if (e == null) {
                                    var a = Article()
                                    a.imgUrl = img.url
                                    a.code = 3
                                    a.update("xOjVGGGO",object : UpdateListener() {
                                        override fun done(e1: BmobException?) {
                                            if (e1 == null) {
                                                bmobThread(5)
                                            } else {
                                                Toast.makeText(this@MainActivity, "上传失败 message:${e1.message}", Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                    })
                                } else {
                                    Toast.makeText(this@MainActivity, "上传文件失败 message:${e.message}", Toast.LENGTH_SHORT).show()
                                }
                            }
                        })
                    }else{
                        Toast.makeText(this@MainActivity, "没网上传不了头像", Toast.LENGTH_SHORT).show()
                    }
                }
                else -> {
                    Toast.makeText(this@MainActivity, R.string.loser, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    val mOnHanlderResultCallback = object : GalleryFinal.OnHanlderResultCallback {
        override fun onHanlderSuccess(reqeustCode: Int, resultList: MutableList<PhotoInfo>?) {
            when (reqeustCode) {
                CHOOSE_PHOTO -> {
                    imgUrl = resultList?.get(0)?.photoPath
                    bmobThread(6)
                }
                else -> {
                }
            }
        }

        override fun onHanlderFailure(requestCode: Int, errorMsg: String?) {

        }
    }

    fun openReceiver() {
        var inentFilter = IntentFilter()
        inentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
        registerReceiver(networkChangeReceiver, inentFilter)
    }

    fun initViewAndListener() {
        setContentView(R.layout.activity_main)
        //设置界面菜单
        setSupportActionBar(myToolbar)
        //设置头像点击事件
        myheader.setOnClickListener(this)
        //
        val toolbar = supportActionBar
        toolbar?.setTitle("")
        //获取NavigationView控件实例
        var Nav = myNav
        //设置默认选中
        Nav.setCheckedItem(R.id.nav_txt)
        //设置监听器
        Nav.setNavigationItemSelectedListener(this)
        //给悬浮按钮设置点击事件
        myFab.setOnClickListener(this)
        //给头像设置点击事件
        imgView = View.inflate(this@MainActivity,R.layout.nav_header,myNav).img_header
        imgView?.setOnClickListener(this)
        myVp.adapter  = MyPageAdapter(supportFragmentManager)
        myPsts.setViewPager(myVp)
    }

    fun bmobThread(send: Int) {
        Thread(object : Runnable {
            override fun run() {
                var msg = Message()
                msg.what = send
                handler.sendMessage(msg)
            }
        }).start()
    }

    fun replaceFragment(fragment: Fragment) {
        //获取FragmentManager
        val fm = supportFragmentManager
        //开启事务
        var transaction = fm.beginTransaction()
        //向容器中添加Fragment
        transaction.replace(R.id.myFl, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.camera -> {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivity(intent)
            }
            R.id.chat -> {
                val intent = Intent(Intent.ACTION_SENDTO)
                intent.data = Uri.parse("smsto:17671116291")
                intent.putExtra("sms_body", "可能是个傻子吧")
                startActivity(intent)
            }
            R.id.call -> {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:17671116291")
                startActivity(intent)
            }
            else -> {
                Toast.makeText(this, R.string.other, Toast.LENGTH_SHORT).show()
            }
        }
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
        //放入查询碎片
            R.id.nav_txt -> replaceFragment(QueryFragment())
            R.id.nav_map -> {

            }
            R.id.nav_setting -> {
            }
            else -> {
            }
        }
        myDra.closeDrawers() //关闭滑动菜单
        return true
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.myFab -> {
                //提示工具 - Snackbar
                Snackbar.make(view, R.string.askUp, Snackbar.LENGTH_SHORT).setAction(R.string.up, View.OnClickListener {
                    replaceFragment(AddFragment())
                }).show()
            }
            R.id.img_header -> {
                //选择头像
                GalleryFinal.openGallerySingle(CHOOSE_PHOTO, mOnHanlderResultCallback);
            }
            R.id.myheader ->{
                //打开滑动菜单
                myDra.openDrawer(GravityCompat.START)//start属性根据系统语言判断是从左到右（英文和汉语） 或者相反（阿拉伯语）
            }
            else -> {
            }
        }
    }


}