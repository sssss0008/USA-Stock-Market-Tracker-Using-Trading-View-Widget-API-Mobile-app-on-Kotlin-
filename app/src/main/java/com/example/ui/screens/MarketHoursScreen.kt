package com.example.ui.screens

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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.ui.components.glassmorphic
import com.example.ui.theme.BearRed
import com.example.ui.theme.BullGreen
import com.example.ui.theme.FinancialBlue
import kotlinx.coroutines.delay
import java.time.DayOfWeek
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

enum class MarketSession(val title: String, val statusDesc: String, val color: Color) {
    PRE_MARKET("Pre-Market Trading", "4:00 AM – 9:30 AM ET (Early Orders)", Color(0xFFF59E0B)),
    REGULAR("Regular Market Open", "9:30 AM – 4:00 PM ET (NYSE & NASDAQ Core)", BullGreen),
    AFTER_HOURS("After-Hours Trading", "4:00 PM – 8:00 PM ET (Post-Market ECN)", Color(0xFF8B5CF6)),
    CLOSED("Market Closed", "Reopens next business day at 4:00 AM ET", Color(0xFF94A3B8))
}

data class MarketHoliday(val name: String, val date: String, val status: String)

val US_MARKET_HOLIDAYS = listOf(
    MarketHoliday("New Year's Day", "January 1", "Closed"),
    MarketHoliday("Martin Luther King Jr. Day", "3rd Monday in January", "Closed"),
    MarketHoliday("Washington's Birthday (Presidents Day)", "3rd Monday in February", "Closed"),
    MarketHoliday("Good Friday", "Friday before Easter", "Closed"),
    MarketHoliday("Memorial Day", "Last Monday in May", "Closed"),
    MarketHoliday("Juneteenth National Independence Day", "June 19", "Closed"),
    MarketHoliday("Independence Day (July 4th)", "July 4 (1:00 PM early close on July 3)", "Closed"),
    MarketHoliday("Labor Day", "1st Monday in September", "Closed"),
    MarketHoliday("Thanksgiving Day", "4th Thursday in November", "Closed (1:00 PM early close on Friday)"),
    MarketHoliday("Christmas Day", "December 25 (1:00 PM early close on Dec 24)", "Closed")
)

@Composable
fun MarketHoursScreen(
    onNavigateBack: () -> Unit,
    isDark: Boolean,
    modifier: Modifier = Modifier
) {
    var currentTimeEt by remember { mutableStateOf(getEasternTimeNow()) }

    LaunchedEffect(Unit) {
        while (true) {
            currentTimeEt = getEasternTimeNow()
            delay(1000L)
        }
    }

    val currentSession = determineSession(currentTimeEt)

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .testTag("market_hours_screen"),
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
                            modifier = Modifier.testTag("market_hours_back_button")
                        )
                        GlowingIconBadge(
                            icon = Icons.Default.Schedule,
                            contentDescription = null,
                            size = 38.dp,
                            iconSize = 20.dp,
                            tint = currentSession.color,
                            containerColor = currentSession.color
                        )
                        Column {
                            Text(
                                text = "Market Hours & Sessions",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "NYSE & NASDAQ Clock",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                    GlassStatusPill(
                        text = "NEW YORK TIME",
                        accentColor = FinancialBlue
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
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Live Status Banner
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .glassmorphic(
                        backgroundColor = currentSession.color.copy(alpha = 0.12f),
                        borderColor = currentSession.color.copy(alpha = 0.4f),
                        shape = RoundedCornerShape(18.dp),
                        borderWidth = 1.dp
                    )
                    .padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .clip(CircleShape)
                                    .background(currentSession.color)
                            )
                            Text(
                                text = currentSession.title.uppercase(),
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.ExtraBold,
                                color = currentSession.color
                            )
                        }
                        Text(
                            text = currentTimeEt.format(DateTimeFormatter.ofPattern("hh:mm:ss a z")),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Text(
                        text = currentSession.statusDesc,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            // Trading Sessions Breakdown
            Text(
                text = "DAILY US TRADING SCHEDULE (ET)",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            SessionTimeCard(
                name = "Pre-Market Session",
                timeWindow = "04:00 AM – 09:30 AM ET",
                description = "Electronic communication networks (ECNs) allow institutional and retail participants to price overnight news and earnings before the opening bell.",
                isActive = currentSession == MarketSession.PRE_MARKET,
                accent = Color(0xFFF59E0B)
            )

            SessionTimeCard(
                name = "Regular Core Hours (NYSE / NASDAQ)",
                timeWindow = "09:30 AM – 04:00 PM ET",
                description = "Highest liquidity, tightest bid-ask spreads, and standard continuous trading on major American equity exchanges.",
                isActive = currentSession == MarketSession.REGULAR,
                accent = BullGreen
            )

            SessionTimeCard(
                name = "After-Hours Session",
                timeWindow = "04:00 PM – 08:00 PM ET",
                description = "Post-closing bell trading where major companies release quarterly earnings and reports are immediately absorbed by market participants.",
                isActive = currentSession == MarketSession.AFTER_HOURS,
                accent = Color(0xFF8B5CF6)
            )

            // Market Circuit Breakers Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f)
                )
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = null,
                            tint = BearRed,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "Market-Wide Circuit Breakers (MWCB)",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Text(
                        text = "Triggered by S&P 500 declines from prior day's close:",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        CircuitLevel(level = "Level 1 (-7%)", action = "15-min Halt (before 3:25 PM)")
                        CircuitLevel(level = "Level 2 (-13%)", action = "15-min Halt (before 3:25 PM)")
                        CircuitLevel(level = "Level 3 (-20%)", action = "Halt for remainder of day")
                    }
                }
            }

            // Market Holidays Section
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.CalendarMonth,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(18.dp)
                )
                Text(
                    text = "ANNUAL NYSE & NASDAQ HOLIDAYS",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                US_MARKET_HOLIDAYS.forEach { holiday ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f))
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = holiday.name,
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = holiday.date,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(MaterialTheme.colorScheme.surface)
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = holiday.status,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = if (holiday.status == "Closed") BearRed else BullGreen
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun SessionTimeCard(
    name: String,
    timeWindow: String,
    description: String,
    isActive: Boolean,
    accent: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isActive) accent.copy(alpha = 0.12f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
        )
    ) {
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = if (isActive) accent else MaterialTheme.colorScheme.onSurface
                )
                if (isActive) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(accent)
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "ACTIVE NOW",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
            Text(
                text = timeWindow,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.SemiBold,
                color = if (isActive) accent else MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun CircuitLevel(level: String, action: String) {
    Column(modifier = Modifier.width(100.dp)) {
        Text(
            text = level,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = BearRed
        )
        Text(
            text = action,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

private fun getEasternTimeNow(): ZonedDateTime {
    return ZonedDateTime.now(ZoneId.of("America/New_York"))
}

private fun determineSession(time: ZonedDateTime): MarketSession {
    val day = time.dayOfWeek
    if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) {
        return MarketSession.CLOSED
    }
    val minutes = time.hour * 60 + time.minute
    return when {
        minutes in (4 * 60)..(9 * 60 + 30) -> MarketSession.PRE_MARKET
        minutes in (9 * 60 + 30)..(16 * 60) -> MarketSession.REGULAR
        minutes in (16 * 60)..(20 * 60) -> MarketSession.AFTER_HOURS
        else -> MarketSession.CLOSED
    }
}
