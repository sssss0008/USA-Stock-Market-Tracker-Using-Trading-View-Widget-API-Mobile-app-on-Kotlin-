package com.example.ui.screens

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
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
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
import com.example.model.SymbolSubTab
import com.example.ui.components.GlowingIconBadge
import com.example.ui.components.TradingViewWebView
import com.example.ui.components.glassmorphic
import com.example.util.TradingViewHtmlProvider

@Composable
fun SymbolDetailsScreen(
    isDark: Boolean,
    modifier: Modifier = Modifier,
    reloadTrigger: Int = 0
) {
    var selectedSymbol by remember { mutableStateOf("NASDAQ:AAPL") }
    var selectedSubTab by remember { mutableStateOf(SymbolSubTab.ALL) }
    var showSearchDialog by remember { mutableStateOf(false) }
    var customSymbolInput by remember { mutableStateOf("") }
    var technicalInterval by remember { mutableStateOf("1M") }

    val currentHtml = remember(selectedSymbol, selectedSubTab, technicalInterval, isDark, reloadTrigger) {
        when (selectedSubTab) {
            SymbolSubTab.ALL -> TradingViewHtmlProvider.getSymbolDetailsFullHtml(selectedSymbol, isDark)
            SymbolSubTab.INFO -> TradingViewHtmlProvider.getSymbolInfoHtml(selectedSymbol, isDark)
            SymbolSubTab.TECHNICAL -> TradingViewHtmlProvider.getTechnicalAnalysisHtml(selectedSymbol, technicalInterval, isDark)
            SymbolSubTab.FINANCIALS -> TradingViewHtmlProvider.getFinancialsHtml(selectedSymbol, isDark)
            SymbolSubTab.PROFILE -> TradingViewHtmlProvider.getCompanyProfileHtml(selectedSymbol, isDark)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("symbol_details_screen")
    ) {
        // Glassmorphic Symbol Selector Deck
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
            Column(modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp)) {
                // Symbols Carousel
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    GlowingIconBadge(
                        icon = Icons.Default.Search,
                        contentDescription = "Search Ticker",
                        size = 36.dp,
                        iconSize = 18.dp,
                        tint = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .clickable { showSearchDialog = true }
                            .testTag("search_detail_symbol_button")
                    )

                    POPULAR_SYMBOLS.forEach { sym ->
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
                            label = "sym_border_${sym.name}"
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
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                                .testTag("detail_chip_${sym.name}"),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (isSelected) {
                                Box(
                                    modifier = Modifier
                                        .size(5.dp)
                                        .clip(CircleShape)
                                        .background(MaterialTheme.colorScheme.primary)
                                )
                                Spacer(modifier = Modifier.width(5.dp))
                            }
                            Text(
                                text = "${sym.name} (${sym.id.substringAfter(":")})",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    letterSpacing = 0.3.sp
                                ),
                                fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Medium,
                                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Sub-tabs: All in One, Overview, Technical, Financials, Profile
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SymbolSubTab.entries.forEach { subTab ->
                        val isSelected = selectedSubTab == subTab

                        val scale by animateFloatAsState(
                            targetValue = if (isSelected) 1.04f else 1.0f,
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                stiffness = Spring.StiffnessLow
                            ),
                            label = "subtab_scale_${subTab.name}"
                        )

                        Box(
                            modifier = Modifier
                                .scale(scale)
                                .clip(RoundedCornerShape(10.dp))
                                .background(
                                    if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.20f)
                                    else Color.Transparent
                                )
                                .border(
                                    width = 1.dp,
                                    color = if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                                    else Color.Transparent,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .clickable { selectedSubTab = subTab }
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                                .testTag("tab_detail_${subTab.name}"),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = subTab.title,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontSize = 11.sp,
                                    letterSpacing = 0.2.sp
                                ),
                                fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Medium,
                                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }

        // Interval selector for Technical tab
        if (selectedSubTab == SymbolSubTab.TECHNICAL) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 2.dp)
                    .glassmorphic(
                        backgroundColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                        borderColor = Color.White.copy(alpha = 0.08f),
                        shape = RoundedCornerShape(12.dp),
                        borderWidth = 1.dp
                    )
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Rating Interval:",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    listOf("1m" to "1 Minute", "5m" to "5 Minutes", "1h" to "1 Hour", "1D" to "1 Day", "1W" to "1 Week", "1M" to "1 Month").forEach { (intVal, _) ->
                        val isSelected = technicalInterval == intVal
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(
                                    if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.20f)
                                    else Color.Transparent
                                )
                                .border(
                                    width = 1.dp,
                                    color = if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)
                                    else Color.Transparent,
                                    shape = RoundedCornerShape(6.dp)
                                )
                                .clickable { technicalInterval = intVal }
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = intVal,
                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }

        // Main Web Content Viewport
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            TradingViewWebView(
                htmlData = currentHtml,
                modifier = Modifier.fillMaxSize(),
                testTag = "symbol_details_webview",
                reloadTrigger = reloadTrigger
            )
        }
    }

    if (showSearchDialog) {
        AlertDialog(
            onDismissRequest = { showSearchDialog = false },
            title = { Text("Look Up Any US Stock") },
            text = {
                Column {
                    Text(
                        text = "Enter any exchange:symbol (e.g., NASDAQ:AAPL, NYSE:TSM, NASDAQ:COIN, BITSTAMP:BTCUSD):",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = customSymbolInput,
                        onValueChange = { customSymbolInput = it.uppercase() },
                        placeholder = { Text("e.g. NASDAQ:NVDA") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("detail_custom_symbol_input")
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
                    modifier = Modifier.testTag("detail_apply_symbol_button")
                ) {
                    Text("Apply")
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
