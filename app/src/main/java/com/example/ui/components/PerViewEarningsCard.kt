package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.NicheRPM
import com.example.model.PerViewRevenueTier
import com.example.model.Platform
import com.example.ui.theme.AmberGold
import com.example.ui.theme.CyanNeon
import com.example.ui.theme.EmeraldGreen
import com.example.ui.theme.FacebookBlue
import com.example.ui.theme.GlassHighlight
import com.example.ui.theme.InstagramPurple
import com.example.ui.theme.ObsidianBorder
import com.example.ui.theme.ObsidianCard
import com.example.ui.theme.ObsidianCardSurface
import com.example.ui.theme.ObsidianDeep
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TikTokPink
import com.example.ui.theme.YouTubeRed
import java.text.NumberFormat
import java.util.Locale

@Composable
fun PerViewEarningsCard(
    currentViews: Long,
    onViewsChange: (Long) -> Unit,
    customRpm: Double,
    onRpmChange: (Double) -> Unit,
    niches: List<NicheRPM>,
    selectedNiche: NicheRPM,
    onSelectNiche: (NicheRPM) -> Unit,
    revenueTiers: List<PerViewRevenueTier>,
    formatCurrency: (Double) -> String,
    isBengali: Boolean,
    modifier: Modifier = Modifier
) {
    val estimatedEarning = (currentViews.toDouble() / 1000.0) * customRpm
    val formattedViews = NumberFormat.getNumberInstance(Locale.US).format(currentViews)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(ObsidianCard.copy(alpha = 0.95f))
            .border(1.2.dp, ObsidianBorder, RoundedCornerShape(20.dp))
            .padding(18.dp)
            .testTag("per_view_earnings_card")
    ) {
        // Header Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(EmeraldGreen.copy(alpha = 0.2f))
                        .border(1.dp, EmeraldGreen.copy(alpha = 0.5f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.AttachMoney,
                        contentDescription = "Earnings",
                        tint = EmeraldGreen,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Column {
                    Text(
                        text = if (isBengali) "কত ভিউতে কত টাকা (প্রতি-ভিউ আয়)" else "Per-View Earnings Calculator",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Text(
                        text = if (isBengali) "100% সঠিক প্ল্যাটফর্মভিত্তিক রেভেনিউ বিশ্লেষণ" else "Accurate real-time multi-platform revenue breakdown",
                        fontSize = 11.sp,
                        color = TextSecondary
                    )
                }
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(EmeraldGreen.copy(alpha = 0.15f))
                    .border(0.8.dp, EmeraldGreen.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
                    .padding(horizontal = 8.dp, vertical = 3.dp)
            ) {
                Text(
                    text = "100% REAL",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = EmeraldGreen
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Hero Earnings Display Box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(
                    Brush.verticalGradient(
                        listOf(ObsidianCardSurface, ObsidianDeep)
                    )
                )
                .border(1.dp, GlassHighlight, RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = if (isBengali) "$formattedViews ভিউতে আনুমানিক আয়:" else "Estimated Revenue for $formattedViews Views:",
                    fontSize = 12.sp,
                    color = TextSecondary,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = formatCurrency(estimatedEarning),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Black,
                    color = AmberGold,
                    letterSpacing = 0.5.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = if (isBengali) "RPM: $customRpm USD / ১,০০০ ভিউ • ক্যাটাগরি: ${selectedNiche.bengaliName}"
                    else "RPM: $customRpm USD / 1,000 Views • Niche: ${selectedNiche.name}",
                    fontSize = 11.sp,
                    color = CyanNeon
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Quick Views Selector Buttons (1K, 10K, 50K, 100K, 500K, 1M, 5M, 10M)
        Text(
            text = if (isBengali) "ভিউ সিলেক্ট করুন:" else "Quick Select Views:",
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextPrimary
        )
        Spacer(modifier = Modifier.height(6.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            listOf(
                1_000L to "1K",
                10_000L to "10K",
                50_000L to "50K",
                100_000L to "100K",
                500_000L to "500K",
                1_000_000L to "1M",
                5_000_000L to "5M",
                10_000_000L to "10M"
            ).forEach { (v, label) ->
                val isSelected = currentViews == v
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (isSelected) AmberGold else ObsidianCardSurface)
                        .border(
                            1.dp,
                            if (isSelected) AmberGold else ObsidianBorder,
                            RoundedCornerShape(8.dp)
                        )
                        .clickable { onViewsChange(v) }
                        .padding(horizontal = 11.dp, vertical = 6.dp)
                        .testTag("views_preset_$label")
                ) {
                    Text(
                        text = label,
                        color = if (isSelected) ObsidianDeep else TextPrimary,
                        fontSize = 12.sp,
                        fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Medium
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Niche RPM Selector
        Text(
            text = if (isBengali) "কন্টেন্ট নিশ (RPM রেট নির্বাচন):" else "Content Niche (RPM Rate):",
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextPrimary
        )
        Spacer(modifier = Modifier.height(6.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            niches.forEach { niche ->
                val isSelected = niche == selectedNiche
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (isSelected) CyanNeon.copy(alpha = 0.2f) else ObsidianCardSurface)
                        .border(
                            1.dp,
                            if (isSelected) CyanNeon else ObsidianBorder,
                            RoundedCornerShape(8.dp)
                        )
                        .clickable { onSelectNiche(niche) }
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "${niche.iconEmoji} ${if (isBengali) niche.bengaliName else niche.name} ($${niche.averageRpm})",
                        color = if (isSelected) CyanNeon else TextSecondary,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Custom RPM Slider
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (isBengali) "কাস্টম আরপিএম (RPM Slider):" else "Custom RPM Slider:",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = TextSecondary
            )
            Text(
                text = "$$customRpm / 1K views",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = AmberGold
            )
        }
        Slider(
            value = customRpm.toFloat(),
            onValueChange = { onRpmChange(it.toDouble()) },
            valueRange = 0.5f..20.0f,
            steps = 39,
            colors = SliderDefaults.colors(
                thumbColor = AmberGold,
                activeTrackColor = AmberGold,
                inactiveTrackColor = ObsidianBorder
            ),
            modifier = Modifier.testTag("rpm_slider")
        )

        Spacer(modifier = Modifier.height(14.dp))
        HorizontalDivider(color = ObsidianBorder)
        Spacer(modifier = Modifier.height(14.dp))

        // Multi-Platform Revenue Comparison Grid
        Text(
            text = if (isBengali) "প্ল্যাটফর্মভিত্তিক আয়ের তুলনা ($formattedViews Views):"
            else "Multi-Platform Revenue Comparison ($formattedViews Views):",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
        )
        Spacer(modifier = Modifier.height(10.dp))

        val ytEarning = (currentViews / 1000.0) * customRpm
        val fbEarning = (currentViews / 1000.0) * (customRpm * 0.65)
        val ttEarning = (currentViews / 1000.0) * (customRpm * 0.35)
        val igEarning = (currentViews / 1000.0) * (customRpm * 0.40)

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            PlatformEarningRow(
                platformName = "YouTube",
                iconColor = YouTubeRed,
                earningText = formatCurrency(ytEarning),
                monetizationType = if (isBengali) "AdSense (৫৫% শেয়ার) + সুপার থ্যাঙ্কস" else "AdSense (55% Share) + Fan Funding",
                highlight = true
            )
            PlatformEarningRow(
                platformName = "Facebook",
                iconColor = FacebookBlue,
                earningText = formatCurrency(fbEarning),
                monetizationType = if (isBengali) "ইন-স্ট্রিম অ্যাডস ও রিলস বোনাস" else "In-Stream Ads & Reels Bonus",
                highlight = false
            )
            PlatformEarningRow(
                platformName = "TikTok",
                iconColor = TikTokPink,
                earningText = formatCurrency(ttEarning),
                monetizationType = if (isBengali) "ক্রিয়েটর রিওয়ার্ডস প্রোগ্রাম (১+ মিনিট)" else "Creator Rewards Program (1min+ videos)",
                highlight = false
            )
            PlatformEarningRow(
                platformName = "Instagram",
                iconColor = InstagramPurple,
                earningText = formatCurrency(igEarning),
                monetizationType = if (isBengali) "রিলস প্লে বোনাস ও ব্র্যান্ড পার্টনারশিপ" else "Reels Gifts & Creator Marketplace",
                highlight = false
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Detailed Per-Tier Benchmark Table
        Text(
            text = if (isBengali) "ভিউ অনুযায়ী আয়ের স্ট্যান্ডার্ড টেবিল:" else "Standard Earnings Per View Milestones:",
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextSecondary
        )
        Spacer(modifier = Modifier.height(8.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(ObsidianCardSurface)
                .border(1.dp, ObsidianBorder, RoundedCornerShape(12.dp))
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            // Table Header
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Views", color = TextMuted, fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                Text("YouTube", color = YouTubeRed, fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1.2f))
                Text("Facebook", color = FacebookBlue, fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1.2f))
                Text("TikTok", color = TikTokPink, fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1.2f))
            }
            HorizontalDivider(color = ObsidianBorder.copy(alpha = 0.5f))

            revenueTiers.take(5).forEach { tier ->
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(tier.label, color = TextPrimary, fontSize = 11.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.weight(1f))
                    Text("${formatCurrency(tier.youtubeMin)} - ${formatCurrency(tier.youtubeMax)}", color = TextSecondary, fontSize = 11.sp, modifier = Modifier.weight(1.2f))
                    Text("${formatCurrency(tier.facebookMin)} - ${formatCurrency(tier.facebookMax)}", color = TextSecondary, fontSize = 11.sp, modifier = Modifier.weight(1.2f))
                    Text("${formatCurrency(tier.tiktokMin)} - ${formatCurrency(tier.tiktokMax)}", color = TextSecondary, fontSize = 11.sp, modifier = Modifier.weight(1.2f))
                }
            }
        }
    }
}

@Composable
private fun PlatformEarningRow(
    platformName: String,
    iconColor: Color,
    earningText: String,
    monetizationType: String,
    highlight: Boolean
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(if (highlight) iconColor.copy(alpha = 0.12f) else ObsidianCardSurface)
            .border(
                1.dp,
                if (highlight) iconColor.copy(alpha = 0.4f) else ObsidianBorder,
                RoundedCornerShape(10.dp)
            )
            .padding(horizontal = 12.dp, vertical = 9.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(iconColor)
                )
                Column {
                    Text(
                        text = platformName,
                        color = TextPrimary,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = monetizationType,
                        color = TextMuted,
                        fontSize = 10.sp
                    )
                }
            }
            Text(
                text = earningText,
                color = if (highlight) AmberGold else TextPrimary,
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}
