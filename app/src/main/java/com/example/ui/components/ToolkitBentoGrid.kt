package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.CloudDownload
import androidx.compose.material.icons.filled.DataExploration
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Label
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ToolType
import com.example.ui.theme.AmberGold
import com.example.ui.theme.CrimsonNeon
import com.example.ui.theme.CyanNeon
import com.example.ui.theme.EmeraldGreen
import com.example.ui.theme.GlassHighlight
import com.example.ui.theme.ObsidianBorder
import com.example.ui.theme.ObsidianCard
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun ToolkitBentoGrid(
    selectedTool: ToolType,
    onSelectTool: (ToolType) -> Unit,
    isBengali: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (isBengali) "ক্রিয়েটর টুলকিট স্যুট (Toolkit Bento)" else "Creator Toolkit Suite",
                fontSize = 16.sp,
                fontWeight = FontWeight.ExtraBold,
                color = TextPrimary
            )
            Text(
                text = if (isBengali) "৭টি প্রো টুলস" else "7 Pro Tools",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = CyanNeon
            )
        }

        // Grid Cards
        ToolType.values().forEach { tool ->
            val isSelected = tool == selectedTool
            val (icon, accentColor) = when (tool) {
                ToolType.MONETIZATION_CHECKER -> Icons.Default.MonetizationOn to EmeraldGreen
                ToolType.EARNINGS_CALCULATOR -> Icons.Default.Calculate to AmberGold
                ToolType.CHANNEL_ID_FINDER -> Icons.Default.Fingerprint to CyanNeon
                ToolType.TAG_EXTRACTOR -> Icons.Default.Label to CrimsonNeon
                ToolType.DATA_VIEWER -> Icons.Default.DataExploration to Color(0xFF8B5CF6)
                ToolType.IMAGE_DOWNLOADER -> Icons.Default.CloudDownload to Color(0xFFEC4899)
                ToolType.SHADOWBAN_DETECTOR -> Icons.Default.Shield to Color(0xFF06B6D4)
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        if (isSelected) {
                            Brush.horizontalGradient(
                                listOf(ObsidianCard, accentColor.copy(alpha = 0.15f))
                            )
                        } else {
                            Brush.horizontalGradient(
                                listOf(ObsidianCard.copy(alpha = 0.9f), ObsidianCard.copy(alpha = 0.9f))
                            )
                        }
                    )
                    .border(
                        1.2.dp,
                        if (isSelected) accentColor else ObsidianBorder,
                        RoundedCornerShape(16.dp)
                    )
                    .clickable { onSelectTool(tool) }
                    .padding(14.dp)
                    .testTag("tool_card_${tool.name.lowercase()}")
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(accentColor.copy(alpha = 0.2f))
                                .border(1.dp, accentColor.copy(alpha = 0.4f), RoundedCornerShape(10.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = icon,
                                contentDescription = tool.title,
                                tint = accentColor,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        Column {
                            Text(
                                text = if (isBengali) tool.bengaliTitle else tool.title,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = tool.description,
                                fontSize = 11.sp,
                                color = TextSecondary,
                                maxLines = 2
                            )
                        }
                    }

                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = "Open",
                        tint = if (isSelected) accentColor else TextMuted,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}
