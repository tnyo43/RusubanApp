package com.example.ktomoya.rusuapp.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.ktomoya.rusuapp.R
import com.example.ktomoya.rusuapp.values.Urls


class MonitorActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_monitor)
        val webView : WebView = findViewById(R.id.webview_monitor) as WebView

        webView.webViewClient = WebViewClient()
        webView.loadUrl(Urls.getStreamingAddress())
        webView.settings.javaScriptEnabled = true
    }

}