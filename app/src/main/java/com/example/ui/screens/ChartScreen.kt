package com.example.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material.icons.filled.FullscreenExit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.POPULAR_SYMBOLS
import com.example.ui.components.GlassIconButton
import com.example.ui.components.GlowingIconBadge
import com.example.ui.components.TradingViewWebView
import com.example.ui.components.glassmorphic
import com.example.util.TradingViewHtmlProvider

@Composable
fun ChartScreen(
    isDark: Boolean,
    modifier: Modifier = Modifier,
    reloadTrigger: Int = 0
) {
    var selectedSymbol by remember { mutableStateOf("BITSTAMP:BTCUSD") }
    var selectedInterval by remember { mutableStateOf("D") }
    var selectedRange by remember { mutableStateOf("YTD") }
    var isFullscreen by remember { mutableStateOf(false) }
    var showSearchDialog by remember { mutableStateOf(false) }
    var customSymbolInput by remember { mutableStateOf("") }

    val intervals = listOf(
        "15" to "15m",
        "60" to "1H",
        "D" to "1D",
        "W" to "1W",
        "M" to "1M"
    )

    val ranges = listOf("1D", "1M", "3M", "YTD", "12M", "ALL")

    val htmlData = remember(selectedSymbol, selectedInterval, selectedRange, isDark, reloadTrigger) {
        TradingViewHtmlProvider.getChartHtml(
            symbol = selectedSymbol,
            interval = selectedInterval,
            range = selectedRange,
            isDark = isDark
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("chart_screen")
    ) {
        if (!isFullscreen) {
            // Quick symbol selector and timeframe bar with glassmorphic styling
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 6.dp)
                    .glassmorphic(
                        backgroundColor = MaterialTheme.colorScheme.surface,
                        borderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.25f),
                        shape = RoundedCornerShape(20.dp),
                        borderWidth = 1.dp
                    )
            ) {
                Column(modifier = Modifier.padding(vertical = 8.dp, horizontal = 6.dp)) {
                    // Symbol horizontal carousel
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState())
                            .padding(horizontal = 6.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Glass search button with glowing icon badge
                        GlowingIconBadge(
                            icon = Icons.Default.Search,
                            contentDescription = "Search Symbol",
                            size = 36.dp,
                            iconSize = 18.dp,
                            tint = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .clickable { showSearchDialog = true }
                                .testTag("search_symbol_button")
                        )

                        POPULAR_SYMBOLS.take(8).forEach { sym ->
                            val isSelected = selectedSymbol == sym.id

                            val scale by animateFloatAsState(
                                targetValue = if (isSelected) 1.05f else 1.0f,
                                animationSpec = spring(
                                    dampingRatio = Spring.DampingRatioMediumBouncy,
                                    stiffness = Spring.StiffnessLow
                                ),
                                label = "sym_scale_${sym.name}"
                            )

                            val borderAlpha by animateFloatAsState(
                                targetValue = if (isSelected) 0.8f else 0.15f,
                                animationSpec = tween(200),
                                label = "border_alpha_${sym.name}"
                            )

                            Row(
                                modifier = Modifier
                                    .scale(scale)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(
                                        if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.18f)
                                        else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f)
                                    )
                                    .border(
                                        width = 1.dp,
                                        brush = Brush.linearGradient(
                                            listOf(
                                                MaterialTheme.colorScheme.primary.copy(alpha = borderAlpha),
                                                MaterialTheme.colorScheme.primary.copy(alpha = borderAlpha * 0.3f)
                                            )
                                        ),
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .clickable { selectedSymbol = sym.id }
                                    .padding(horizontal = 12.dp, vertical = 7.dp)
                                    .testTag("symbol_chip_${sym.name}"),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                if (isSelected) {
                                    Box(
                                        modifier = Modifier
                                            .size(6.dp)
                                            .clip(CircleShape)
                                            .background(MaterialTheme.colorScheme.primary)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                }
                                Text(
                                    text = sym.name,
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        letterSpacing = 0.4.sp
                                    ),
                                    fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Medium,
                                    color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    // Timeframe & Range bar
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 6.dp, vertical = 2.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Intervals
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            intervals.forEach { (intVal, intLabel) ->
                                val isSelected = selectedInterval == intVal

                                val scale by animateFloatAsState(
                                    targetValue = if (isSelected) 1.05f else 1.0f,
                                    animationSpec = spring(
                                        dampingRatio = Spring.DampingRatioMediumBouncy,
                                        stiffness = Spring.StiffnessLow
                                    ),
                                    label = "interval_scale_$intLabel"
                                )

                                Box(
                                    modifier = Modifier
                                        .scale(scale)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(
                                            if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                                            else Color.Transparent
                                        )
                                        .border(
                                            width = 1.dp,
                                            color = if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.45f)
                                            else Color.Transparent,
                                            shape = RoundedCornerShape(8.dp)
                                        )
                                        .clickable { selectedInterval = intVal }
                                        .padding(horizontal = 10.dp, vertical = 5.dp)
                                        .testTag("interval_$intLabel"),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = intLabel,
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontSize = 11.sp,
                                            letterSpacing = 0.3.sp
                                        ),
                                        fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Medium,
                                        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }

                        // Fullscreen Toggle with glass styling
                        GlassIconButton(
                            icon = Icons.Default.Fullscreen,
                            contentDescription = "Fullscreen Chart",
                            tint = MaterialTheme.colorScheme.primary,
                            onClick = { isFullscreen = true },
                            size = 32.dp,
                            modifier = Modifier.testTag("fullscreen_chart_button")
                        )
                    }
                }
            }
        }

        // Chart Viewport
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            TradingViewWebView(
                htmlData = htmlData,
                modifier = Modifier.fillMaxSize(),
                testTag = "advanced_chart_webview",
                reloadTrigger = reloadTrigger
            )

            // Exit Fullscreen Floating Button
            if (isFullscreen) {
                GlassIconButton(
                    icon = Icons.Default.FullscreenExit,
                    contentDescription = "Exit Fullscreen",
                    tint = MaterialTheme.colorScheme.primary,
                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.85f),
                    onClick = { isFullscreen = false },
                    size = 42.dp,
                    modifier = Modifier
                        .padding(14.dp)
                        .align(Alignment.TopEnd)
                        .testTag("exit_fullscreen_button")
                )
            }
        }
    }

    if (showSearchDialog) {
        AlertDialog(
            onDismissRequest = { showSearchDialog = false },
            title = { Text("Search Any Ticker") },
            text = {
                Column {
                    Text(
                        text = "Enter stock, crypto, or forex symbol (e.g., NASDAQ:NVDA, BINANCE:ETHUSDT, NYSE:JPM):",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = customSymbolInput,
                        onValueChange = { customSymbolInput = it.uppercase() },
                        placeholder = { Text("e.g. NASDAQ:PLTR") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("custom_symbol_input")
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val trimmed = customSymbolInput.trim()
                        if (trimmed.isNotEmpty()) {
                            selectedSymbol = if (!trimmed.contains(":")) {
                                "NASDAQ:$trimmed"
                            } else {
                                trimmed
                            }
                        }
                        showSearchDialog = false
                    },
                    modifier = Modifier.testTag("apply_symbol_button")
                ) {
                    Text("Load Symbol")
                }
            },
            dismissButton = {
                TextButton(onClick = { showSearchDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}
