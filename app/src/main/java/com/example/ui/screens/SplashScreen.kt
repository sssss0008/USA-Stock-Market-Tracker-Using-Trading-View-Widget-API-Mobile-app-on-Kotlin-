package com.example.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.CandlestickChart
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.BullGreen
import com.example.ui.theme.FinancialBlue
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onSplashFinished: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scale = remember { Animatable(0.85f) }
    val alpha = remember { Animatable(0f) }
    val glowScale = remember { Animatable(1f) }

    LaunchedEffect(Unit) {
        // Entrance animation
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 700, easing = FastOutSlowInEasing)
        )
        alpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 500)
        )
    }

    LaunchedEffect(Unit) {
        // Subtle pulsing glow
        glowScale.animateTo(
            targetValue = 1.15f,
            animationSpec = infiniteRepeatable(
                animation = tween(1200, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            )
        )
    }

    LaunchedEffect(Unit) {
        // Automatically proceed after 2.2 seconds
        delay(2200)
        onSplashFinished()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF070E1A),
                        Color(0xFF0C192E),
                        Color(0xFF081220)
                    )
                )
            )
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                // Tap to skip splash screen immediately
                onSplashFinished()
            }
            .testTag("splash_screen"),
        contentAlignment = Alignment.Center
    ) {
        // Decorative background grid & glow canvas
        Canvas(modifier = Modifier.fillMaxSize()) {
            val canvasWidth = size.width
            val canvasHeight = size.height

            // Cyan/Blue glow gradient at center
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0x221E88E5),
                        Color(0x0A00E676),
                        Color.Transparent
                    ),
                    center = Offset(canvasWidth / 2f, canvasHeight * 0.42f),
                    radius = canvasWidth * 0.65f
                )
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(32.dp)
                .scale(scale.value)
                .alpha(alpha.value)
        ) {
            // App Logo Icon Container with Bull / Stock Market Badge
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(120.dp)
            ) {
                // Outer glowing aura
                Box(
                    modifier = Modifier
                        .size(120.dp * glowScale.value)
                        .clip(CircleShape)
                        .background(
                            Brush.radialGradient(
                                colors = listOf(
                                    Color(0x4000C853),
                                    Color(0x2000B0FF),
                                    Color.Transparent
                                )
                            )
                        )
                )

                // Logo core card
                Surface(
                    shape = RoundedCornerShape(28.dp),
                    color = Color(0xFF10243E),
                    tonalElevation = 8.dp,
                    modifier = Modifier
                        .size(92.dp)
                        .shadow(16.dp, RoundedCornerShape(28.dp), ambientColor = BullGreen, spotColor = FinancialBlue)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.TrendingUp,
                                contentDescription = null,
                                tint = BullGreen,
                                modifier = Modifier.size(46.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // App Title
            Text(
                text = "USA Stock Screener",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 0.5.sp
                ),
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Tagline
            Text(
                text = "Real-Time USA Stocks, Charts & Macro Intelligence",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                ),
                color = Color(0xFF90CAF9),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(18.dp))

            // Badges row
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0x2000E676),
                    modifier = Modifier.padding(2.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(BullGreen)
                        )
                        Text(
                            text = "100% Free Project",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = BullGreen
                            )
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0x20448AFF),
                    modifier = Modifier.padding(2.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Security,
                            contentDescription = null,
                            tint = Color(0xFF82B1FF),
                            modifier = Modifier.size(12.dp)
                        )
                        Text(
                            text = "Zero Data Tracking",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF82B1FF)
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            // Loading indicator & tap hint
            CircularProgressIndicator(
                color = BullGreen,
                strokeWidth = 2.5.dp,
                modifier = Modifier.size(28.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Tap anywhere to continue",
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                color = Color(0xFF5C6F84)
            )
        }
    }
}
