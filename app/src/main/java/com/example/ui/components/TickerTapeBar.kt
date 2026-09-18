package com.example.ui.components

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.util.TradingViewHtmlProvider

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun TickerTapeBar(
    isDark: Boolean,
    modifier: Modifier = Modifier,
    reloadTrigger: Int = 0
) {
    val htmlData = remember(isDark, reloadTrigger) {
        TradingViewHtmlProvider.getTickerTapeHtml(isDark)
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(MaterialTheme.colorScheme.surface)
            .testTag("ticker_tape_bar")
    ) {
        AndroidView(
            modifier = Modifier.fillMaxWidth().height(50.dp),
            factory = { ctx ->
                WebView(ctx).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    setBackgroundColor(0)
                    setLayerType(android.view.View.LAYER_TYPE_NONE, null)
                    isVerticalScrollBarEnabled = false
                    isHorizontalScrollBarEnabled = false

                    settings.apply {
                        javaScriptEnabled = true
                        domStorageEnabled = true
                        databaseEnabled = true
                        cacheMode = WebSettings.LOAD_DEFAULT
                        allowFileAccess = false
                        allowContentAccess = false
                        useWideViewPort = true
                        loadWithOverviewMode = true
                        mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                        userAgentString =
                            "Mozilla/5.0 (Linux; Android 14; Mobile) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Mobile Safari/537.36"
                    }

                    webViewClient = object : WebViewClient() {
                        override fun onRenderProcessGone(
                            view: WebView?,
                            detail: android.webkit.RenderProcessGoneDetail?
                        ): Boolean {
                            return true
                        }
                    }

                    loadDataWithBaseURL(
                        "https://www.tradingview.com",
                        htmlData,
                        "text/html",
                        "UTF-8",
                        null
                    )
                }
            },
            update = { webView ->
                webView.loadDataWithBaseURL(
                    "https://www.tradingview.com",
                    htmlData,
                    "text/html",
                    "UTF-8",
                    null
                )
            },
            onRelease = { webView ->
                webView.stopLoading()
                webView.destroy()
            }
        )
    }
}
