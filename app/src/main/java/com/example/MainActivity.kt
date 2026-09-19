package com.example

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.GridOn
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material.icons.filled.ViewStream
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import com.example.model.NavigationTab
import com.example.ui.components.GlassIconButton
import com.example.ui.components.GlassStatusPill
import com.example.ui.components.GlowingIconBadge
import com.example.ui.components.MarketDrawerContent
import com.example.ui.components.TickerTapeBar
import com.example.ui.components.glassmorphic
import com.example.ui.screens.AboutUsScreen
import com.example.ui.screens.CalculatorScreen
import com.example.ui.screens.ChartScreen
import com.example.ui.screens.DictionaryScreen
import com.example.ui.screens.EconomyScreen
import com.example.ui.screens.HeatmapScreen
import com.example.ui.screens.IndicatorsGuideScreen
import com.example.ui.screens.MarketHoursScreen
import com.example.ui.screens.OnboardingScreen
import com.example.ui.screens.ScreenerScreen
import com.example.ui.screens.SplashScreen
import com.example.ui.screens.SymbolDetailsScreen
import com.example.ui.theme.BullGreen
import com.example.ui.theme.MyApplicationTheme
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

enum class AppDestination {
  SPLASH,
  ONBOARDING,
  MAIN_DASHBOARD,
  ABOUT_US,
  CALCULATOR,
  DICTIONARY,
  MARKET_HOURS,
  INDICATORS_GUIDE
}

class MainActivity : ComponentActivity() {
  companion object {
    init {
      try {
        android.system.Os.setenv("LIBGL_ALWAYS_SOFTWARE", "1", true)
        android.system.Os.setenv("MESA_LOADER_DRIVER_OVERRIDE", "swrast", true)
        android.system.Os.setenv("MESA_DEBUG", "silent", true)
      } catch (_: Throwable) {
      }
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    try {
      // Clean up any stale or corrupted WebView Code Cache from previous runs to prevent Chromium simple_file_enumerator errors
      val codeCacheDir = java.io.File(cacheDir, "WebView/Default/HTTP Cache/Code Cache")
      if (codeCacheDir.exists()) {
        codeCacheDir.deleteRecursively()
      }
    } catch (_: Exception) {
    }

    enableEdgeToEdge()
    setContent {
      var isDarkTheme by remember { mutableStateOf(true) }

      MyApplicationTheme(darkTheme = isDarkTheme, dynamicColor = false) {
        val context = LocalContext.current
        val prefs = remember {
          context.getSharedPreferences("usa_stock_screener_prefs", Context.MODE_PRIVATE)
        }

        var destination by remember { mutableStateOf(AppDestination.SPLASH) }

        Crossfade(targetState = destination, label = "app_navigation_crossfade") { currentDest ->
          when (currentDest) {
            AppDestination.SPLASH -> {
              SplashScreen(
                onSplashFinished = {
                  val hasSeenOnboarding = prefs.getBoolean("has_completed_onboarding", false)
                  destination = if (hasSeenOnboarding) {
                    AppDestination.MAIN_DASHBOARD
                  } else {
                    AppDestination.ONBOARDING
                  }
                }
              )
            }

            AppDestination.ONBOARDING -> {
              BackHandler {
                val hasSeen = prefs.getBoolean("has_completed_onboarding", false)
                destination = if (hasSeen) AppDestination.MAIN_DASHBOARD else AppDestination.SPLASH
              }
              OnboardingScreen(
                onFinish = {
                  prefs.edit().putBoolean("has_completed_onboarding", true).apply()
                  destination = AppDestination.MAIN_DASHBOARD
                }
              )
            }

            AppDestination.MAIN_DASHBOARD -> {
              UsaStockScreenerApp(
                isDarkTheme = isDarkTheme,
                onToggleTheme = { isDarkTheme = !isDarkTheme },
                onOpenAboutUs = { destination = AppDestination.ABOUT_US },
                onOpenCalculator = { destination = AppDestination.CALCULATOR },
                onOpenDictionary = { destination = AppDestination.DICTIONARY },
                onOpenMarketHours = { destination = AppDestination.MARKET_HOURS },
                onOpenIndicatorsGuide = { destination = AppDestination.INDICATORS_GUIDE },
                onOpenWalkthrough = { destination = AppDestination.ONBOARDING }
              )
            }

            AppDestination.ABOUT_US -> {
              BackHandler {
                destination = AppDestination.MAIN_DASHBOARD
              }
              AboutUsScreen(
                onNavigateBack = { destination = AppDestination.MAIN_DASHBOARD },
                onOpenWalkthrough = { destination = AppDestination.ONBOARDING },
                isDark = isDarkTheme
              )
            }

            AppDestination.CALCULATOR -> {
              BackHandler {
                destination = AppDestination.MAIN_DASHBOARD
              }
              CalculatorScreen(
                onNavigateBack = { destination = AppDestination.MAIN_DASHBOARD },
                isDark = isDarkTheme
              )
            }

            AppDestination.DICTIONARY -> {
              BackHandler {
                destination = AppDestination.MAIN_DASHBOARD
              }
              DictionaryScreen(
                onNavigateBack = { destination = AppDestination.MAIN_DASHBOARD },
                isDark = isDarkTheme
              )
            }

            AppDestination.MARKET_HOURS -> {
              BackHandler {
                destination = AppDestination.MAIN_DASHBOARD
              }
              MarketHoursScreen(
                onNavigateBack = { destination = AppDestination.MAIN_DASHBOARD },
                isDark = isDarkTheme
              )
            }

            AppDestination.INDICATORS_GUIDE -> {
              BackHandler {
                destination = AppDestination.MAIN_DASHBOARD
              }
              IndicatorsGuideScreen(
                onNavigateBack = { destination = AppDestination.MAIN_DASHBOARD },
                isDark = isDarkTheme
              )
            }
          }
        }
      }
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsaStockScreenerApp(
  isDarkTheme: Boolean,
  onToggleTheme: () -> Unit,
  onOpenAboutUs: () -> Unit,
  onOpenCalculator: () -> Unit,
  onOpenDictionary: () -> Unit,
  onOpenMarketHours: () -> Unit,
  onOpenIndicatorsGuide: () -> Unit,
  onOpenWalkthrough: () -> Unit
) {
  var currentTab by remember { mutableStateOf(NavigationTab.CHART) }
  var showTickerTape by remember { mutableStateOf(true) }
  var reloadTrigger by remember { mutableIntStateOf(0) }

  val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
  val scope = rememberCoroutineScope()

  ModalNavigationDrawer(
    drawerState = drawerState,
    drawerContent = {
      MarketDrawerContent(
        currentTab = currentTab,
        onSelectTab = { tab ->
          currentTab = tab
          scope.launch { drawerState.close() }
        },
        onOpenCalculator = {
          scope.launch { drawerState.close() }
          onOpenCalculator()
        },
        onOpenDictionary = {
          scope.launch { drawerState.close() }
          onOpenDictionary()
        },
        onOpenMarketHours = {
          scope.launch { drawerState.close() }
          onOpenMarketHours()
        },
        onOpenIndicatorsGuide = {
          scope.launch { drawerState.close() }
          onOpenIndicatorsGuide()
        },
        onOpenAboutUs = {
          scope.launch { drawerState.close() }
          onOpenAboutUs()
        },
        onOpenWalkthrough = {
          scope.launch { drawerState.close() }
          onOpenWalkthrough()
        },
        isDarkTheme = isDarkTheme,
        onToggleTheme = onToggleTheme
      )
    }
  ) {
    Scaffold(
      modifier = Modifier
        .fillMaxSize()
        .testTag("usa_stock_screener_scaffold"),
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
              .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              GlassIconButton(
                icon = Icons.Default.Menu,
                contentDescription = "Open Navigation Drawer",
                tint = MaterialTheme.colorScheme.primary,
                onClick = { scope.launch { drawerState.open() } },
                modifier = Modifier.testTag("drawer_menu_button")
              )
              GlowingIconBadge(
                icon = Icons.AutoMirrored.Filled.TrendingUp,
                contentDescription = null,
                size = 38.dp,
                iconSize = 20.dp,
                tint = MaterialTheme.colorScheme.primary,
                containerColor = MaterialTheme.colorScheme.primary
              )
              Column {
                Text(
                  text = "USA Stock Screener",
                  style = MaterialTheme.typography.titleMedium,
                  fontWeight = FontWeight.ExtraBold,
                  letterSpacing = (-0.3).sp,
                  color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(2.dp))
                GlassStatusPill(
                  text = "LIVE • FREE & PRIVATE",
                  accentColor = BullGreen
                )
              }
            }

            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
              GlassIconButton(
                icon = Icons.Default.Calculate,
                contentDescription = "Investment Calculators",
                tint = MaterialTheme.colorScheme.primary,
                onClick = onOpenCalculator,
                modifier = Modifier.testTag("calculator_header_button")
              )
              GlassIconButton(
                icon = Icons.Default.Info,
                contentDescription = "About Us & Contact",
                tint = MaterialTheme.colorScheme.primary,
                onClick = onOpenAboutUs,
                modifier = Modifier.testTag("about_us_button")
              )
              GlassIconButton(
                icon = Icons.Default.ViewStream,
                contentDescription = "Toggle Ticker Tape",
                tint = if (showTickerTape) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                onClick = { showTickerTape = !showTickerTape },
                modifier = Modifier.testTag("toggle_ticker_tape_button")
              )
              GlassIconButton(
                icon = Icons.Default.Refresh,
                contentDescription = "Refresh Market Data",
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                onClick = { reloadTrigger++ },
                modifier = Modifier.testTag("refresh_button")
              )
              GlassIconButton(
                icon = if (isDarkTheme) Icons.Default.LightMode else Icons.Default.DarkMode,
                contentDescription = if (isDarkTheme) "Switch to Light Mode" else "Switch to Dark Mode",
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                onClick = onToggleTheme,
                modifier = Modifier.testTag("toggle_theme_button")
              )
            }
          }

        // Live Ticker Tape Bar
        AnimatedVisibility(
          visible = showTickerTape,
          enter = expandVertically() + fadeIn(),
          exit = shrinkVertically() + fadeOut()
        ) {
          TickerTapeBar(
            isDark = isDarkTheme,
            reloadTrigger = reloadTrigger
          )
        }

        Box(
          modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(
              Brush.horizontalGradient(
                listOf(
                  Color.Transparent,
                  MaterialTheme.colorScheme.primary.copy(alpha = 0.35f),
                  Color.Transparent
                )
              )
            )
        )
      }
    },
    bottomBar = {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .windowInsetsPadding(WindowInsets.navigationBars)
          .padding(horizontal = 14.dp, vertical = 8.dp)
          .glassmorphic(
            backgroundColor = MaterialTheme.colorScheme.surface,
            borderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
            shape = RoundedCornerShape(26.dp),
            borderWidth = 1.dp
          )
          .testTag("bottom_navigation_bar")
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp, horizontal = 4.dp),
          horizontalArrangement = Arrangement.SpaceEvenly,
          verticalAlignment = Alignment.CenterVertically
        ) {
          NavigationTab.entries.forEach { tab ->
            val isSelected = currentTab == tab
            val icon = when (tab) {
              NavigationTab.CHART -> Icons.Default.ShowChart
              NavigationTab.HEATMAP -> Icons.Default.GridOn
              NavigationTab.SCREENER -> Icons.Default.FilterList
              NavigationTab.ECONOMY -> Icons.Default.Public
              NavigationTab.SYMBOL_DETAILS -> Icons.Default.Analytics
            }

            val scale by animateFloatAsState(
              targetValue = if (isSelected) 1.06f else 1.0f,
              animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
              ),
              label = "tab_scale_${tab.name}"
            )

            val pillAlpha by animateFloatAsState(
              targetValue = if (isSelected) 1.0f else 0.0f,
              animationSpec = tween(220),
              label = "pill_alpha_${tab.name}"
            )

            val contentColor by animateColorAsState(
              targetValue = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
              animationSpec = tween(200),
              label = "tab_color_${tab.name}"
            )

            Column(
              modifier = Modifier
                .scale(scale)
                .clip(RoundedCornerShape(18.dp))
                .background(
                  if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.15f * pillAlpha)
                  else Color.Transparent
                )
                .border(
                  width = 1.dp,
                  brush = Brush.radialGradient(
                    listOf(
                      MaterialTheme.colorScheme.primary.copy(alpha = 0.4f * pillAlpha),
                      Color.Transparent
                    )
                  ),
                  shape = RoundedCornerShape(18.dp)
                )
                .clickable { currentTab = tab }
                .padding(horizontal = 10.dp, vertical = 6.dp)
                .testTag("nav_item_${tab.name}"),
              horizontalAlignment = Alignment.CenterHorizontally,
              verticalArrangement = Arrangement.Center
            ) {
              Box(
                modifier = Modifier.size(26.dp),
                contentAlignment = Alignment.Center
              ) {
                if (isSelected) {
                  Box(
                    modifier = Modifier
                      .size(24.dp)
                      .clip(CircleShape)
                      .background(
                        Brush.radialGradient(
                          listOf(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.35f),
                            Color.Transparent
                          )
                        )
                      )
                  )
                }
                Icon(
                  imageVector = icon,
                  contentDescription = tab.title,
                  tint = contentColor,
                  modifier = Modifier.size(20.dp)
                )
              }

              Spacer(modifier = Modifier.height(2.dp))

              Text(
                text = tab.title,
                style = MaterialTheme.typography.labelSmall.copy(
                  fontSize = 10.sp,
                  fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Medium,
                  letterSpacing = 0.3.sp
                ),
                color = contentColor
              )
            }
          }
        }
      }
    }
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
        .background(MaterialTheme.colorScheme.background)
    ) {
      when (currentTab) {
        NavigationTab.CHART -> {
          ChartScreen(
            isDark = isDarkTheme,
            reloadTrigger = reloadTrigger
          )
        }
        NavigationTab.HEATMAP -> {
          HeatmapScreen(
            isDark = isDarkTheme,
            reloadTrigger = reloadTrigger
          )
        }
        NavigationTab.SCREENER -> {
          ScreenerScreen(
            isDark = isDarkTheme,
            reloadTrigger = reloadTrigger
          )
        }
        NavigationTab.ECONOMY -> {
          EconomyScreen(
            isDark = isDarkTheme,
            reloadTrigger = reloadTrigger
          )
        }
        NavigationTab.SYMBOL_DETAILS -> {
          SymbolDetailsScreen(
            isDark = isDarkTheme,
            reloadTrigger = reloadTrigger
          )
        }
      }
    }
  }
  }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
  Text(text = "Hello $name!", modifier = modifier)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
  MyApplicationTheme { Greeting("Android") }
}

