package com.bao.iu

import android.content.Context
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.recycler_acticle.view.*

/**
 * Created by Administrator on 2017/9/19.
 */
class ArticleAdapter(aList: List<Article>): RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {

   private var mContext: Context? = null
   private val aList: List<Article>? = aList

    override fun getItemCount(): Int {
        return aList!!.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        if (mContext == null){
            mContext = parent?.context
        }
        val view = LayoutInflater.from(mContext).inflate(R.layout.recycler_acticle,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val a = aList?.get(position)
        //加载图片
        Glide.with(mContext).load(a?.imgUrl).placeholder(R.drawable.img_3396).into(holder?.img)
        holder?.title!!.setText(a?.title)
        holder.ctime.setText(a?.createdAt.toString())
        holder.utime.setText(a?.updatedAt.toString())
        holder.contents.setText(a?.contents)
        holder.cardView.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
                var arg = Bundle()
                arg.putString("imgUrl",a?.imgUrl)
                arg.putString("title",a?.title)
                arg.putString("contents",a?.contents)
                arg.putString("objectId",a?.objectId)
                var fragment = ArticleFragment()
                fragment.arguments =arg
                (mContext as MainActivity).replaceFragment(fragment)
            }
        })
    }


    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        var cardView = view as CardView
        var img = view.a_img
        var title = view.a_title
        var ctime = view.a_ctime
        var utime = view.a_utime
        var contents = view.a_contents
    }
}