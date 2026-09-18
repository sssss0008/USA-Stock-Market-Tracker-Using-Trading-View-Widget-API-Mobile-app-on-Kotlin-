package com.example.util

object TradingViewHtmlProvider {

    private fun wrapHtml(content: String, isDark: Boolean, overflowYScroll: Boolean = false): String {
        val theme = if (isDark) "dark" else "light"
        val bgColor = if (isDark) "#0B1120" else "#FFFFFF"
        val textColor = if (isDark) "#F1F5F9" else "#0F172A"
        val overflowY = if (overflowYScroll) "auto" else "hidden"

        return """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="utf-8" />
                <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
                <style>
                    * {
                        box-sizing: border-box;
                        margin: 0;
                        padding: 0;
                    }
                    html, body {
                        width: 100%;
                        height: 100%;
                        background-color: $bgColor;
                        color: $textColor;
                        overflow-x: hidden;
                        overflow-y: $overflowY;
                        font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif;
                        -webkit-font-smoothing: antialiased;
                    }
                    .tradingview-widget-container {
                        width: 100% !important;
                        height: 100% !important;
                    }
                    .tradingview-widget-container__widget {
                        width: 100% !important;
                        height: 100% !important;
                    }
                    .tradingview-widget-copyright {
                        display: none !important;
                    }
                    .section-card {
                        background: ${if (isDark) "#111C33" else "#F8FAFC"};
                        border: 1px solid ${if (isDark) "#1E2A4A" else "#E2E8F0"};
                        border-radius: 12px;
                        padding: 12px;
                        margin-bottom: 16px;
                    }
                    .section-header {
                        font-size: 14px;
                        font-weight: 600;
                        text-transform: uppercase;
                        letter-spacing: 0.05em;
                        color: ${if (isDark) "#38BDF8" else "#2563EB"};
                        margin-bottom: 10px;
                    }
                </style>
            </head>
            <body class="$theme">
                $content
            </body>
            </html>
        """.trimIndent()
    }

    fun getTickerTapeHtml(isDark: Boolean): String {
        val theme = if (isDark) "dark" else "light"
        val content = """
            <script type="module" src="https://widgets.tradingview-widget.com/w/en/tv-ticker-tape.js"></script>
            <tv-ticker-tape
                symbols="NASDAQ:AAPL,NASDAQ:NVDA,NASDAQ:MU,NASDAQ:TSLA,NASDAQ:META,NASDAQ:SPCX,NASDAQ:MSFT,NASDAQ:AMZN,NASDAQ:GOOGL,NASDAQ:PLTR,NASDAQ:SNDK,NASDAQ:INTC,NASDAQ:NFLX"
                color-theme="$theme"
                show-hover
                symbol-url="https://wealthorbitcenter.com/free-live-trading-real-time-chart-stocks-forex-crypto/">
            </tv-ticker-tape>
        """.trimIndent()
        return wrapHtml(content, isDark)
    }

    fun getChartHtml(
        symbol: String = "BITSTAMP:BTCUSD",
        interval: String = "D",
        range: String = "YTD",
        isDark: Boolean = true
    ): String {
        val theme = if (isDark) "dark" else "light"
        val bgColor = if (isDark) "#0B1120" else "#FFFFFF"
        val gridColor = if (isDark) "rgba(255, 255, 255, 0.06)" else "rgba(46, 46, 46, 0.12)"

        val content = """
            <div class="tradingview-widget-container" style="height:100%;width:100%">
                <div class="tradingview-widget-container__widget" style="height:100%;width:100%"></div>
                <div class="tradingview-widget-copyright"><a href="https://www.tradingview.com/" rel="noopener nofollow" target="_blank"><span class="blue-text">$symbol</span></a><span class="trademark"> by TradingView</span></div>
                <script type="text/javascript" src="https://s3.tradingview.com/external-embedding/embed-widget-advanced-chart.js" async>
                {
                    "allow_symbol_change": true,
                    "calendar": false,
                    "details": true,
                    "hide_side_toolbar": false,
                    "hide_top_toolbar": false,
                    "hide_legend": false,
                    "hide_volume": false,
                    "hotlist": true,
                    "interval": "$interval",
                    "locale": "en",
                    "save_image": true,
                    "style": "1",
                    "symbol": "$symbol",
                    "theme": "$theme",
                    "timezone": "Etc/UTC",
                    "backgroundColor": "$bgColor",
                    "gridColor": "$gridColor",
                    "watchlist": [],
                    "withdateranges": true,
                    "range": "$range",
                    "compareSymbols": [],
                    "support_host": "https://www.tradingview.com",
                    "studies": [],
                    "autosize": true
                }
                </script>
            </div>
        """.trimIndent()
        return wrapHtml(content, isDark)
    }

    fun getHeatmapHtml(isDark: Boolean): String {
        val theme = if (isDark) "dark" else "light"
        val content = """
            <div class="tradingview-widget-container" style="height:100%;width:100%">
                <div class="tradingview-widget-container__widget" style="height:100%;width:100%"></div>
                <div class="tradingview-widget-copyright"><a href="https://www.tradingview.com/heatmap/stock/" rel="noopener nofollow" target="_blank"><span class="blue-text">Stock Heatmap</span></a><span class="trademark"> by TradingView</span></div>
                <script type="text/javascript" src="https://s3.tradingview.com/external-embedding/embed-widget-stock-heatmap.js" async>
                {
                    "dataSource": "AllUSA",
                    "blockSize": "market_cap_basic",
                    "blockColor": "change",
                    "grouping": "sector",
                    "locale": "en",
                    "symbolUrl": "https://wealthorbitcenter.com/free-live-trading-real-time-chart-stocks-forex-crypto/",
                    "colorTheme": "$theme",
                    "exchanges": [],
                    "hasTopBar": true,
                    "isDataSetEnabled": true,
                    "isZoomEnabled": true,
                    "hasSymbolTooltip": true,
                    "isMonoSize": false,
                    "width": "100%",
                    "height": "100%"
                }
                </script>
            </div>
        """.trimIndent()
        return wrapHtml(content, isDark)
    }

    fun getScreenerHtml(isDark: Boolean): String {
        val theme = if (isDark) "dark" else "light"
        val content = """
            <div class="tradingview-widget-container" style="height:100%;width:100%">
                <div class="tradingview-widget-container__widget" style="height:100%;width:100%"></div>
                <div class="tradingview-widget-copyright"><a href="https://www.tradingview.com/screener/" rel="noopener nofollow" target="_blank"><span class="blue-text">Stock Screener</span></a><span class="trademark"> by TradingView</span></div>
                <script type="text/javascript" src="https://s3.tradingview.com/external-embedding/embed-widget-screener.js" async>
                {
                    "market": "america",
                    "showToolbar": true,
                    "defaultColumn": "overview",
                    "defaultScreen": "most_capitalized",
                    "isTransparent": false,
                    "locale": "en",
                    "colorTheme": "$theme",
                    "largeChartUrl": "https://wealthorbitcenter.com/free-live-trading-real-time-chart-stocks-forex-crypto/",
                    "width": "100%",
                    "height": "100%"
                }
                </script>
            </div>
        """.trimIndent()
        return wrapHtml(content, isDark)
    }

    fun getEconomicCalendarHtml(isDark: Boolean): String {
        val theme = if (isDark) "dark" else "light"
        val content = """
            <div class="tradingview-widget-container" style="height:100%;width:100%">
                <div class="tradingview-widget-container__widget" style="height:100%;width:100%"></div>
                <div class="tradingview-widget-copyright"><a href="https://www.tradingview.com/economic-calendar/" rel="noopener nofollow" target="_blank"><span class="blue-text">Economic Calendar</span></a><span class="trademark"> by TradingView</span></div>
                <script type="text/javascript" src="https://s3.tradingview.com/external-embedding/embed-widget-events.js" async>
                {
                    "colorTheme": "$theme",
                    "isTransparent": false,
                    "locale": "en",
                    "countryFilter": "us",
                    "importanceFilter": "-1,0,1",
                    "width": "100%",
                    "height": "100%"
                }
                </script>
            </div>
        """.trimIndent()
        return wrapHtml(content, isDark)
    }

    fun getEconomicMapHtml(isDark: Boolean): String {
        val theme = if (isDark) "dark" else "light"
        val content = """
            <div style="width:100%;height:100%;display:flex;align-items:center;justify-content:center;padding:12px;">
                <script type="module" src="https://widgets.tradingview-widget.com/w/en/tv-economic-map.js"></script>
                <tv-economic-map
                    region="north-america"
                    hide-legend
                    color-theme="$theme"
                    symbol-url="https://wealthorbitcenter.com/free-live-trading-real-time-chart-stocks-forex-crypto/">
                </tv-economic-map>
            </div>
        """.trimIndent()
        return wrapHtml(content, isDark)
    }

    fun getSymbolInfoHtml(symbol: String, isDark: Boolean): String {
        val theme = if (isDark) "dark" else "light"
        val content = """
            <div class="tradingview-widget-container" style="width:100%;padding:12px;">
                <div class="tradingview-widget-container__widget"></div>
                <div class="tradingview-widget-copyright"><a href="https://www.tradingview.com/" rel="noopener nofollow" target="_blank"><span class="blue-text">$symbol</span></a></div>
                <script type="text/javascript" src="https://s3.tradingview.com/external-embedding/embed-widget-symbol-info.js" async>
                {
                    "symbol": "$symbol",
                    "colorTheme": "$theme",
                    "isTransparent": false,
                    "locale": "en",
                    "largeChartUrl": "https://wealthorbitcenter.com/free-live-trading-real-time-chart-stocks-forex-crypto/",
                    "width": "100%"
                }
                </script>
            </div>
        """.trimIndent()
        return wrapHtml(content, isDark, overflowYScroll = true)
    }

    fun getTechnicalAnalysisHtml(symbol: String, interval: String = "1M", isDark: Boolean): String {
        val theme = if (isDark) "dark" else "light"
        val content = """
            <div style="width:100%;min-height:450px;display:flex;justify-content:center;padding:16px;">
                <script type="module" src="https://widgets.tradingview-widget.com/w/en/tv-technical-analysis.js"></script>
                <tv-technical-analysis
                    symbol="$symbol"
                    interval="$interval"
                    ratings-mode="multiple"
                    color-theme="$theme"
                    symbol-url="https://wealthorbitcenter.com/free-live-trading-real-time-chart-stocks-forex-crypto/">
                </tv-technical-analysis>
            </div>
        """.trimIndent()
        return wrapHtml(content, isDark, overflowYScroll = true)
    }

    fun getFinancialsHtml(symbol: String, isDark: Boolean): String {
        val theme = if (isDark) "dark" else "light"
        val content = """
            <div class="tradingview-widget-container" style="width:100%;height:100%;padding:12px;">
                <div class="tradingview-widget-container__widget" style="width:100%;height:100%;"></div>
                <div class="tradingview-widget-copyright"><a href="https://www.tradingview.com/" rel="noopener nofollow" target="_blank"><span class="blue-text">$symbol</span></a></div>
                <script type="text/javascript" src="https://s3.tradingview.com/external-embedding/embed-widget-financials.js" async>
                {
                    "symbol": "$symbol",
                    "colorTheme": "$theme",
                    "displayMode": "compact",
                    "isTransparent": false,
                    "locale": "en",
                    "largeChartUrl": "https://wealthorbitcenter.com/free-live-trading-real-time-chart-stocks-forex-crypto/",
                    "width": "100%",
                    "height": 550
                }
                </script>
            </div>
        """.trimIndent()
        return wrapHtml(content, isDark, overflowYScroll = true)
    }

    fun getCompanyProfileHtml(symbol: String, isDark: Boolean): String {
        val theme = if (isDark) "dark" else "light"
        val content = """
            <div style="width:100%;padding:16px;">
                <script type="module" src="https://widgets.tradingview-widget.com/w/en/tv-company-profile.js"></script>
                <tv-company-profile
                    symbol="$symbol"
                    color-theme="$theme"
                    symbol-url="https://wealthorbitcenter.com/free-live-trading-real-time-chart-stocks-forex-crypto/">
                </tv-company-profile>
            </div>
        """.trimIndent()
        return wrapHtml(content, isDark, overflowYScroll = true)
    }

    fun getSymbolDetailsFullHtml(symbol: String, isDark: Boolean): String {
        val theme = if (isDark) "dark" else "light"
        val content = """
            <div style="padding: 12px; width: 100%;">
                <div class="section-card">
                    <div class="section-header">Performance & Overview</div>
                    <div class="tradingview-widget-container" style="width:100%;">
                        <div class="tradingview-widget-container__widget"></div>
                        <script type="text/javascript" src="https://s3.tradingview.com/external-embedding/embed-widget-symbol-info.js" async>
                        {
                            "symbol": "$symbol",
                            "colorTheme": "$theme",
                            "isTransparent": false,
                            "locale": "en",
                            "largeChartUrl": "https://wealthorbitcenter.com/free-live-trading-real-time-chart-stocks-forex-crypto/",
                            "width": "100%"
                        }
                        </script>
                    </div>
                </div>

                <div class="section-card">
                    <div class="section-header">Technical Analysis Ratings</div>
                    <div style="display:flex;justify-content:center;width:100%;">
                        <script type="module" src="https://widgets.tradingview-widget.com/w/en/tv-technical-analysis.js"></script>
                        <tv-technical-analysis
                            symbol="$symbol"
                            interval="1M"
                            ratings-mode="multiple"
                            color-theme="$theme"
                            symbol-url="https://wealthorbitcenter.com/free-live-trading-real-time-chart-stocks-forex-crypto/">
                        </tv-technical-analysis>
                    </div>
                </div>

                <div class="section-card">
                    <div class="section-header">Financials & Fundamentals</div>
                    <div class="tradingview-widget-container" style="width:100%;height:550px;">
                        <div class="tradingview-widget-container__widget" style="width:100%;height:100%;"></div>
                        <script type="text/javascript" src="https://s3.tradingview.com/external-embedding/embed-widget-financials.js" async>
                        {
                            "symbol": "$symbol",
                            "colorTheme": "$theme",
                            "displayMode": "compact",
                            "isTransparent": false,
                            "locale": "en",
                            "largeChartUrl": "https://wealthorbitcenter.com/free-live-trading-real-time-chart-stocks-forex-crypto/",
                            "width": "100%",
                            "height": 550
                        }
                        </script>
                    </div>
                </div>

                <div class="section-card">
                    <div class="section-header">Company Profile</div>
                    <script type="module" src="https://widgets.tradingview-widget.com/w/en/tv-company-profile.js"></script>
                    <tv-company-profile
                        symbol="$symbol"
                        color-theme="$theme"
                        symbol-url="https://wealthorbitcenter.com/free-live-trading-real-time-chart-stocks-forex-crypto/">
                    </tv-company-profile>
                </div>
            </div>
        """.trimIndent()
        return wrapHtml(content, isDark, overflowYScroll = true)
    }
}
