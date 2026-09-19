package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.automirrored.filled.ShowChart
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.CandlestickChart
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.GridOn
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tour
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.NavigationTab
import com.example.ui.theme.BullGreen
import com.example.ui.theme.FinancialBlue

@Composable
fun MarketDrawerContent(
    currentTab: NavigationTab,
    onSelectTab: (NavigationTab) -> Unit,
    onOpenCalculator: () -> Unit,
    onOpenDictionary: () -> Unit,
    onOpenMarketHours: () -> Unit,
    onOpenIndicatorsGuide: () -> Unit,
    onOpenAboutUs: () -> Unit,
    onOpenWalkthrough: () -> Unit,
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit,
    modifier: Modifier = Modifier
) {
    ModalDrawerSheet(
        modifier = modifier
            .width(320.dp)
            .fillMaxHeight()
            .testTag("navigation_drawer_sheet"),
        drawerContainerColor = MaterialTheme.colorScheme.surface,
        drawerContentColor = MaterialTheme.colorScheme.onSurface
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
        ) {
            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                FinancialBlue.copy(alpha = 0.2f),
                                Color.Transparent
                            )
                        )
                    )
                    .statusBarsPadding()
                    .padding(horizontal = 20.dp, vertical = 20.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        GlowingIconBadge(
                            icon = Icons.AutoMirrored.Filled.TrendingUp,
                            contentDescription = null,
                            size = 46.dp,
                            iconSize = 26.dp,
                            tint = FinancialBlue,
                            containerColor = FinancialBlue
                        )
                        Column {
                            Text(
                                text = "USA Stock Screener",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.ExtraBold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "Professional Market Suite",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        GlassStatusPill(
                            text = "100% FREE",
                            accentColor = BullGreen
                        )
                        GlassStatusPill(
                            text = "ZERO TRACKING",
                            accentColor = FinancialBlue
                        )
                    }
                }
            }

            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 16.dp),
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f)
            )

            // Section 1: Core Dashboards
            DrawerSectionHeader(title = "CORE DASHBOARDS")

            DrawerNavItem(
                label = "Live Interactive Charts",
                icon = Icons.Default.CandlestickChart,
                selected = currentTab == NavigationTab.CHART,
                onClick = { onSelectTab(NavigationTab.CHART) },
                testTag = "drawer_item_chart"
            )
            DrawerNavItem(
                label = "Stock Screener (S&P 500)",
                icon = Icons.Default.Search,
                selected = currentTab == NavigationTab.SCREENER,
                onClick = { onSelectTab(NavigationTab.SCREENER) },
                testTag = "drawer_item_screener"
            )
            DrawerNavItem(
                label = "Sector Heatmap",
                icon = Icons.Default.GridOn,
                selected = currentTab == NavigationTab.HEATMAP,
                onClick = { onSelectTab(NavigationTab.HEATMAP) },
                testTag = "drawer_item_heatmap"
            )
            DrawerNavItem(
                label = "Macro & Treasury Yields",
                icon = Icons.Default.AccountBalance,
                selected = currentTab == NavigationTab.ECONOMY,
                onClick = { onSelectTab(NavigationTab.ECONOMY) },
                testTag = "drawer_item_economy"
            )
            DrawerNavItem(
                label = "Symbol Deep Dive",
                icon = Icons.AutoMirrored.Filled.ShowChart,
                selected = currentTab == NavigationTab.SYMBOL_DETAILS,
                onClick = { onSelectTab(NavigationTab.SYMBOL_DETAILS) },
                testTag = "drawer_item_symbol_details"
            )

            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f)
            )

            // Section 2: Financial Toolkit (User requested Calculator, Dictionary, etc.)
            DrawerSectionHeader(title = "FINANCIAL TOOLKIT & EDUCATION")

            DrawerNavItem(
                label = "Financial Calculators",
                badge = "Profit • Div • Risk",
                icon = Icons.Default.Calculate,
                selected = false,
                onClick = onOpenCalculator,
                testTag = "drawer_item_calculator"
            )
            DrawerNavItem(
                label = "Market Dictionary",
                badge = "40+ Terms",
                icon = Icons.AutoMirrored.Filled.MenuBook,
                selected = false,
                onClick = onOpenDictionary,
                testTag = "drawer_item_dictionary"
            )
            DrawerNavItem(
                label = "Trading Hours & Sessions",
                badge = "NYSE • NASDAQ",
                icon = Icons.Default.Schedule,
                selected = false,
                onClick = onOpenMarketHours,
                testTag = "drawer_item_market_hours"
            )
            DrawerNavItem(
                label = "Technical Indicators Guide",
                badge = "RSI • MACD",
                icon = Icons.AutoMirrored.Filled.ShowChart,
                selected = false,
                onClick = onOpenIndicatorsGuide,
                testTag = "drawer_item_indicators_guide"
            )

            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f)
            )

            // Section 3: App Controls & Info
            DrawerSectionHeader(title = "PREFERENCES & SUPPORT")

            DrawerNavItem(
                label = if (isDarkTheme) "Switch to Light Theme" else "Switch to Dark Theme",
                icon = if (isDarkTheme) Icons.Default.LightMode else Icons.Default.DarkMode,
                selected = false,
                onClick = onToggleTheme,
                testTag = "drawer_item_theme"
            )
            DrawerNavItem(
                label = "Interactive Walkthrough",
                icon = Icons.Default.Tour,
                selected = false,
                onClick = onOpenWalkthrough,
                testTag = "drawer_item_walkthrough"
            )
            DrawerNavItem(
                label = "About Us & Privacy Guarantee",
                icon = Icons.Default.Info,
                selected = false,
                onClick = onOpenAboutUs,
                testTag = "drawer_item_about_us"
            )

            Spacer(modifier = Modifier.weight(1f, fill = false))
            Spacer(modifier = Modifier.height(16.dp))

            // Footer
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 12.dp)
            ) {
                Column {
                    Text(
                        text = "USA Stock Screener v1.0.0",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "TradingView & Yahoo Finance Data Feeds",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                    )
                }
            }
        }
    }
}

@Composable
private fun DrawerSectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.labelSmall,
        fontWeight = FontWeight.ExtraBold,
        letterSpacing = 1.sp,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
    )
}

@Composable
private fun DrawerNavItem(
    label: String,
    icon: ImageVector,
    selected: Boolean,
    onClick: () -> Unit,
    testTag: String,
    badge: String? = null
) {
    NavigationDrawerItem(
        label = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                    maxLines = 1
                )
                if (badge != null) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = badge,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        },
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(22.dp)
            )
        },
        selected = selected,
        onClick = onClick,
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 2.dp)
            .testTag(testTag),
        shape = RoundedCornerShape(12.dp),
        colors = NavigationDrawerItemDefaults.colors(
            selectedContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
            selectedTextColor = MaterialTheme.colorScheme.primary,
            unselectedTextColor = MaterialTheme.colorScheme.onSurface,
            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    )
}
