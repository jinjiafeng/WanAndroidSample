package com.jjf.template.util

import android.net.ConnectivityManager
import androidx.core.content.getSystemService
import com.jjf.template.App


/**
 * Created by jinjiafeng
 * Time :19-3-31
 */
object CommonUtils {

    /**
     * 检查是否有可用网络
     */
    fun isNetworkConnected(): Boolean {
        val connectivityManager: ConnectivityManager? = App.instance.getSystemService()
        return connectivityManager?.activeNetworkInfo != null
    }
}