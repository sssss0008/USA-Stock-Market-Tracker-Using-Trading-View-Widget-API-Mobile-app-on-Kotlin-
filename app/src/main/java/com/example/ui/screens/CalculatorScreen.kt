package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Percent
import androidx.compose.material.icons.filled.Savings
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.GlassIconButton
import com.example.ui.components.GlassStatusPill
import com.example.ui.components.GlowingIconBadge
import com.example.ui.components.glassmorphic
import com.example.ui.theme.BearRed
import com.example.ui.theme.BullGreen
import com.example.ui.theme.FinancialBlue
import java.util.Locale
import kotlin.math.pow

enum class CalculatorMode(val title: String, val icon: ImageVector) {
    PROFIT_LOSS("Profit & ROI", Icons.Default.TrendingUp),
    COMPOUND("Compound & Div", Icons.Default.Savings),
    POSITION_SIZE("Risk & Size", Icons.Default.Shield)
}

@Composable
fun CalculatorScreen(
    onNavigateBack: () -> Unit,
    isDark: Boolean,
    modifier: Modifier = Modifier
) {
    var selectedMode by remember { mutableStateOf(CalculatorMode.PROFIT_LOSS) }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .testTag("calculator_screen"),
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
                            contentDescription = "Back to Dashboard",
                            onClick = onNavigateBack,
                            tint = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.testTag("calculator_back_button")
                        )
                        GlowingIconBadge(
                            icon = Icons.Default.Calculate,
                            contentDescription = null,
                            size = 38.dp,
                            iconSize = 20.dp,
                            tint = FinancialBlue,
                            containerColor = FinancialBlue
                        )
                        Column {
                            Text(
                                text = "Financial Calculators",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "Instant Wall Street Math",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                    GlassStatusPill(
                        text = "100% OFFLINE",
                        accentColor = BullGreen
                    )
                }

                // Calculator Mode Selector Bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 6.dp)
                        .glassmorphic(
                            backgroundColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                            borderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(12.dp),
                            borderWidth = 1.dp
                        )
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    CalculatorMode.values().forEach { mode ->
                        val isSelected = selectedMode == mode
                        val bgColor by animateColorAsState(
                            targetValue = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
                            label = "calc_tab_bg"
                        )
                        val textColor by animateColorAsState(
                            targetValue = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                            label = "calc_tab_text"
                        )

                        Row(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(8.dp))
                                .background(bgColor)
                                .clickable { selectedMode = mode }
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = mode.icon,
                                contentDescription = null,
                                tint = textColor,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = mode.title,
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = textColor,
                                maxLines = 1
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            when (selectedMode) {
                CalculatorMode.PROFIT_LOSS -> ProfitLossCalculator()
                CalculatorMode.COMPOUND -> CompoundDividendCalculator()
                CalculatorMode.POSITION_SIZE -> PositionSizeCalculator()
            }

            // Disclaimer Footer
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
                )
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "Calculations are for educational and planning purposes only. Brokerage commissions, slippage, and taxation are estimates.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun ProfitLossCalculator() {
    var buyPriceText by remember { mutableStateOf("150.00") }
    var sellPriceText by remember { mutableStateOf("175.50") }
    var sharesText by remember { mutableStateOf("100") }
    var feeText by remember { mutableStateOf("0.00") }

    val buyPrice = buyPriceText.toDoubleOrNull() ?: 0.0
    val sellPrice = sellPriceText.toDoubleOrNull() ?: 0.0
    val shares = sharesText.toDoubleOrNull() ?: 0.0
    val fee = feeText.toDoubleOrNull() ?: 0.0

    val initialCost = buyPrice * shares
    val totalRevenue = sellPrice * shares
    val netProfit = (totalRevenue - initialCost) - fee
    val returnPercent = if (initialCost > 0) (netProfit / initialCost) * 100 else 0.0
    val breakEven = if (shares > 0) (initialCost + fee) / shares else 0.0
    val isProfit = netProfit >= 0

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        // Result Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .glassmorphic(
                    backgroundColor = if (isProfit) BullGreen.copy(alpha = 0.12f) else BearRed.copy(alpha = 0.12f),
                    borderColor = if (isProfit) BullGreen.copy(alpha = 0.35f) else BearRed.copy(alpha = 0.35f),
                    shape = RoundedCornerShape(18.dp),
                    borderWidth = 1.dp
                )
                .padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = if (isProfit) "NET PROFIT" else "NET LOSS",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = if (isProfit) BullGreen else BearRed
                )
                Row(
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = (if (netProfit >= 0) "+" else "") + String.format(Locale.US, "$%,.2f", netProfit),
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = if (isProfit) BullGreen else BearRed
                    )
                    Text(
                        text = "(${if (returnPercent >= 0) "+" else ""}${String.format(Locale.US, "%.2f", returnPercent)}%)",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = if (isProfit) BullGreen else BearRed,
                        modifier = Modifier.padding(bottom = 3.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    MetricMini(label = "Total Invested", value = String.format(Locale.US, "$%,.2f", initialCost))
                    MetricMini(label = "Exit Total", value = String.format(Locale.US, "$%,.2f", totalRevenue))
                    MetricMini(label = "Break-Even Price", value = String.format(Locale.US, "$%,.2f", breakEven))
                }
            }
        }

        // Inputs
        NumberInputField(
            value = buyPriceText,
            onValueChange = { buyPriceText = it },
            label = "Buy Price per Share ($)",
            prefix = "$"
        )
        NumberInputField(
            value = sellPriceText,
            onValueChange = { sellPriceText = it },
            label = "Sell Price per Share ($)",
            prefix = "$"
        )
        NumberInputField(
            value = sharesText,
            onValueChange = { sharesText = it },
            label = "Number of Shares",
            prefix = "#"
        )
        NumberInputField(
            value = feeText,
            onValueChange = { feeText = it },
            label = "Brokerage Fees / Commission ($)",
            prefix = "$"
        )
    }
}

@Composable
private fun CompoundDividendCalculator() {
    var initialCapitalText by remember { mutableStateOf("10000") }
    var monthlyContributionText by remember { mutableStateOf("500") }
    var annualReturnText by remember { mutableStateOf("9.5") }
    var dividendYieldText by remember { mutableStateOf("3.0") }
    var yearsText by remember { mutableStateOf("10") }

    val initial = initialCapitalText.toDoubleOrNull() ?: 0.0
    val monthly = monthlyContributionText.toDoubleOrNull() ?: 0.0
    val growthRate = (annualReturnText.toDoubleOrNull() ?: 0.0) / 100.0
    val dividendYield = (dividendYieldText.toDoubleOrNull() ?: 0.0) / 100.0
    val years = (yearsText.toIntOrNull() ?: 1).coerceIn(1, 40)

    val totalRate = growthRate + dividendYield
    val monthlyRate = totalRate / 12.0
    val totalMonths = years * 12

    // Future value formula: Initial * (1+r)^n + Monthly * (((1+r)^n - 1) / r)
    val fvInitial = initial * (1 + monthlyRate).pow(totalMonths.toDouble())
    val fvMonthly = if (monthlyRate > 0) {
        monthly * (((1 + monthlyRate).pow(totalMonths.toDouble()) - 1) / monthlyRate)
    } else {
        monthly * totalMonths
    }
    val totalPortfolio = fvInitial + fvMonthly
    val totalContributed = initial + (monthly * totalMonths)
    val totalGains = (totalPortfolio - totalContributed).coerceAtLeast(0.0)
    val estimatedAnnualDividend = totalPortfolio * dividendYield

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        // Result Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .glassmorphic(
                    backgroundColor = FinancialBlue.copy(alpha = 0.12f),
                    borderColor = FinancialBlue.copy(alpha = 0.35f),
                    shape = RoundedCornerShape(18.dp),
                    borderWidth = 1.dp
                )
                .padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "ESTIMATED FUTURE PORTFOLIO (${years} YEARS)",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = FinancialBlue
                )
                Text(
                    text = String.format(Locale.US, "$%,.0f", totalPortfolio),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    MetricMini(label = "Total Contributed", value = String.format(Locale.US, "$%,.0f", totalContributed))
                    MetricMini(label = "Compound Growth", value = String.format(Locale.US, "$%,.0f", totalGains))
                    MetricMini(label = "Annual Dividend", value = String.format(Locale.US, "$%,.0f/yr", estimatedAnnualDividend))
                }
            }
        }

        NumberInputField(
            value = initialCapitalText,
            onValueChange = { initialCapitalText = it },
            label = "Initial Principal Investment ($)",
            prefix = "$"
        )
        NumberInputField(
            value = monthlyContributionText,
            onValueChange = { monthlyContributionText = it },
            label = "Monthly Addition ($)",
            prefix = "$"
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(modifier = Modifier.weight(1f)) {
                NumberInputField(
                    value = annualReturnText,
                    onValueChange = { annualReturnText = it },
                    label = "Annual Growth (%)",
                    prefix = "%"
                )
            }
            Box(modifier = Modifier.weight(1f)) {
                NumberInputField(
                    value = dividendYieldText,
                    onValueChange = { dividendYieldText = it },
                    label = "Dividend Yield (%)",
                    prefix = "%"
                )
            }
        }
        NumberInputField(
            value = yearsText,
            onValueChange = { yearsText = it },
            label = "Time Horizon (Years)",
            prefix = "Yr"
        )
    }
}

@Composable
private fun PositionSizeCalculator() {
    var accountSizeText by remember { mutableStateOf("25000") }
    var riskPercentText by remember { mutableStateOf("1.5") }
    var entryPriceText by remember { mutableStateOf("185.00") }
    var stopLossText by remember { mutableStateOf("175.00") }
    var targetPriceText by remember { mutableStateOf("215.00") }

    val accountSize = accountSizeText.toDoubleOrNull() ?: 0.0
    val riskPercent = (riskPercentText.toDoubleOrNull() ?: 0.0) / 100.0
    val entryPrice = entryPriceText.toDoubleOrNull() ?: 0.0
    val stopLoss = stopLossText.toDoubleOrNull() ?: 0.0
    val targetPrice = targetPriceText.toDoubleOrNull() ?: 0.0

    val maxDollarRisk = accountSize * riskPercent
    val riskPerShare = (entryPrice - stopLoss).coerceAtLeast(0.01)
    val rewardPerShare = (targetPrice - entryPrice).coerceAtLeast(0.0)
    val recommendedShares = if (riskPerShare > 0) (maxDollarRisk / riskPerShare).toInt() else 0
    val totalCapitalRequired = recommendedShares * entryPrice
    val potentialProfit = recommendedShares * rewardPerShare
    val riskRewardRatio = if (riskPerShare > 0) rewardPerShare / riskPerShare else 0.0

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        // Result Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .glassmorphic(
                    backgroundColor = BullGreen.copy(alpha = 0.12f),
                    borderColor = BullGreen.copy(alpha = 0.35f),
                    shape = RoundedCornerShape(18.dp),
                    borderWidth = 1.dp
                )
                .padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "RECOMMENDED POSITION SIZE",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = BullGreen
                )
                Row(
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "$recommendedShares SHARES",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "(${String.format(Locale.US, "$%,.2f", totalCapitalRequired)})",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    MetricMini(label = "Max Dollar Risk", value = String.format(Locale.US, "$%,.2f", maxDollarRisk))
                    MetricMini(label = "Potential Gain", value = String.format(Locale.US, "$%,.2f", potentialProfit))
                    MetricMini(label = "Risk / Reward", value = "1 : ${String.format(Locale.US, "%.1f", riskRewardRatio)}")
                }
            }
        }

        NumberInputField(
            value = accountSizeText,
            onValueChange = { accountSizeText = it },
            label = "Total Portfolio Account Size ($)",
            prefix = "$"
        )
        NumberInputField(
            value = riskPercentText,
            onValueChange = { riskPercentText = it },
            label = "Max Risk Per Trade (Standard: 1-2%)",
            prefix = "%"
        )
        NumberInputField(
            value = entryPriceText,
            onValueChange = { entryPriceText = it },
            label = "Planned Entry Price ($)",
            prefix = "$"
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(modifier = Modifier.weight(1f)) {
                NumberInputField(
                    value = stopLossText,
                    onValueChange = { stopLossText = it },
                    label = "Stop Loss ($)",
                    prefix = "$"
                )
            }
            Box(modifier = Modifier.weight(1f)) {
                NumberInputField(
                    value = targetPriceText,
                    onValueChange = { targetPriceText = it },
                    label = "Target Profit ($)",
                    prefix = "$"
                )
            }
        }
    }
}

@Composable
private fun MetricMini(label: String, value: String) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun NumberInputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    prefix: String,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = { input ->
            // Allow numbers and one decimal point
            if (input.all { it.isDigit() || it == '.' }) {
                onValueChange(input)
            }
        },
        label = { Text(label) },
        leadingIcon = {
            Text(
                text = prefix,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(start = 12.dp, end = 4.dp)
            )
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface
        ),
        modifier = modifier.fillMaxWidth()
    )
}
