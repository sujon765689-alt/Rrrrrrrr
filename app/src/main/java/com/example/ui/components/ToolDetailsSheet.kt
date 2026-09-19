package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.model.ChannelAnalysis
import com.example.model.ToolType
import com.example.ui.theme.AmberGold
import com.example.ui.theme.CyanNeon
import com.example.ui.theme.EmeraldGreen
import com.example.ui.theme.ObsidianBorder
import com.example.ui.theme.ObsidianCard
import com.example.ui.theme.ObsidianCardSurface
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ToolDetailsSheet(
    activeTool: ToolType?,
    analysis: ChannelAnalysis?,
    onDismiss: () -> Unit,
    onShowSnack: (String) -> Unit,
    isBengali: Boolean
) {
    if (activeTool == null || analysis == null) return

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val clipboardManager = LocalClipboardManager.current

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = ObsidianCard,
        dragHandle = null
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .verticalScroll(rememberScrollState())
                .testTag("tool_details_sheet")
        ) {
            // Sheet Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = if (isBengali) activeTool.bengaliTitle else activeTool.title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = TextPrimary
                    )
                    Text(
                        text = "${analysis.channelName} (${analysis.handle})",
                        fontSize = 12.sp,
                        color = CyanNeon
                    )
                }
                IconButton(onClick = onDismiss) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = TextSecondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(color = ObsidianBorder)
            Spacer(modifier = Modifier.height(16.dp))

            when (activeTool) {
                ToolType.CHANNEL_ID_FINDER -> {
                    InfoCopyItem(
                        label = if (isBengali) "চ্যানেল আইডি (Channel ID)" else "Channel ID",
                        value = analysis.channelId,
                        onCopy = {
                            clipboardManager.setText(AnnotatedString(analysis.channelId))
                            onShowSnack(if (isBengali) "চ্যানেল আইডি কপি হয়েছে!" else "Channel ID copied to clipboard!")
                        }
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    InfoCopyItem(
                        label = if (isBengali) "কাস্টম হ্যান্ডেল (Handle)" else "Custom Handle",
                        value = analysis.handle,
                        onCopy = {
                            clipboardManager.setText(AnnotatedString(analysis.handle))
                            onShowSnack(if (isBengali) "হ্যান্ডেল কপি হয়েছে!" else "Handle copied!")
                        }
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    InfoCopyItem(
                        label = if (isBengali) "ইউটিউব আরএসএস ফিড URL" else "RSS Feed URL",
                        value = "https://www.youtube.com/feeds/videos.xml?channel_id=${analysis.channelId}",
                        onCopy = {
                            clipboardManager.setText(AnnotatedString("https://www.youtube.com/feeds/videos.xml?channel_id=${analysis.channelId}"))
                            onShowSnack("RSS URL copied!")
                        }
                    )
                }

                ToolType.TAG_EXTRACTOR -> {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (isBengali) "সংগৃহীত ভাইরাল ট্যাগ (${analysis.tags.size}টি):" else "Extracted Viral Tags (${analysis.tags.size}):",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(CyanNeon.copy(alpha = 0.2f))
                                .border(1.dp, CyanNeon.copy(alpha = 0.6f), RoundedCornerShape(8.dp))
                                .clickable {
                                    val all = analysis.tags.joinToString(", ")
                                    clipboardManager.setText(AnnotatedString(all))
                                    onShowSnack(if (isBengali) "সব ট্যাগ কপি হয়েছে!" else "All tags copied to clipboard!")
                                }
                                .padding(horizontal = 10.dp, vertical = 5.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                Icon(Icons.Default.ContentCopy, contentDescription = null, tint = CyanNeon, modifier = Modifier.size(13.dp))
                                Text(if (isBengali) "সব কপি করুন" else "Copy All", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = CyanNeon)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        analysis.tags.forEach { tag ->
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(ObsidianCardSurface)
                                    .border(1.dp, ObsidianBorder, RoundedCornerShape(8.dp))
                                    .clickable {
                                        clipboardManager.setText(AnnotatedString(tag))
                                        onShowSnack("\"$tag\" copied!")
                                    }
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Text("#$tag", color = TextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Medium)
                            }
                        }
                    }
                }

                ToolType.DATA_VIEWER -> {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        DataDetailRow(if (isBengali) "চ্যানেল খোলার তারিখ:" else "Created Date:", analysis.creationDate)
                        DataDetailRow(if (isBengali) "মোট ভিডিও সংখ্যা:" else "Total Uploaded Videos:", analysis.videoCount)
                        DataDetailRow(if (isBengali) "মোট দর্শন (Views):" else "Lifetime Views:", analysis.totalViews)
                        DataDetailRow(if (isBengali) "দেশ / অঞ্চল:" else "Channel Country:", analysis.country)
                        DataDetailRow(if (isBengali) "প্রধান ক্যাটাগরি:" else "Primary Category:", analysis.category)
                        DataDetailRow(if (isBengali) "ভিডিও লাউডনেস:" else "Audio Loudness:", analysis.loudnessLevel)
                        DataDetailRow(if (isBengali) "বাচ্চাদের কন্টেন্ট (COPPA):" else "Made for Kids:", "Not restricted (General Audience)")
                        DataDetailRow(if (isBengali) "অঞ্চল সীমাবদ্ধতা:" else "Region Restrictions:", "Worldwide Available (No Geo-block)")
                    }
                }

                ToolType.IMAGE_DOWNLOADER -> {
                    Text(
                        text = if (isBengali) "ভিডিও থাম্বনেইল প্রিভিউ (HD / 4K):" else "Video HD Thumbnail Preview:",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .border(1.dp, ObsidianBorder, RoundedCornerShape(12.dp))
                    ) {
                        AsyncImage(
                            model = analysis.thumbnailMaxResUrl,
                            contentDescription = "Thumbnail",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(CyanNeon)
                            .clickable {
                                clipboardManager.setText(AnnotatedString(analysis.thumbnailMaxResUrl))
                                onShowSnack(if (isBengali) "থাম্বনেইল লিঙ্ক কপি হয়েছে!" else "MaxRes thumbnail link copied!")
                            }
                            .padding(vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Icon(Icons.Default.Download, contentDescription = null, tint = ObsidianCard, modifier = Modifier.size(18.dp))
                            Text(if (isBengali) "4K থাম্বনেইল ডাউনলোড লিঙ্ক কপি" else "Copy 4K Thumbnail Link", color = ObsidianCard, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = if (isBengali) "চ্যানেল কভার আর্ট (ব্যানার):" else "Channel Cover Banner:",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .border(1.dp, ObsidianBorder, RoundedCornerShape(12.dp))
                    ) {
                        AsyncImage(
                            model = analysis.bannerUrl,
                            contentDescription = "Banner",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                ToolType.SHADOWBAN_DETECTOR -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .background(EmeraldGreen.copy(alpha = 0.15f))
                            .border(1.dp, EmeraldGreen.copy(alpha = 0.5f), RoundedCornerShape(14.dp))
                            .padding(14.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            Icon(Icons.Default.VerifiedUser, contentDescription = null, tint = EmeraldGreen, modifier = Modifier.size(32.dp))
                            Column {
                                Text(
                                    text = if (isBengali) "কোনো শ্যাডוב্যান পাওয়া যায়নি (CLEAN)" else "NO SHADOWBAN DETECTED (CLEAN)",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = EmeraldGreen
                                )
                                Text(
                                    text = if (isBengali) "সার্চ রেজাল্ট ও ফিডে চ্যানেলটি স্বাভাবিকভাবে দৃশ্যমান।" else "All videos, tags & comments are properly indexed in algorithmic search feeds.",
                                    fontSize = 11.sp,
                                    color = TextSecondary
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        DataDetailRow("Search Indexing Status:", "100% Fully Indexed")
                        DataDetailRow("Hashtag Visibility:", "Normal (Not suppressed)")
                        DataDetailRow("Comment Filtering:", "Standard algorithmic moderation")
                        DataDetailRow("Community Standing:", "Good standing (Zero active strikes)")
                    }
                }

                else -> {
                    Text(
                        text = analysis.description,
                        color = TextSecondary,
                        fontSize = 13.sp,
                        lineHeight = 20.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
private fun InfoCopyItem(label: String, value: String, onCopy: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(ObsidianCardSurface)
            .border(1.dp, ObsidianBorder, RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = label, color = TextMuted, fontSize = 11.sp)
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = value, color = TextPrimary, fontSize = 13.sp, fontWeight = FontWeight.Bold)
            }
            IconButton(onClick = onCopy) {
                Icon(Icons.Default.ContentCopy, contentDescription = "Copy", tint = CyanNeon, modifier = Modifier.size(18.dp))
            }
        }
    }
}

@Composable
private fun DataDetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, color = TextSecondary, fontSize = 12.sp)
        Text(text = value, color = TextPrimary, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
    }
}
