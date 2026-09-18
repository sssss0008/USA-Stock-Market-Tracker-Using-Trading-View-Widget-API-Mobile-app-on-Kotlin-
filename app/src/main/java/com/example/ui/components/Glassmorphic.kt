package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.BullGreen

/**
 * Creates a frosted glassmorphic card style with specular gradient borders and translucent backdrop.
 */
fun Modifier.glassmorphic(
    backgroundColor: Color,
    borderColor: Color = Color.White.copy(alpha = 0.18f),
    shape: Shape = RoundedCornerShape(16.dp),
    borderWidth: Dp = 1.dp
): Modifier = this
    .clip(shape)
    .background(
        Brush.verticalGradient(
            colors = listOf(
                backgroundColor.copy(alpha = 0.85f),
                backgroundColor.copy(alpha = 0.65f)
            )
        )
    )
    .border(
        width = borderWidth,
        brush = Brush.linearGradient(
            colors = listOf(
                borderColor,
                borderColor.copy(alpha = 0.05f),
                borderColor.copy(alpha = 0.25f)
            )
        ),
        shape = shape
    )

/**
 * Modern icon container with radial gradient glow and subtle specular border.
 * Avoids plain, generic AI icons by embedding icons into handcrafted visual tokens.
 */
@Composable
fun GlowingIconBadge(
    icon: ImageVector,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    size: Dp = 38.dp,
    iconSize: Dp = 20.dp,
    tint: Color = MaterialTheme.colorScheme.primary,
    containerColor: Color = tint.copy(alpha = 0.12f),
    borderColor: Color = tint.copy(alpha = 0.35f),
    shape: Shape = RoundedCornerShape(12.dp)
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(shape)
            .background(
                Brush.radialGradient(
                    colors = listOf(
                        containerColor.copy(alpha = 0.30f),
                        containerColor.copy(alpha = 0.10f)
                    )
                )
            )
            .border(
                width = 1.dp,
                brush = Brush.linearGradient(
                    colors = listOf(
                        borderColor,
                        borderColor.copy(alpha = 0.10f)
                    )
                ),
                shape = shape
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = tint,
            modifier = Modifier.size(iconSize)
        )
    }
}

/**
 * Live pulsing beacon ring (radar animation) used for live market data status.
 */
@Composable
fun PulsingLiveBeacon(
    modifier: Modifier = Modifier,
    color: Color = BullGreen,
    size: Dp = 8.dp,
    ringSize: Dp = 18.dp
) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse_beacon")

    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.6f,
        targetValue = 1.6f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "pulse_scale"
    )

    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 0.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "pulse_alpha"
    )

    Box(
        modifier = modifier.size(ringSize),
        contentAlignment = Alignment.Center
    ) {
        // Expanding radar ring
        Box(
            modifier = Modifier
                .size(ringSize)
                .scale(pulseScale)
                .clip(CircleShape)
                .background(color.copy(alpha = pulseAlpha))
        )
        // Core solid dot
        Box(
            modifier = Modifier
                .size(size)
                .clip(CircleShape)
                .background(color)
        )
    }
}

/**
 * Glassmorphic interactive action button with spring physics on press/touch.
 */
@Composable
fun GlassIconButton(
    icon: ImageVector,
    contentDescription: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    tint: Color = MaterialTheme.colorScheme.onSurface,
    containerColor: Color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
    size: Dp = 40.dp
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.90f else 1.0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "glass_button_scale"
    )

    Box(
        modifier = modifier
            .size(size)
            .scale(scale)
            .glassmorphic(
                backgroundColor = containerColor,
                shape = CircleShape,
                borderWidth = 1.dp
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = tint,
            modifier = Modifier.size(20.dp)
        )
    }
}

/**
 * Glassmorphic status pill with live pulse beacon and crisp typography.
 */
@Composable
fun GlassStatusPill(
    text: String,
    modifier: Modifier = Modifier,
    accentColor: Color = BullGreen,
    backgroundColor: Color = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)
) {
    Row(
        modifier = modifier
            .glassmorphic(
                backgroundColor = backgroundColor,
                borderColor = accentColor.copy(alpha = 0.35f),
                shape = RoundedCornerShape(20.dp),
                borderWidth = 1.dp
            )
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        PulsingLiveBeacon(
            color = accentColor,
            size = 6.dp,
            ringSize = 14.dp
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 10.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.6.sp
            ),
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
