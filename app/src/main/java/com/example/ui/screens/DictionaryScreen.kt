package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.ui.theme.BullGreen
import com.example.ui.theme.FinancialBlue

enum class TermCategory(val label: String) {
    ALL("All Terms"),
    FUNDAMENTALS("Valuation & Ratios"),
    TECHNICAL("Technical Analysis"),
    MARKET_MECHANICS("Market Mechanics"),
    OPTIONS("Options & Derivatives"),
    MACRO("Macroeconomics")
}

data class GlossaryTerm(
    val term: String,
    val acronym: String? = null,
    val category: TermCategory,
    val shortDefinition: String,
    val formula: String? = null,
    val whyItMatters: String,
    val example: String
)

val STOCK_GLOSSARY: List<GlossaryTerm> = listOf(
    GlossaryTerm(
        term = "Price-to-Earnings Ratio",
        acronym = "P/E",
        category = TermCategory.FUNDAMENTALS,
        shortDefinition = "Measures a company's current share price relative to its per-share earnings. Evaluates how much investors pay per $1 of profit.",
        formula = "P/E = Market Price per Share ÷ Earnings Per Share (EPS)",
        whyItMatters = "A high P/E implies expectations of high future growth (or an overvalued stock), whereas a low P/E may indicate value or trouble.",
        example = "If Apple trades at \$200 and earns \$6.50/share, its P/E is 30.7x."
    ),
    GlossaryTerm(
        term = "Price/Earnings-to-Growth",
        acronym = "PEG",
        category = TermCategory.FUNDAMENTALS,
        shortDefinition = "A valuation metric that refines the P/E ratio by factoring in the company's expected earnings growth rate.",
        formula = "PEG = (P/E Ratio) ÷ Annual EPS Growth Rate (%)",
        whyItMatters = "Peter Lynch popularized PEG. A PEG of 1.0 is considered fairly valued; under 1.0 is often an undervalued bargain.",
        example = "A stock with 30 P/E growing earnings at 30% per year has a PEG of 1.0."
    ),
    GlossaryTerm(
        term = "Market Capitalization",
        acronym = "Market Cap",
        category = TermCategory.FUNDAMENTALS,
        shortDefinition = "The total dollar market value of a company's outstanding shares of stock.",
        formula = "Market Cap = Share Price × Total Outstanding Shares",
        whyItMatters = "Categorizes companies into Mega-Cap (\$200B+), Large-Cap (\$10B-\$200B), Mid-Cap (\$2B-\$10B), and Small-Cap (\$300M-\$2B).",
        example = "Microsoft with 7.43B shares at \$415 = \$3.08 Trillion market cap."
    ),
    GlossaryTerm(
        term = "Dividend Yield",
        acronym = "Div Yield",
        category = TermCategory.FUNDAMENTALS,
        shortDefinition = "The annual dividend payout expressed as a percentage of the stock's current share price.",
        formula = "Dividend Yield = (Annual Dividend per Share ÷ Share Price) × 100",
        whyItMatters = "Provides recurring passive income to investors regardless of daily share price fluctuations.",
        example = "Coca-Cola (KO) pays \$1.94 annually on a \$65 stock = 2.98% dividend yield."
    ),
    GlossaryTerm(
        term = "Beta (Volatility)",
        acronym = "β",
        category = TermCategory.FUNDAMENTALS,
        shortDefinition = "A measure of an individual stock's volatility and systematic risk in comparison to the broader market (S&P 500).",
        formula = "Beta = Covariance(Stock, Market) ÷ Variance(Market)",
        whyItMatters = "Beta = 1.0 moves with the S&P 500; Beta > 1.0 (e.g. Tesla at 2.4) is more volatile; Beta < 1.0 is defensive (e.g. Utilities at 0.5).",
        example = "If Beta is 1.5, when the S&P 500 rises 2%, the stock tends to rise 3%."
    ),
    GlossaryTerm(
        term = "Free Cash Flow",
        acronym = "FCF",
        category = TermCategory.FUNDAMENTALS,
        shortDefinition = "The cash a company generates after accounting for cash outflows to support operations and maintain capital assets (CapEx).",
        formula = "FCF = Operating Cash Flow − Capital Expenditures (CapEx)",
        whyItMatters = "Real cash on hand to fund dividends, share buybacks, acquisitions, and debt repayment. Harder to manipulate than net income.",
        example = "Alphabet produces over \$60B annually in FCF to reinvest in AI and cloud."
    ),
    GlossaryTerm(
        term = "EBITDA",
        acronym = "EBITDA",
        category = TermCategory.FUNDAMENTALS,
        shortDefinition = "Earnings Before Interest, Taxes, Depreciation, and Amortization. A proxy for operating profitability.",
        formula = "EBITDA = Net Income + Interest + Taxes + Depreciation + Amortization",
        whyItMatters = "Used by private equity and analysts to compare company profitability across different tax brackets and debt loads.",
        example = "High-growth software companies frequently quote EV/EBITDA multiples."
    ),
    GlossaryTerm(
        term = "Relative Strength Index",
        acronym = "RSI",
        category = TermCategory.TECHNICAL,
        shortDefinition = "A momentum oscillator that measures the speed and change of price movements on a scale from 0 to 100.",
        formula = "RSI = 100 − [100 ÷ (1 + Average Gain ÷ Average Loss)]",
        whyItMatters = "Readings above 70 indicate overbought conditions (potential pullback); readings below 30 indicate oversold conditions (potential rebound).",
        example = "An RSI of 82 on NVDA signals extreme short-term buyer exhaustion."
    ),
    GlossaryTerm(
        term = "Moving Average Convergence Divergence",
        acronym = "MACD",
        category = TermCategory.TECHNICAL,
        shortDefinition = "A trend-following momentum indicator that shows the relationship between two exponential moving averages (usually 12-day and 26-day EMA).",
        formula = "MACD Line = 12-period EMA − 26-period EMA. Signal Line = 9-period EMA of MACD.",
        whyItMatters = "Bullish crossover occurs when the MACD line crosses above the Signal line; bearish when it crosses below.",
        example = "Traders buy when the MACD histogram flips green above the zero baseline."
    ),
    GlossaryTerm(
        term = "Golden Cross & Death Cross",
        acronym = null,
        category = TermCategory.TECHNICAL,
        shortDefinition = "Chart patterns where the 50-day Simple Moving Average (SMA) crosses the long-term 200-day SMA.",
        formula = "Golden Cross: 50-day SMA crosses ABOVE 200-day SMA. Death Cross: 50-day crosses BELOW.",
        whyItMatters = "Golden Cross signals a major long-term bull market; Death Cross warns of severe bear market continuation.",
        example = "The S&P 500 Golden Cross in early 2023 preceded a historic 18-month bull run."
    ),
    GlossaryTerm(
        term = "Bollinger Bands",
        acronym = "BB",
        category = TermCategory.TECHNICAL,
        shortDefinition = "A volatility indicator consisting of a 20-day simple moving average and an upper/lower band placed 2 standard deviations away.",
        formula = "Upper Band = 20 SMA + (2 × σ), Lower Band = 20 SMA − (2 × σ)",
        whyItMatters = "When bands contract ('The Squeeze'), it indicates low volatility that precedes a powerful explosive breakout.",
        example = "When a stock touches the lower band, mean-reversion traders watch for a bounce."
    ),
    GlossaryTerm(
        term = "Volume-Weighted Average Price",
        acronym = "VWAP",
        category = TermCategory.TECHNICAL,
        shortDefinition = "The benchmark price a security has traded at throughout the day, based on both volume and price.",
        formula = "VWAP = Σ(Price × Volume) ÷ Σ(Total Volume)",
        whyItMatters = "Used by institutional algorithms and intraday traders to determine if they purchased at a discount or premium relative to the day's volume.",
        example = "Institutions accumulate shares when price is below VWAP to get optimal fills."
    ),
    GlossaryTerm(
        term = "Short Selling & Short Squeeze",
        acronym = null,
        category = TermCategory.MARKET_MECHANICS,
        shortDefinition = "Borrowing shares to sell them immediately with the expectation of repurchasing them at a lower price for profit.",
        formula = "Short Profit = (Initial Short Sale Price − Buyback Price) × Shares",
        whyItMatters = "A Short Squeeze happens when a heavily shorted stock rapidly rallies, forcing short sellers to panic-buy back shares, triggering explosive upside.",
        example = "GameStop (GME) famously experienced a historic short squeeze in Jan 2021."
    ),
    GlossaryTerm(
        term = "Bid-Ask Spread",
        acronym = null,
        category = TermCategory.MARKET_MECHANICS,
        shortDefinition = "The difference between the highest price a buyer is willing to pay (Bid) and the lowest price a seller will accept (Ask).",
        formula = "Spread = Ask Price − Bid Price",
        whyItMatters = "Tight spreads (\$0.01 on SPY) signify high liquidity. Wide spreads mean high slippage and lower volume.",
        example = "Bid \$100.00 / Ask \$100.02 = tight 2-cent spread."
    ),
    GlossaryTerm(
        term = "Call Option & Put Option",
        acronym = null,
        category = TermCategory.OPTIONS,
        shortDefinition = "A Call gives the buyer the right (not obligation) to buy 100 shares at a strike price. A Put gives the right to sell 100 shares.",
        formula = "Standard contract represents 100 shares of underlying stock.",
        whyItMatters = "Enables leverage for speculation and insurance (hedging) against downside portfolio losses.",
        example = "Buying a \$180 AAPL Call option profits if Apple surges well above \$180."
    ),
    GlossaryTerm(
        term = "Implied Volatility",
        acronym = "IV",
        category = TermCategory.OPTIONS,
        shortDefinition = "The market's forecast of a stock's potential price movement, derived from options contract pricing.",
        formula = "Calculated in reverse using the Black-Scholes options pricing model.",
        whyItMatters = "High IV makes options contracts expensive (common before earnings reports); after earnings, 'IV Crush' causes options value to plummet.",
        example = "NVDA options IV often spikes to 90%+ in the 48 hours leading up to earnings."
    ),
    GlossaryTerm(
        term = "Federal Funds Rate & FOMC",
        acronym = "Fed Rate",
        category = TermCategory.MACRO,
        shortDefinition = "The target interest rate set by the Federal Reserve's Federal Open Market Committee (FOMC) at which commercial banks borrow overnight.",
        formula = null,
        whyItMatters = "The baseline cost of money globally. Higher rates reduce inflation but pressure stock valuations; rate cuts stimulate growth and equity markets.",
        example = "When the Fed lowers rates by 50 bps, growth and tech stocks typically rally."
    ),
    GlossaryTerm(
        term = "Inverted Yield Curve",
        acronym = "10Y - 2Y",
        category = TermCategory.MACRO,
        shortDefinition = "An unusual economic situation where short-term US Treasury yields (2-year) are higher than long-term yields (10-year).",
        formula = "Spread = 10-Year Treasury Yield − 2-Year Treasury Yield",
        whyItMatters = "Historically, an inverted yield curve has preceded almost every US economic recession over the past 60 years.",
        example = "When 2Y yields 4.8% and 10Y yields 4.2%, the yield curve is inverted by -60 bps."
    ),
    GlossaryTerm(
        term = "Consumer Price Index",
        acronym = "CPI",
        category = TermCategory.MACRO,
        shortDefinition = "The primary benchmark measuring the average change over time in prices paid by urban consumers for a market basket of consumer goods and services.",
        formula = null,
        whyItMatters = "The definitive gauge of inflation. High CPI readings prompt the Fed to hike rates, affecting equity market liquidity.",
        example = "A CPI reading of 2.5% signals inflation is approaching the Fed's 2.0% target."
    )
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DictionaryScreen(
    onNavigateBack: () -> Unit,
    isDark: Boolean,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(TermCategory.ALL) }
    var expandedTermId by remember { mutableStateOf<String?>(null) }

    val filteredTerms = remember(searchQuery, selectedCategory) {
        STOCK_GLOSSARY.filter { item ->
            val matchesCategory = selectedCategory == TermCategory.ALL || item.category == selectedCategory
            val matchesSearch = searchQuery.isBlank() ||
                item.term.contains(searchQuery, ignoreCase = true) ||
                (item.acronym?.contains(searchQuery, ignoreCase = true) == true) ||
                item.shortDefinition.contains(searchQuery, ignoreCase = true) ||
                item.example.contains(searchQuery, ignoreCase = true)
            matchesCategory && matchesSearch
        }
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .testTag("dictionary_screen"),
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
                            modifier = Modifier.testTag("dictionary_back_button")
                        )
                        GlowingIconBadge(
                            icon = Icons.AutoMirrored.Filled.MenuBook,
                            contentDescription = null,
                            size = 38.dp,
                            iconSize = 20.dp,
                            tint = BullGreen,
                            containerColor = BullGreen
                        )
                        Column {
                            Text(
                                text = "Market Dictionary",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "${STOCK_GLOSSARY.size} Wall Street Definitions",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                    GlassStatusPill(
                        text = "${filteredTerms.size} FOUND",
                        accentColor = FinancialBlue
                    )
                }

                // Search Bar
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search terms (e.g. P/E, RSI, Beta, CPI)...") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { searchQuery = "" }) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "Clear search",
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.25f),
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 4.dp)
                )

                // Category Chips Filter
                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    TermCategory.values().forEach { cat ->
                        val isSelected = selectedCategory == cat
                        SuggestionChip(
                            onClick = { selectedCategory = cat },
                            label = {
                                Text(
                                    text = cat.label,
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            colors = SuggestionChipDefaults.suggestionChipColors(
                                containerColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                                labelColor = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                            ),
                            border = SuggestionChipDefaults.suggestionChipBorder(
                                enabled = true,
                                borderColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent
                            ),
                            shape = RoundedCornerShape(8.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 14.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            if (filteredTerms.isEmpty()) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 60.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "No matching financial terms found",
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "Try searching for P/E, RSI, MACD, EBITDA, or Beta",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                        )
                    }
                }
            } else {
                items(filteredTerms, key = { it.term }) { item ->
                    val isExpanded = expandedTermId == item.term
                    GlossaryTermCard(
                        term = item,
                        isExpanded = isExpanded,
                        onToggleExpand = {
                            expandedTermId = if (isExpanded) null else item.term
                        }
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
private fun GlossaryTermCard(
    term: GlossaryTerm,
    isExpanded: Boolean,
    onToggleExpand: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .clickable { onToggleExpand() }
            .animateContentSize(spring(dampingRatio = 0.8f)),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f)
        )
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = term.term,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    if (term.acronym != null) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = term.acronym,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }

                Icon(
                    imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = if (isExpanded) "Collapse" else "Expand",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = term.shortDefinition,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f),
                lineHeight = 18.sp
            )

            AnimatedVisibility(visible = isExpanded) {
                Column(
                    modifier = Modifier.padding(top = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (term.formula != null) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(MaterialTheme.colorScheme.surface)
                                .padding(10.dp)
                        ) {
                            Column {
                                Text(
                                    text = "FORMULA",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = FinancialBlue
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = term.formula,
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.7f))
                            .padding(10.dp)
                    ) {
                        Column {
                            Text(
                                text = "WHY IT MATTERS TO INVESTORS",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = BullGreen
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = term.whyItMatters,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.7f))
                            .padding(10.dp)
                    ) {
                        Column {
                            Text(
                                text = "REAL-WORLD EXAMPLE",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = term.example,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
        }
    }
}
