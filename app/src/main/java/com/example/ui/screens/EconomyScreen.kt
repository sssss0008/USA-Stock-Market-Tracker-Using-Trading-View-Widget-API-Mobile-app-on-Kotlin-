package com.example.ui.screens

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.Icon
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
import com.example.model.EconomySubTab
import com.example.ui.components.GlassStatusPill
import com.example.ui.components.GlowingIconBadge
import com.example.ui.components.TradingViewWebView
import com.example.ui.components.glassmorphic
import com.example.ui.theme.BullGreen
import com.example.util.TradingViewHtmlProvider

@Composable
fun EconomyScreen(
    isDark: Boolean,
    modifier: Modifier = Modifier,
    reloadTrigger: Int = 0
) {
    var selectedSubTab by remember { mutableStateOf(EconomySubTab.CALENDAR) }

    val calendarHtml = remember(isDark, reloadTrigger) {
        TradingViewHtmlProvider.getEconomicCalendarHtml(isDark)
    }

    val mapHtml = remember(isDark, reloadTrigger) {
        TradingViewHtmlProvider.getEconomicMapHtml(isDark)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("economy_screen")
    ) {
        // Glassmorphic Macro Deck
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
                            icon = Icons.Default.Public,
                            contentDescription = "Macro Economy",
                            size = 38.dp,
                            iconSize = 20.dp,
                            tint = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(12.dp)
                        )
                        Column {
                            Text(
                                text = "US Macro & Fed Policy",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = (-0.2).sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "CPI • FOMC • NON-FARM PAYROLLS",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontSize = 9.5.sp,
                                    letterSpacing = 0.5.sp
                                ),
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    GlassStatusPill(
                        text = "LIVE MACRO",
                        accentColor = BullGreen
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Segmented Glass Tabs
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f))
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    val tabs = listOf(
                        EconomySubTab.CALENDAR to ("US Economic Calendar" to Icons.Default.CalendarMonth),
                        EconomySubTab.MAP to ("North America Map" to Icons.Default.Map)
                    )

                    tabs.forEach { (tab, details) ->
                        val (title, icon) = details
                        val isSelected = selectedSubTab == tab

                        val scale by animateFloatAsState(
                            targetValue = if (isSelected) 1.02f else 1.0f,
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                stiffness = Spring.StiffnessLow
                            ),
                            label = "econ_tab_scale_${tab.name}"
                        )

                        Row(
                            modifier = Modifier
                                .weight(1f)
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
                                .clickable { selectedSubTab = tab }
                                .padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = icon,
                                contentDescription = title,
                                tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = title,
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

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            when (selectedSubTab) {
                EconomySubTab.CALENDAR -> {
                    TradingViewWebView(
                        htmlData = calendarHtml,
                        modifier = Modifier.fillMaxSize(),
                        testTag = "economic_calendar_webview",
                        reloadTrigger = reloadTrigger
                    )
                }
                EconomySubTab.MAP -> {
                    TradingViewWebView(
                        htmlData = mapHtml,
                        modifier = Modifier.fillMaxSize(),
                        testTag = "economic_map_webview",
                        reloadTrigger = reloadTrigger
                    )
                }
            }
        }
    }
}
