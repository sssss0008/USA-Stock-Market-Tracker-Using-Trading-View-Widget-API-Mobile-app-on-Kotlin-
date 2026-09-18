package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme =
  darkColorScheme(
    primary = FinancialBlueDark,
    onPrimary = Color(0xFF003258),
    primaryContainer = Color(0xFF00497D),
    onPrimaryContainer = Color(0xFFD1E4FF),
    secondary = BullGreenDark,
    onSecondary = Color(0xFF003920),
    secondaryContainer = Color(0xFF005230),
    onSecondaryContainer = Color(0xFF8CF8BD),
    tertiary = BearRedDark,
    background = DarkNavyBackground,
    onBackground = DarkTextPrimary,
    surface = DarkNavySurface,
    onSurface = DarkTextPrimary,
    surfaceVariant = DarkNavySurfaceVariant,
    onSurfaceVariant = DarkTextSecondary,
    outline = DarkNavyBorder,
  )

private val LightColorScheme =
  lightColorScheme(
    primary = FinancialBlue,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFD8E2FF),
    onPrimaryContainer = Color(0xFF001A41),
    secondary = BullGreen,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFA6F2CE),
    onSecondaryContainer = Color(0xFF002111),
    tertiary = BearRed,
    background = LightBackground,
    onBackground = LightTextPrimary,
    surface = LightSurface,
    onSurface = LightTextPrimary,
    surfaceVariant = LightSurfaceVariant,
    onSurfaceVariant = LightTextSecondary,
    outline = LightBorder,
  )

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  // Dynamic color is available on Android 12+
  dynamicColor: Boolean = true,
  content: @Composable () -> Unit,
) {
  val colorScheme =
    when {
      dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
        val context = LocalContext.current
        if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
      }

      darkTheme -> DarkColorScheme
      else -> LightColorScheme
    }

  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}
