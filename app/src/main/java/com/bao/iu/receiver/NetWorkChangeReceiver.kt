package com.bao.iu.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.util.Log
import android.widget.Toast

class NetWorkChangeReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val nt = cm.activeNetworkInfo
        if (nt != null && nt.isAvailable) {
            Toast.makeText(context,"IU提醒:网络打开美滋滋",Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(context,"IU提醒:没网了-。-",Toast.LENGTH_LONG).show()
        }
    }
}
