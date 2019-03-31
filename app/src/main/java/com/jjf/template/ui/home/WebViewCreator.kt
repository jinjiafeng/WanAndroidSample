package com.jjf.template.ui.home

import android.annotation.SuppressLint
import android.app.Activity
import android.view.ViewGroup
import android.webkit.WebSettings
import android.widget.LinearLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.jjf.template.util.CommonUtils
import com.just.agentweb.AgentWeb


/**
 * Created by jinjiafeng
 * Time :19-3-31
 */
class WebViewCreator : LifecycleObserver {
    lateinit var agentWeb: AgentWeb

    fun createWebView(activity: Activity, viewGroup: ViewGroup, articleLink: String) {
        this.agentWeb = AgentWeb.with(activity)
                .setAgentWebParent(viewGroup, LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .setMainFrameErrorView(com.jjf.template.R.layout.webview_error_view, -1)
                .createAgentWeb()
                .ready()
                .go(articleLink)
        initSetting()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initSetting() {
        val mWebView = agentWeb.webCreator.webView
        val mSettings = mWebView.settings
        mSettings.setAppCacheEnabled(true)
        mSettings.domStorageEnabled = true
        mSettings.databaseEnabled = true
        if (CommonUtils.isNetworkConnected()) {
            mSettings.cacheMode = WebSettings.LOAD_DEFAULT
        } else {
            mSettings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        }
        mSettings.javaScriptEnabled = true
        mSettings.setSupportZoom(true)
        mSettings.builtInZoomControls = true
        //不显示缩放按钮
        mSettings.displayZoomControls = false
        //设置自适应屏幕，两者合用
        //将图片调整到适合WebView的大小
        mSettings.useWideViewPort = true
        //缩放至屏幕的大小
        mSettings.loadWithOverviewMode = true
        //自适应屏幕
        mSettings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        agentWeb.webLifeCycle.onResume()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        agentWeb.webLifeCycle.onPause();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        agentWeb.webLifeCycle.onDestroy()
    }
}