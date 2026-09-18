package com.example.ui.components

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun TradingViewWebView(
    htmlData: String,
    modifier: Modifier = Modifier,
    testTag: String = "tradingview_webview",
    reloadTrigger: Int = 0
) {
    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(true) }
    var progress by remember { mutableFloatStateOf(0f) }
    var hasError by remember { mutableStateOf(false) }
    var webViewRef by remember { mutableStateOf<WebView?>(null) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .testTag(testTag)
    ) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { ctx ->
                WebView(ctx).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    setBackgroundColor(0)
                    // Do not force LAYER_TYPE_HARDWARE as WebView handles its own rendering and avoids Mesa rendernode issues
                    setLayerType(android.view.View.LAYER_TYPE_NONE, null)

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
                        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                            super.onPageStarted(view, url, favicon)
                            isLoading = true
                            hasError = false
                        }

                        override fun onPageFinished(view: WebView?, url: String?) {
                            super.onPageFinished(view, url)
                            isLoading = false
                        }

                        override fun onReceivedError(
                            view: WebView?,
                            request: WebResourceRequest?,
                            error: WebResourceError?
                        ) {
                            super.onReceivedError(view, request, error)
                            // Only show error for main frame requests
                            if (request?.isForMainFrame == true) {
                                hasError = true
                                isLoading = false
                            }
                        }

                        override fun onRenderProcessGone(
                            view: WebView?,
                            detail: android.webkit.RenderProcessGoneDetail?
                        ): Boolean {
                            hasError = true
                            isLoading = false
                            return true
                        }

                        override fun shouldOverrideUrlLoading(
                            view: WebView?,
                            request: WebResourceRequest?
                        ): Boolean {
                            val url = request?.url?.toString() ?: return false
                            // If external intent link is tapped, launch browser safely
                            if (url.startsWith("http://") || url.startsWith("https://")) {
                                if (!url.contains("tradingview.com") && !url.contains("wealthorbitcenter.com")) {
                                    return try {
                                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                        context.startActivity(intent)
                                        true
                                    } catch (e: Exception) {
                                        false
                                    }
                                }
                            }
                            return false
                        }
                    }

                    webChromeClient = object : WebChromeClient() {
                        override fun onProgressChanged(view: WebView?, newProgress: Int) {
                            super.onProgressChanged(view, newProgress)
                            progress = newProgress / 100f
                            if (newProgress >= 100) {
                                isLoading = false
                            }
                        }
                    }

                    loadDataWithBaseURL(
                        "https://www.tradingview.com",
                        htmlData,
                        "text/html",
                        "UTF-8",
                        null
                    )
                    webViewRef = this
                }
            },
            update = { webView ->
                webViewRef = webView
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

        // Loading bar
        AnimatedVisibility(
            visible = isLoading && !hasError,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.align(Alignment.TopCenter)
        ) {
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(3.dp),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )
        }

        // Error retry overlay
        if (hasError) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.WarningAmber,
                    contentDescription = "Connection Error",
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Unable to load market data",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Please check your network connection and try again.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        hasError = false
                        isLoading = true
                        webViewRef?.loadDataWithBaseURL(
                            "https://www.tradingview.com",
                            htmlData,
                            "text/html",
                            "UTF-8",
                            null
                        )
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Retry",
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text("Retry")
                }
            }
        }
    }

    DisposableEffect(reloadTrigger) {
        if (reloadTrigger > 0) {
            hasError = false
            isLoading = true
            webViewRef?.loadDataWithBaseURL(
                "https://www.tradingview.com",
                htmlData,
                "text/html",
                "UTF-8",
                null
            )
        }
        onDispose { }
    }
}
