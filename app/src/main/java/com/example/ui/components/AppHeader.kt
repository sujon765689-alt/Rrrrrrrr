package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.MotionPhotosOn
import androidx.compose.material.icons.filled.MotionPhotosPaused
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Platform
import com.example.ui.theme.AmberGold
import com.example.ui.theme.CyanNeon
import com.example.ui.theme.GlassHighlight
import com.example.ui.theme.ObsidianBorder
import com.example.ui.theme.ObsidianCard
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun AppHeader(
    selectedPlatform: Platform,
    onSelectPlatform: (Platform) -> Unit,
    isBengali: Boolean,
    onToggleLanguage: () -> Unit,
    isBgMotionEnabled: Boolean,
    onToggleBgMotion: () -> Unit,
    selectedCurrency: String,
    onSelectCurrency: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        // Top Brand Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Glowing Brand Icon Badge
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            Brush.linearGradient(
                                listOf(selectedPlatform.accentColor, CyanNeon)
                            )
                        )
                        .border(1.dp, GlassHighlight, RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = "Logo",
                        tint = Color.White,
                        modifier = Modifier.size(22.dp)
                    )
                }

                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Om Rakib isLam",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = TextPrimary,
                            letterSpacing = 0.5.sp
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        // 4K PRO Badge
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(AmberGold.copy(alpha = 0.2f))
                                .border(0.8.dp, AmberGold.copy(alpha = 0.6f), RoundedCornerShape(6.dp))
                                .padding(horizontal = 5.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "4K PRO",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Black,
                                color = AmberGold
                            )
                        }
                    }
                    Text(
                        text = if (isBengali) "আল্টিমেট ক্রিয়েটর টুলকিট • মাল্টি-প্ল্যাটফর্ম" else "Ultimate Multi-Platform Creator Toolkit",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = TextSecondary
                    )
                }
            }

            // Quick Control Buttons (EN/বাংলা, Motion Toggle, Currency)
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                // Currency Switcher Chip
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(ObsidianCard)
                        .border(1.dp, ObsidianBorder, RoundedCornerShape(8.dp))
                        .clickable {
                            val currencies = listOf("USD", "BDT", "EUR", "INR")
                            val next = currencies[(currencies.indexOf(selectedCurrency) + 1) % currencies.size]
                            onSelectCurrency(next)
                        }
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .testTag("currency_toggle")
                ) {
                    Text(
                        text = selectedCurrency,
                        color = CyanNeon,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Motion Toggle
                IconButton(
                    onClick = onToggleBgMotion,
                    modifier = Modifier
                        .size(34.dp)
                        .testTag("motion_toggle")
                ) {
                    Icon(
                        imageVector = if (isBgMotionEnabled) Icons.Default.MotionPhotosOn else Icons.Default.MotionPhotosPaused,
                        contentDescription = "Toggle Background Motion",
                        tint = if (isBgMotionEnabled) CyanNeon else TextMuted,
                        modifier = Modifier.size(18.dp)
                    )
                }

                // Language Toggle Button
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(ObsidianCard)
                        .border(1.dp, ObsidianBorder, RoundedCornerShape(8.dp))
                        .clickable { onToggleLanguage() }
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .testTag("language_toggle")
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(3.dp)) {
                        Icon(
                            imageVector = Icons.Default.Language,
                            contentDescription = "Language",
                            tint = TextSecondary,
                            modifier = Modifier.size(12.dp)
                        )
                        Text(
                            text = if (isBengali) "বাংলা" else "EN",
                            color = TextPrimary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Platform Selector Pills: YouTube, Facebook, Instagram, TikTok
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Platform.values().forEach { platform ->
                val isSelected = platform == selectedPlatform
                val bgBrush = if (isSelected) {
                    Brush.horizontalGradient(
                        listOf(platform.accentColor, platform.accentColor.copy(alpha = 0.75f))
                    )
                } else {
                    Brush.horizontalGradient(
                        listOf(ObsidianCard, ObsidianCard)
                    )
                }
                val borderColor by animateColorAsState(
                    targetValue = if (isSelected) platform.accentColor else ObsidianBorder,
                    animationSpec = tween(250),
                    label = "border_${platform.name}"
                )

                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(bgBrush)
                        .border(1.dp, borderColor, RoundedCornerShape(12.dp))
                        .clickable { onSelectPlatform(platform) }
                        .padding(horizontal = 14.dp, vertical = 9.dp)
                        .testTag("platform_tab_${platform.name.lowercase()}"),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(7.dp)
                ) {
                    Icon(
                        imageVector = platform.icon,
                        contentDescription = platform.title,
                        tint = if (isSelected) Color.White else platform.accentColor,
                        modifier = Modifier.size(17.dp)
                    )
                    Text(
                        text = if (isBengali) platform.bengaliTitle else platform.title,
                        color = if (isSelected) Color.White else TextSecondary,
                        fontSize = 13.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                    )
                }
            }
        }
    }
}
