package com.example.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.CandlestickChart
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.EventNote
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.GridOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.BullGreen
import com.example.ui.theme.FinancialBlue

data class OnboardingPageData(
    val stepIndex: Int,
    val totalSteps: Int = 3,
    val badge: String,
    val title: String,
    val subtitle: String,
    val highlights: List<String>,
    val accentColor: Color
)

val ONBOARDING_PAGES = listOf(
    OnboardingPageData(
        stepIndex = 0,
        badge = "Step 1 of 3 • Market Scanning",
        title = "Live USA Stock Screener",
        subtitle = "Scan and filter thousands of equities across NASDAQ, NYSE, and AMEX. Uncover top gainers, high-volume breakouts, valuation metrics, and technical signals in real time.",
        highlights = listOf(
            "Screen by Market Cap, P/E Ratio, Volume, and Sector",
            "Instantly view Top Gainers, Losers, and Most Active",
            "Multi-criteria filters built directly for US equity traders"
        ),
        accentColor = FinancialBlue
    ),
    OnboardingPageData(
        stepIndex = 1,
        badge = "Step 2 of 3 • Visual Analysis",
        title = "Interactive Charts & Heatmaps",
        subtitle = "Professional TradingView charts with real-time candlestick bars, multiple timeframes, and indicators. Visualize market-wide performance using interactive sector heatmaps.",
        highlights = listOf(
            "Advanced candlestick charts with technical indicators",
            "S&P 500 & Sector Heatmap to spot market rotation",
            "Streaming live ticker tape for real-time price updates"
        ),
        accentColor = BullGreen
    ),
    OnboardingPageData(
        stepIndex = 2,
        badge = "Step 3 of 3 • Macro & Community",
        title = "US Economy & Deep Fundamentals",
        subtitle = "Stay ahead of Federal Reserve interest rates, CPI inflation, and jobs reports. Inspect balance sheets, revenue, and analyst targets. 100% free with zero data tracking.",
        highlights = listOf(
            "US Economic Calendar with Fed dates, CPI, and GDP releases",
            "Detailed fundamental statements, ratios, and company profiles",
            "100% Free Open Project — We do NOT track or sell any user data"
        ),
        accentColor = Color(0xFF9C27B0)
    )
)

@Composable
fun OnboardingScreen(
    onFinish: () -> Unit,
    modifier: Modifier = Modifier
) {
    var currentPageIndex by remember { mutableIntStateOf(0) }
    val currentPage = ONBOARDING_PAGES[currentPageIndex]
    val isLastPage = currentPageIndex == ONBOARDING_PAGES.size - 1

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF081220),
                        Color(0xFF0B1B33),
                        Color(0xFF070E1A)
                    )
                )
            )
            .testTag("onboarding_screen")
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            // Top Bar: Back button, App Title, Skip Button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (currentPageIndex > 0) {
                    IconButton(
                        onClick = { currentPageIndex-- },
                        modifier = Modifier.testTag("onboarding_back_btn")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                } else {
                    Spacer(modifier = Modifier.width(48.dp))
                }

                Text(
                    text = "USA Stock Screener",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    ),
                    color = Color.White
                )

                TextButton(
                    onClick = onFinish,
                    modifier = Modifier.testTag("onboarding_skip_btn")
                ) {
                    Text(
                        text = "Skip",
                        color = Color(0xFF90CAF9),
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Main Scrollable Content with Page Transition
            AnimatedContent(
                targetState = currentPageIndex,
                transitionSpec = {
                    if (targetState > initialState) {
                        slideInHorizontally { width -> width } + fadeIn() togetherWith
                                slideOutHorizontally { width -> -width } + fadeOut()
                    } else {
                        slideInHorizontally { width -> -width } + fadeIn() togetherWith
                                slideOutHorizontally { width -> width } + fadeOut()
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                label = "onboarding_content"
            ) { pageIdx ->
                val page = ONBOARDING_PAGES[pageIdx]
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(8.dp))

                    // Step badge
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = page.accentColor.copy(alpha = 0.18f),
                        border = androidx.compose.foundation.BorderStroke(1.dp, page.accentColor.copy(alpha = 0.35f))
                    ) {
                        Text(
                            text = page.badge,
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = page.accentColor
                            ),
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Custom Visual Illustration for each step
                    when (pageIdx) {
                        0 -> OnboardingScreenerIllustration()
                        1 -> OnboardingChartsIllustration()
                        2 -> OnboardingMacroIllustration()
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Title
                    Text(
                        text = page.title,
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 24.sp
                        ),
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Subtitle / Details
                    Text(
                        text = page.subtitle,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 14.sp,
                            lineHeight = 21.sp
                        ),
                        color = Color(0xFFB0BEC5),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Highlights List Card
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF102138)),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF1A3353)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            page.highlights.forEach { highlight ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = null,
                                        tint = page.accentColor,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Text(
                                        text = highlight,
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            fontWeight = FontWeight.Medium,
                                            fontSize = 13.sp
                                        ),
                                        color = Color.White
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            // Bottom Navigation Area: Dots & Action Button
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Page Indicator Dots
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    ONBOARDING_PAGES.indices.forEach { index ->
                        val isSelected = index == currentPageIndex
                        Box(
                            modifier = Modifier
                                .height(8.dp)
                                .width(if (isSelected) 24.dp else 8.dp)
                                .clip(CircleShape)
                                .background(
                                    if (isSelected) currentPage.accentColor else Color(0xFF263D5C)
                                )
                                .clickable { currentPageIndex = index }
                        )
                    }
                }

                // Next / Get Started Action Button
                Button(
                    onClick = {
                        if (isLastPage) {
                            onFinish()
                        } else {
                            currentPageIndex++
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isLastPage) BullGreen else FinancialBlue
                    ),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("onboarding_primary_action_btn")
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = if (isLastPage) "Get Started • Enter App" else "Next Step",
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            ),
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Privacy assurance note
                Text(
                    text = "Free Open Project • Zero Data Tracking • 100% Private",
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                    color = Color(0xFF78909C)
                )
            }
        }
    }
}

// -------------------------------------------------------------
// Visual Illustrations for each of the 3 onboarding screens
// -------------------------------------------------------------

@Composable
fun OnboardingScreenerIllustration() {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF0D1D33)),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF1E3A5F)),
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .shadow(12.dp, RoundedCornerShape(20.dp))
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Background Canvas Grid
            Canvas(modifier = Modifier.fillMaxSize()) {
                val stepX = size.width / 6
                for (i in 1..5) {
                    drawLine(
                        color = Color(0x102196F3),
                        start = Offset(stepX * i, 0f),
                        end = Offset(stepX * i, size.height),
                        strokeWidth = 1f
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(14.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Top filter pills simulation
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IllustrationPill("NASDAQ", Color(0xFF1E88E5), isFilled = true)
                    IllustrationPill("NYSE", Color(0xFF42A5F5), isFilled = false)
                    IllustrationPill("Tech & AI", Color(0xFF00E676), isFilled = true)
                    IllustrationPill("P/E < 25", Color(0xFFFFB300), isFilled = false)
                }

                // Sample stock screener items
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    StockScreenerRow("NVDA", "Nvidia Corp", "$128.40", "+4.28%", isBullish = true)
                    StockScreenerRow("AAPL", "Apple Inc", "$224.23", "+1.65%", isBullish = true)
                    StockScreenerRow("TSLA", "Tesla Motors", "$248.50", "-0.84%", isBullish = false)
                }

                // Bottom badge
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Real-time USA Market Screener",
                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                        color = Color(0xFF90CAF9)
                    )
                    Text(
                        text = "12,000+ Tickers",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        color = BullGreen
                    )
                }
            }
        }
    }
}

@Composable
fun OnboardingChartsIllustration() {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF0A1C30)),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF1A4064)),
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .shadow(12.dp, RoundedCornerShape(20.dp))
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Candlestick & Trend Canvas
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 20.dp)
            ) {
                val w = size.width
                val h = size.height

                // Draw Candles
                val candles = listOf(
                    Triple(0.08f, 0.45f, 0.65f),
                    Triple(0.18f, 0.55f, 0.40f),
                    Triple(0.28f, 0.42f, 0.30f),
                    Triple(0.38f, 0.35f, 0.50f),
                    Triple(0.48f, 0.48f, 0.35f),
                    Triple(0.58f, 0.38f, 0.22f),
                    Triple(0.68f, 0.26f, 0.34f),
                    Triple(0.78f, 0.30f, 0.18f),
                    Triple(0.88f, 0.20f, 0.12f)
                )

                candles.forEach { (relX, topRel, botRel) ->
                    val x = w * relX
                    val isGreen = topRel > botRel
                    val col = if (isGreen) Color(0xFF00E676) else Color(0xFFFF5252)

                    val candleTop = h * minOf(topRel, botRel)
                    val candleBot = h * maxOf(topRel, botRel)
                    val wickTop = maxOf(0f, candleTop - 12f)
                    val wickBot = minOf(h, candleBot + 12f)

                    // Wick
                    drawLine(
                        color = col,
                        start = Offset(x, wickTop),
                        end = Offset(x, wickBot),
                        strokeWidth = 2f
                    )
                    // Body
                    drawRoundRect(
                        color = col,
                        topLeft = Offset(x - 8f, candleTop),
                        size = Size(16f, maxOf(10f, candleBot - candleTop)),
                        cornerRadius = CornerRadius(2f, 2f)
                    )
                }

                // Moving Average Trend line
                val path = androidx.compose.ui.graphics.Path().apply {
                    moveTo(w * 0.05f, h * 0.60f)
                    cubicTo(
                        w * 0.30f, h * 0.55f,
                        w * 0.50f, h * 0.35f,
                        w * 0.90f, h * 0.15f
                    )
                }
                drawPath(
                    path = path,
                    color = Color(0xFF00E5FF),
                    style = androidx.compose.ui.graphics.drawscope.Stroke(width = 4f)
                )
            }

            // Overlay info chips
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xCC0D2036)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "S&P 500 • SPY",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = Color.White
                        )
                        Text(
                            text = "+1.82%",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = BullGreen
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xCC00E676)
                ) {
                    Text(
                        text = "TradingView Pro",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        ),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun OnboardingMacroIllustration() {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF121528)),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF28284C)),
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .shadow(12.dp, RoundedCornerShape(20.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Public,
                        contentDescription = null,
                        tint = Color(0xFFB388FF),
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "US Economic Radar",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = Color.White
                    )
                }
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0x30B388FF)
                ) {
                    Text(
                        text = "Live Forecasts",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = Color(0xFFB388FF),
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
            }

            // Grid of economic indicators
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                EconomicCardMetric("FED RATE", "5.25%", "Next FOMC", Color(0xFF82B1FF), Modifier.weight(1f))
                EconomicCardMetric("CPI INFLATION", "2.9%", "Target 2.0%", Color(0xFF00E676), Modifier.weight(1f))
                EconomicCardMetric("US GDP", "+3.0%", "QoQ Est.", Color(0xFFFFD54F), Modifier.weight(1f))
            }

            // Security & Privacy guarantee footer inside card
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = Color(0x2000E676),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Security,
                        contentDescription = null,
                        tint = BullGreen,
                        modifier = Modifier.size(13.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "100% Free & Open • No Accounts • No User Tracking",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold
                        ),
                        color = BullGreen
                    )
                }
            }
        }
    }
}

@Composable
private fun IllustrationPill(label: String, color: Color, isFilled: Boolean) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = if (isFilled) color.copy(alpha = 0.25f) else Color(0x15FFFFFF),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (isFilled) color.copy(alpha = 0.6f) else Color(0x30FFFFFF)
        )
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = if (isFilled) color else Color(0xFFB0BEC5)
            ),
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
        )
    }
}

@Composable
private fun StockScreenerRow(
    ticker: String,
    name: String,
    price: String,
    change: String,
    isBullish: Boolean
) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = Color(0x20FFFFFF),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = ticker,
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    color = Color.White
                )
                Text(
                    text = name,
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                    color = Color(0xFF90A4AE)
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = price,
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
                    color = Color.White
                )
                Text(
                    text = change,
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    color = if (isBullish) BullGreen else Color(0xFFFF5252)
                )
            }
        }
    }
}

@Composable
private fun EconomicCardMetric(
    label: String,
    value: String,
    sub: String,
    accent: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = Color(0x25FFFFFF),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp, fontWeight = FontWeight.Bold),
                color = Color(0xFFB0BEC5)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.ExtraBold),
                color = accent
            )
            Text(
                text = sub,
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 8.sp),
                color = Color(0xFF78909C)
            )
        }
    }
}
