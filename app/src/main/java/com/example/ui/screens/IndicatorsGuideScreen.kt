package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ShowChart
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.TipsAndUpdates
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.GlassIconButton
import com.example.ui.components.GlassStatusPill
import com.example.ui.components.GlowingIconBadge
import com.example.ui.theme.BearRed
import com.example.ui.theme.BullGreen
import com.example.ui.theme.FinancialBlue

data class IndicatorGuide(
    val name: String,
    val acronym: String,
    val type: String,
    val summary: String,
    val bullishSignal: String,
    val bearishSignal: String,
    val proTip: String
)

val TECHNICAL_INDICATORS = listOf(
    IndicatorGuide(
        name = "Relative Strength Index",
        acronym = "RSI (14)",
        type = "Momentum Oscillator (0-100)",
        summary = "Evaluates whether an asset is overbought or oversold by comparing the magnitude of recent gains to recent losses over a standard 14-period lookback.",
        bullishSignal = "RSI dips below 30 (Oversold) and crosses back above 30, or forms Bullish Divergence (price makes lower low while RSI makes higher low).",
        bearishSignal = "RSI rises above 70 (Overbought) and hooks down below 70, or forms Bearish Divergence (price makes higher high while RSI makes lower high).",
        proTip = "In strong upward bull trends, RSI frequently stays above 70 for extended runs; don't short solely based on an overbought reading."
    ),
    IndicatorGuide(
        name = "Moving Average Convergence Divergence",
        acronym = "MACD (12, 26, 9)",
        type = "Trend-Following Momentum",
        summary = "Reveals changes in the strength, direction, momentum, and duration of a trend in a stock price.",
        bullishSignal = "The MACD line crosses above the 9-day Signal line from below, or the histogram expands upwards above zero.",
        bearishSignal = "The MACD line crosses below the Signal line, or momentum bars flip from green to red below the zero line.",
        proTip = "Crossovers occurring far below the zero line represent much higher-probability mean-reversion buying opportunities."
    ),
    IndicatorGuide(
        name = "Moving Averages (SMA 50 & SMA 200)",
        acronym = "SMA 50 / 200",
        type = "Trend Benchmark",
        summary = "The 50-day and 200-day simple moving averages are the most watched institutional lines in the world.",
        bullishSignal = "Golden Cross: 50-day SMA crosses decisively above the 200-day SMA. Price uses the 200-day SMA as dynamic support.",
        bearishSignal = "Death Cross: 50-day SMA crosses below the 200-day SMA. Price fails and rejects repeatedly at the declining 200 SMA.",
        proTip = "Institutions accumulate on first pullbacks to the rising 50-day SMA during healthy bull trends."
    ),
    IndicatorGuide(
        name = "Bollinger Bands",
        acronym = "BB (20, 2)",
        type = "Volatility & Range",
        summary = "Constructed with a 20-period moving average and upper/lower bands situated 2 standard deviations away.",
        bullishSignal = "Bollinger Squeeze (bands narrow tightly together) followed by an explosive candle closing above the upper band on high volume.",
        bearishSignal = "Price rejection at the upper band with a reversal candlestick, targeting the baseline 20 SMA as mean reversion.",
        proTip = "90% of price action takes place within the bands. A squeeze signifies that a violent volatility explosion is imminent."
    ),
    IndicatorGuide(
        name = "Volume & VWAP",
        acronym = "Volume / VWAP",
        type = "Institutional Liquidity",
        summary = "Volume represents the true fuel of market movement. Volume-Weighted Average Price (VWAP) is the intraday institutional benchmark.",
        bullishSignal = "Breakouts confirmed by 1.5x to 2x average daily volume; price holding above daily VWAP indicates buyer dominance.",
        bearishSignal = "Price rallies on low volume (lack of conviction) followed by heavy volume selloffs; breakdown below VWAP.",
        proTip = "Never trust a breakout on below-average volume; false breakouts occur predominantly when volume fails to confirm."
    )
)

@Composable
fun IndicatorsGuideScreen(
    onNavigateBack: () -> Unit,
    isDark: Boolean,
    modifier: Modifier = Modifier
) {
    var expandedIndex by remember { mutableStateOf<Int?>(0) }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .testTag("indicators_guide_screen"),
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.96f))
                    .statusBarsPadding()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        GlassIconButton(
                            icon = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            onClick = onNavigateBack,
                            tint = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.testTag("indicators_back_button")
                        )
                        GlowingIconBadge(
                            icon = Icons.AutoMirrored.Filled.ShowChart,
                            contentDescription = null,
                            size = 38.dp,
                            iconSize = 20.dp,
                            tint = FinancialBlue,
                            containerColor = FinancialBlue
                        )
                        Column {
                            Text(
                                text = "Technical Indicators Guide",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "Cheat Sheet & Trading Rules",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                    GlassStatusPill(
                        text = "PRO RULES",
                        accentColor = BullGreen
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TECHNICAL_INDICATORS.forEachIndexed { index, indicator ->
                val isExpanded = expandedIndex == index
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .clickable { expandedIndex = if (isExpanded) null else index }
                        .animateContentSize(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f)
                    )
                ) {
                    Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(
                                    text = indicator.name,
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = indicator.type,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                            Icon(
                                imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Text(
                            text = indicator.summary,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        AnimatedVisibility(visible = isExpanded) {
                            Column(
                                modifier = Modifier.padding(top = 8.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                // Bullish Signal Box
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(BullGreen.copy(alpha = 0.12f))
                                        .padding(10.dp)
                                ) {
                                    Column {
                                        Text(
                                            text = "BULLISH SETUP",
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = BullGreen
                                        )
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Text(
                                            text = indicator.bullishSignal,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                    }
                                }

                                // Bearish Signal Box
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(BearRed.copy(alpha = 0.12f))
                                        .padding(10.dp)
                                ) {
                                    Column {
                                        Text(
                                            text = "BEARISH SETUP",
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = BearRed
                                        )
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Text(
                                            text = indicator.bearishSignal,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                    }
                                }

                                // Pro Tip Box
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(MaterialTheme.colorScheme.surface)
                                        .padding(10.dp)
                                ) {
                                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                        Icon(
                                            imageVector = Icons.Default.TipsAndUpdates,
                                            contentDescription = null,
                                            tint = FinancialBlue,
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Column {
                                            Text(
                                                text = "PRO TRADER TIP",
                                                style = MaterialTheme.typography.labelSmall,
                                                fontWeight = FontWeight.Bold,
                                                color = FinancialBlue
                                            )
                                            Spacer(modifier = Modifier.height(2.dp))
                                            Text(
                                                text = indicator.proTip,
                                                style = MaterialTheme.typography.bodySmall,
                                                color = MaterialTheme.colorScheme.onSurface
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
