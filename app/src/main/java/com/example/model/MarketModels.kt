package com.example.model

enum class NavigationTab(val title: String, val iconName: String) {
    CHART("Charts", "ShowChart"),
    HEATMAP("Heatmap", "GridOn"),
    SCREENER("Screener", "FilterList"),
    ECONOMY("Economy", "Public"),
    SYMBOL_DETAILS("Details", "Analytics")
}

enum class EconomySubTab(val title: String) {
    CALENDAR("US Calendar"),
    MAP("Economic Map")
}

enum class SymbolSubTab(val title: String) {
    ALL("All in One"),
    INFO("Overview"),
    TECHNICAL("Technical"),
    FINANCIALS("Financials"),
    PROFILE("Profile")
}

data class MarketSymbol(
    val id: String,
    val name: String,
    val category: String
)

val POPULAR_SYMBOLS = listOf(
    MarketSymbol("NASDAQ:AAPL", "Apple", "Mega Cap"),
    MarketSymbol("NASDAQ:NVDA", "Nvidia", "Semiconductor"),
    MarketSymbol("NASDAQ:TSLA", "Tesla", "Auto / Tech"),
    MarketSymbol("BITSTAMP:BTCUSD", "Bitcoin", "Crypto"),
    MarketSymbol("NASDAQ:MSFT", "Microsoft", "Software"),
    MarketSymbol("NASDAQ:AMZN", "Amazon", "E-Commerce"),
    MarketSymbol("NASDAQ:GOOGL", "Google", "Mega Cap"),
    MarketSymbol("NASDAQ:META", "Meta", "Social Media"),
    MarketSymbol("AMEX:SPY", "S&P 500", "Index ETF"),
    MarketSymbol("NASDAQ:QQQ", "Nasdaq 100", "Index ETF"),
    MarketSymbol("NASDAQ:PLTR", "Palantir", "AI / Defense"),
    MarketSymbol("NASDAQ:MU", "Micron", "Semiconductor"),
    MarketSymbol("NASDAQ:NFLX", "Netflix", "Media")
)
