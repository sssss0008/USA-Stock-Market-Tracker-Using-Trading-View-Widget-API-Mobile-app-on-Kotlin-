package com.example.ui.screens

import android.content.Context
import android.widget.Toast
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.PlayCircleOutline
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.GlassIconButton
import com.example.ui.components.GlassStatusPill
import com.example.ui.components.GlowingIconBadge
import com.example.ui.components.glassmorphic
import com.example.ui.theme.BullGreen
import com.example.ui.theme.FinancialBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutUsScreen(
    onNavigateBack: () -> Unit,
    onOpenWalkthrough: () -> Unit,
    isDark: Boolean,
    modifier: Modifier = Modifier
) {
    val uriHandler = LocalUriHandler.current
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current

    val contactEmail = "awiskaracharya@gmail.com"
    val contactPhone = "+977 9827106244"
    val cleanPhoneNumber = "+9779827106244"
    val websiteUrl = "https://wealthorbitcenter.com"
    val developerLinkedIn = "https://www.linkedin.com/in/awiskaracharya/"
    val contributorLinkedIn = "https://www.linkedin.com/in/ujjwal-silwal-1a6360301/"

    fun copyToClipboard(text: String, label: String) {
        clipboardManager.setText(AnnotatedString(text))
        Toast.makeText(context, "$label copied to clipboard!", Toast.LENGTH_SHORT).show()
    }

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 6.dp)
                    .glassmorphic(
                        backgroundColor = MaterialTheme.colorScheme.surface,
                        borderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.25f),
                        shape = RoundedCornerShape(20.dp),
                        borderWidth = 1.dp
                    )
                    .padding(horizontal = 10.dp, vertical = 8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
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
                            modifier = Modifier.testTag("about_back_button")
                        )
                        Text(
                            text = "About USA Stock Screener",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    GlassStatusPill(
                        text = "100% FREE",
                        accentColor = BullGreen
                    )
                }
            }
        },
        modifier = modifier
            .fillMaxSize()
            .testTag("about_us_screen")
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Hero App Header Card
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isDark) Color(0xFF0F223D) else Color(0xFFE3F2FD)
                ),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    if (isDark) Color(0xFF1E3A5F) else Color(0xFFBBDEFB)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(4.dp, RoundedCornerShape(20.dp))
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Logo Icon
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.linearGradient(
                                    listOf(FinancialBlue, BullGreen)
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.TrendingUp,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(38.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "USA Stock Screener",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 22.sp
                        ),
                        color = if (isDark) Color.White else Color(0xFF0D47A1)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Version 1.0.0 • Free Community Project",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = if (isDark) Color(0xFF90CAF9) else Color(0xFF1565C0)
                        )
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Real-time USA stock screening, interactive TradingView charts, sector heatmaps, and macroeconomic intelligence.",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 13.sp,
                            lineHeight = 18.sp,
                            textAlign = TextAlign.Center
                        ),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Replay Onboarding Walkthrough button
                    OutlinedButton(
                        onClick = onOpenWalkthrough,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("replay_walkthrough_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayCircleOutline,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "View 3-Step App Walkthrough",
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            // PRIVACY & ZERO DATA TRACKING GUARANTEE CARD
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isDark) Color(0xFF0A291A) else Color(0xFFE8F5E9)
                ),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    if (isDark) Color(0xFF00E676).copy(alpha = 0.4f) else Color(0xFFA5D6A7)
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Security,
                            contentDescription = "Privacy Shield",
                            tint = BullGreen,
                            modifier = Modifier.size(24.dp)
                        )
                        Text(
                            text = "100% Privacy & Zero-Tracking Guarantee",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = if (isDark) Color.White else Color(0xFF1B5E20)
                        )
                    }

                    Text(
                        text = "All of your data is NOT tracked and we do NOT use your data. There is no account required, no cookies, no advertising telemetry, and no personal profiling. All market screening calculations are handled transparently. This is a 100% free open project for smart investors.",
                        style = MaterialTheme.typography.bodySmall.copy(
                            lineHeight = 19.sp,
                            fontSize = 13.sp
                        ),
                        color = if (isDark) Color(0xFFC8E6C9) else Color(0xFF2E7D32)
                    )
                }
            }

            // DEVELOPER & CONTRIBUTOR SECTION
            Text(
                text = "Development Team",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(horizontal = 4.dp)
            )

            // Lead Developer: Awiskar Acharya
            PersonContactCard(
                name = "Awiskar Acharya",
                role = "Lead Developer & Creator",
                linkedInUrl = developerLinkedIn,
                email = contactEmail,
                isLead = true,
                isDark = isDark,
                onOpenUrl = { uriHandler.openUri(developerLinkedIn) },
                onSendEmail = {
                    try {
                        uriHandler.openUri("mailto:$contactEmail?subject=USA%20Stock%20Screener%20Inquiry")
                    } catch (e: Exception) {
                        copyToClipboard(contactEmail, "Email")
                    }
                },
                onCopyEmail = { copyToClipboard(contactEmail, "Developer Email") }
            )

            // Contributor: Ujjwal Silwal
            PersonContactCard(
                name = "Ujjwal Silwal",
                role = "Project Contributor",
                linkedInUrl = contributorLinkedIn,
                email = null,
                isLead = false,
                isDark = isDark,
                onOpenUrl = { uriHandler.openUri(contributorLinkedIn) },
                onSendEmail = null,
                onCopyEmail = null
            )

            // OFFICIAL WEBSITE SECTION
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { uriHandler.openUri(websiteUrl) }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(FinancialBlue.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Language,
                                contentDescription = "Website",
                                tint = FinancialBlue,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        Column {
                            Text(
                                text = "Official Website",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "wealthorbitcenter.com",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.OpenInNew,
                        contentDescription = "Open Website",
                        tint = FinancialBlue,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            // CONTACT & FEEDBACK
            Text(
                text = "Contact & Feedback",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(horizontal = 4.dp)
            )

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // Email row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Email,
                                contentDescription = null,
                                tint = FinancialBlue,
                                modifier = Modifier.size(22.dp)
                            )
                            Column {
                                Text(
                                    text = "Email Contact",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = contactEmail,
                                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }

                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            IconButton(
                                onClick = { copyToClipboard(contactEmail, "Email") },
                                modifier = Modifier.size(36.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ContentCopy,
                                    contentDescription = "Copy Email",
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                            IconButton(
                                onClick = {
                                    try {
                                        uriHandler.openUri("mailto:$contactEmail?subject=USA%20Stock%20Screener")
                                    } catch (e: Exception) {
                                        copyToClipboard(contactEmail, "Email")
                                    }
                                },
                                modifier = Modifier.size(36.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.OpenInNew,
                                    contentDescription = "Send Email",
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }

                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))

                    // Phone & Feedback row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Phone,
                                contentDescription = null,
                                tint = BullGreen,
                                modifier = Modifier.size(22.dp)
                            )
                            Column {
                                Text(
                                    text = "Feedback & WhatsApp",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = contactPhone,
                                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }

                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            // WhatsApp launch
                            IconButton(
                                onClick = {
                                    try {
                                        uriHandler.openUri("https://wa.me/9779827106244")
                                    } catch (e: Exception) {
                                        copyToClipboard(contactPhone, "Phone Number")
                                    }
                                },
                                modifier = Modifier.size(36.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.Chat,
                                    contentDescription = "WhatsApp Feedback",
                                    tint = BullGreen,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                            // Phone Dial
                            IconButton(
                                onClick = {
                                    try {
                                        uriHandler.openUri("tel:$cleanPhoneNumber")
                                    } catch (e: Exception) {
                                        copyToClipboard(contactPhone, "Phone Number")
                                    }
                                },
                                modifier = Modifier.size(36.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Phone,
                                    contentDescription = "Call Feedback",
                                    tint = BullGreen,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }
            }

            // DEVELOPER CONTRIBUTION INVITATION CARD
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isDark) Color(0xFF1A1A2E) else Color(0xFFF3E5F5)
                ),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    if (isDark) Color(0xFF512DA8).copy(alpha = 0.4f) else Color(0xFFCE93D8)
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Code,
                            contentDescription = "Open Source Contribution",
                            tint = Color(0xFFAB47BC),
                            modifier = Modifier.size(22.dp)
                        )
                        Text(
                            text = "Developer Contributions Welcome",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = if (isDark) Color.White else Color(0xFF4A148C)
                        )
                    }

                    Text(
                        text = "If you are a developer and want to contribute features, improve technical screeners, or collaborate on new market modules, you are warmly welcome! Please send an email with your proposals or repository links to awiskaracharya@gmail.com.",
                        style = MaterialTheme.typography.bodySmall.copy(
                            lineHeight = 18.sp,
                            fontSize = 13.sp
                        ),
                        color = if (isDark) Color(0xFFE1BEE7) else Color(0xFF6A1B9A)
                    )

                    Button(
                        onClick = {
                            try {
                                uriHandler.openUri("mailto:$contactEmail?subject=USA%20Stock%20Screener%20Developer%20Contribution")
                            } catch (e: Exception) {
                                copyToClipboard(contactEmail, "Developer Email")
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8E24AA)),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Mail for Developer Collaboration",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun PersonContactCard(
    name: String,
    role: String,
    linkedInUrl: String,
    email: String?,
    isLead: Boolean,
    isDark: Boolean,
    onOpenUrl: () -> Unit,
    onSendEmail: (() -> Unit)?,
    onCopyEmail: (() -> Unit)?
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (isLead) BullGreen.copy(alpha = 0.5f) else MaterialTheme.colorScheme.outlineVariant
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(CircleShape)
                            .background(
                                if (isLead) BullGreen.copy(alpha = 0.2f) else FinancialBlue.copy(alpha = 0.2f)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = name,
                            tint = if (isLead) BullGreen else FinancialBlue,
                            modifier = Modifier.size(26.dp)
                        )
                    }

                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = name,
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            if (isLead) {
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = BullGreen.copy(alpha = 0.2f)
                                ) {
                                    Text(
                                        text = "Author",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = BullGreen,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 10.sp
                                        ),
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }
                        }
                        Text(
                            text = role,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            // Action Buttons (LinkedIn & Email)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = onOpenUrl,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.OpenInNew,
                        contentDescription = "LinkedIn",
                        modifier = Modifier.size(16.dp),
                        tint = Color(0xFF0A66C2)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "LinkedIn",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = Color(0xFF0A66C2)
                    )
                }

                if (onSendEmail != null) {
                    Button(
                        onClick = onSendEmail,
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = FinancialBlue),
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = "Email",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Email",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                }
            }
        }
    }
}
