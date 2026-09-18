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
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import com.example.ui.components.GlassStatusPill
import com.example.ui.components.GlowingIconBadge
import com.example.ui.components.TradingViewWebView
import com.example.ui.components.glassmorphic
import com.example.ui.theme.BullGreen
import com.example.util.TradingViewHtmlProvider

@Composable
fun ScreenerScreen(
    isDark: Boolean,
    modifier: Modifier = Modifier,
    reloadTrigger: Int = 0
) {
    val htmlData = remember(isDark, reloadTrigger) {
        TradingViewHtmlProvider.getScreenerHtml(isDark)
    }

    var activeFilterPreset by remember { mutableStateOf("Mega-Cap") }

    val filterPresets = listOf(
        "💎 Mega-Cap",
        "🔥 Top Gainers",
        "⚡ High Volume",
        "📈 Breakouts",
        "🎯 Undervalued"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("screener_screen")
    ) {
        // Glassmorphic Screener Command Deck
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
            Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        GlowingIconBadge(
                            icon = Icons.Default.FilterList,
                            contentDescription = "Stock Screener",
                            size = 38.dp,
                            iconSize = 20.dp,
                            tint = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(12.dp)
                        )
                        Column {
                            Text(
                                text = "USA Stock Screener",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = (-0.2).sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "NASDAQ • NYSE • AMEX",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontSize = 9.5.sp,
                                    letterSpacing = 0.5.sp
                                ),
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    GlassStatusPill(
                        text = "ACTIVE SCAN",
                        accentColor = BullGreen
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Interactive preset filter pills
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    filterPresets.forEach { preset ->
                        val isSelected = activeFilterPreset == preset

                        val scale by animateFloatAsState(
                            targetValue = if (isSelected) 1.05f else 1.0f,
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                stiffness = Spring.StiffnessLow
                            ),
                            label = "preset_scale_$preset"
                        )

                        val borderAlpha by animateFloatAsState(
                            targetValue = if (isSelected) 0.75f else 0.15f,
                            animationSpec = tween(200),
                            label = "preset_border_$preset"
                        )

                        Row(
                            modifier = Modifier
                                .scale(scale)
                                .clip(RoundedCornerShape(12.dp))
                                .background(
                                    if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.16f)
                                    else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.40f)
                                )
                                .border(
                                    width = 1.dp,
                                    brush = Brush.linearGradient(
                                        listOf(
                                            MaterialTheme.colorScheme.primary.copy(alpha = borderAlpha),
                                            MaterialTheme.colorScheme.primary.copy(alpha = borderAlpha * 0.2f)
                                        )
                                    ),
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .clickable { activeFilterPreset = preset }
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                                .testTag("screener_preset_$preset"),
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
                                text = preset,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontSize = 11.sp,
                                    letterSpacing = 0.2.sp
                                ),
                                fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Medium,
                                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            TradingViewWebView(
                htmlData = htmlData,
                modifier = Modifier.fillMaxSize(),
                testTag = "screener_webview",
                reloadTrigger = reloadTrigger
            )
        }
    }
}
