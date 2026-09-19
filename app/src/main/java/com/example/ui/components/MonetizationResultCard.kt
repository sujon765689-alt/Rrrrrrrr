package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayCircleFilled
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
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
import coil.compose.AsyncImage
import com.example.model.ChannelAnalysis
import com.example.ui.theme.AmberGold
import com.example.ui.theme.CyanNeon
import com.example.ui.theme.EmeraldGreen
import com.example.ui.theme.GlassHighlight
import com.example.ui.theme.ObsidianBorder
import com.example.ui.theme.ObsidianCard
import com.example.ui.theme.ObsidianCardSurface
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.YouTubeRed

@Composable
fun MonetizationResultCard(
    analysis: ChannelAnalysis,
    isBengali: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(ObsidianCard.copy(alpha = 0.95f))
            .border(1.2.dp, ObsidianBorder, RoundedCornerShape(20.dp))
            .padding(18.dp)
            .testTag("monetization_result_card")
    ) {
        // Channel Profile Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Avatar with Glowing Ring
            Box(
                modifier = Modifier
                    .size(54.dp)
                    .clip(CircleShape)
                    .background(ObsidianCardSurface)
                    .border(2.dp, analysis.platform.accentColor, CircleShape)
            ) {
                AsyncImage(
                    model = analysis.avatarUrl,
                    contentDescription = analysis.channelName,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(CircleShape)
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = analysis.channelName,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Icon(
                        imageVector = Icons.Default.Verified,
                        contentDescription = "Verified",
                        tint = CyanNeon,
                        modifier = Modifier.size(16.dp)
                    )
                }
                Text(
                    text = "${analysis.handle} • ${analysis.platform.title}",
                    fontSize = 12.sp,
                    color = analysis.platform.accentColor,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "${analysis.subscribers} • ${analysis.totalViews}",
                    fontSize = 11.sp,
                    color = TextSecondary
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Hero Monetization Status Banner (Green Glow)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(
                    Brush.horizontalGradient(
                        listOf(EmeraldGreen.copy(alpha = 0.20f), ObsidianCardSurface)
                    )
                )
                .border(1.dp, EmeraldGreen.copy(alpha = 0.6f), RoundedCornerShape(14.dp))
                .padding(14.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Monetized",
                    tint = EmeraldGreen,
                    modifier = Modifier.size(28.dp)
                )
                Column {
                    Text(
                        text = if (isBengali) "মনিটাইজেশন চালু আছে (ON)" else "MONETIZATION IS ACTIVE (ON)",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = EmeraldGreen
                    )
                    Text(
                        text = if (isBengali) "এই চ্যানেলটি এবং এর ভিডিওগুলো থেকে নিয়মিত আয় হচ্ছে।"
                        else "This channel & videos are actively earning revenue from advertisements.",
                        fontSize = 11.sp,
                        color = TextSecondary
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Key Metrics Grid
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            MetricRow(
                label = if (isBengali) "কন্টেন্ট সত্যতা (Authenticity):" else "Authenticity Status:",
                value = analysis.authenticityStatus,
                valueColor = EmeraldGreen
            )
            MetricRow(
                label = if (isBengali) "বিজ্ঞাপন স্থিতি (Ad Status):" else "Ad Placements:",
                value = "${analysis.totalAdsFound} Ads Active (Pre-roll, Mid-rolls)",
                valueColor = CyanNeon
            )
            MetricRow(
                label = if (isBengali) "কমিউনিটি স্ট্রাইক (Strikes):" else "Community Strikes:",
                value = analysis.communityStrikes,
                valueColor = TextPrimary
            )
            MetricRow(
                label = if (isBengali) "শ্যাডוב্যান স্ট্যাটাস:" else "Shadowban Status:",
                value = analysis.shadowbanStatus,
                valueColor = if (analysis.isShadowbanned) YouTubeRed else EmeraldGreen
            )
            MetricRow(
                label = if (isBengali) "অডিও লাউডনেস লেভেল:" else "Loudness Level:",
                value = analysis.loudnessLevel,
                valueColor = TextSecondary
            )
        }

        Spacer(modifier = Modifier.height(14.dp))
        HorizontalDivider(color = ObsidianBorder)
        Spacer(modifier = Modifier.height(14.dp))

        // Ad Breaks Timeline
        Text(
            text = if (isBengali) "ভিডিওতে ব্যবহারকারীর অ্যাড ব্রেক (Ad Breaks Timestamps):" else "User-Added Ad Breaks:",
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextPrimary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            analysis.adBreakTimestamps.forEach { ts ->
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(ObsidianCardSurface)
                        .border(1.dp, CyanNeon.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "⏱ $ts",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = CyanNeon
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Partner Requirements Progress
        Text(
            text = if (isBengali) "মনিটাইজেশন শর্তাবলী অগ্রগতি (Eligibility Checklist):" else "Monetization Eligibility Checklist:",
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextPrimary
        )
        Spacer(modifier = Modifier.height(8.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(ObsidianCardSurface)
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            EligibilityItem(
                title = if (isBengali) "সাবস্ক্রাইবার শর্ত (১,০০০ সাবস্ক্রাইবার)" else "Subscribers (1,000 required)",
                progress = 1.0f,
                statusText = "100% Passed (${analysis.subscribers})"
            )
            EligibilityItem(
                title = if (isBengali) "ওয়াচটাইম শর্ত (৪,০০০ ঘণ্টা / ২৪০,০০০ মিনিট)" else "Public Watch Time (4,000 hrs required)",
                progress = 1.0f,
                statusText = "100% Passed"
            )
            EligibilityItem(
                title = if (isBengali) "কমিউনিটি গাইডলাইন নীতি মেনে চলা" else "Community Guidelines Compliance",
                progress = 1.0f,
                statusText = "100% Good Standing"
            )
        }
    }
}

@Composable
private fun MetricRow(label: String, value: String, valueColor: Color) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, fontSize = 12.sp, color = TextSecondary)
        Text(
            text = value,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = valueColor
        )
    }
}

@Composable
private fun EligibilityItem(
    title: String,
    progress: Float,
    statusText: String
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = title, fontSize = 11.sp, color = TextPrimary, fontWeight = FontWeight.Medium)
            Text(text = statusText, fontSize = 10.sp, color = EmeraldGreen, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(4.dp))
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .clip(CircleShape),
            color = EmeraldGreen,
            trackColor = ObsidianBorder
        )
    }
}
