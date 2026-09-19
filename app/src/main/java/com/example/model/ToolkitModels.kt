package com.example.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.ui.theme.FacebookBlue
import com.example.ui.theme.InstagramPurple
import com.example.ui.theme.TikTokPink
import com.example.ui.theme.YouTubeRed

enum class Platform(
    val title: String,
    val bengaliTitle: String,
    val icon: ImageVector,
    val accentColor: Color,
    val placeholder: String,
    val defaultHandle: String
) {
    YOUTUBE(
        title = "YouTube",
        bengaliTitle = "ইউটিউব",
        icon = Icons.Default.PlayArrow,
        accentColor = YouTubeRed,
        placeholder = "Paste YouTube URL, Channel ID or @handle",
        defaultHandle = "@MrBeast"
    ),
    FACEBOOK(
        title = "Facebook",
        bengaliTitle = "ফেসবুক",
        icon = Icons.Default.ThumbUp,
        accentColor = FacebookBlue,
        placeholder = "Paste Facebook Page URL or Video Link",
        defaultHandle = "facebook.com/DailyDoseOfInternet"
    ),
    INSTAGRAM(
        title = "Instagram",
        bengaliTitle = "ইনস্টাগ্রাম",
        icon = Icons.Default.CameraAlt,
        accentColor = InstagramPurple,
        placeholder = "Paste Instagram @username or Reel URL",
        defaultHandle = "@cristiano"
    ),
    TIKTOK(
        title = "TikTok",
        bengaliTitle = "টিকটক",
        icon = Icons.Default.MusicNote,
        accentColor = TikTokPink,
        placeholder = "Paste TikTok @username or Video URL",
        defaultHandle = "@khaby.lame"
    )
}

enum class ToolType(
    val title: String,
    val bengaliTitle: String,
    val description: String,
    val iconName: String
) {
    MONETIZATION_CHECKER(
        title = "Monetization Checker",
        bengaliTitle = "মনিটাইজেশন চেকার",
        description = "Check if channel/video is monetized, ad placements, and authenticity.",
        iconName = "dollar"
    ),
    EARNINGS_CALCULATOR(
        title = "Per-View Calculator",
        bengaliTitle = "ভিউ প্রতি আয় ক্যালকুলেটর",
        description = "How much money for 1K, 10K, 100K, 1M+ views across platforms.",
        iconName = "calc"
    ),
    CHANNEL_ID_FINDER(
        title = "Channel ID Finder",
        bengaliTitle = "চ্যানেল আইডি ফাইন্ডার",
        description = "Find Channel ID, User ID, RSS Feed, and Custom Handle.",
        iconName = "id"
    ),
    TAG_EXTRACTOR(
        title = "Tag Extractor",
        bengaliTitle = "ট্যাগ এক্সট্রাক্টর",
        description = "Extract viral SEO tags, keyword density, and search volume.",
        iconName = "tag"
    ),
    DATA_VIEWER(
        title = "Data Viewer",
        bengaliTitle = "মেটাডেটা ভিউয়ার",
        description = "Exact upload timestamp, video velocity, loudness & audience stats.",
        iconName = "chart"
    ),
    IMAGE_DOWNLOADER(
        title = "HD Image & Thumbnail",
        bengaliTitle = "থাম্বনেইল ও ছবি ডাউনলোডার",
        description = "Download 4K Thumbnails, Channel Banners (17 sizes), & Profile Avatar.",
        iconName = "image"
    ),
    SHADOWBAN_DETECTOR(
        title = "Shadowban Detector",
        bengaliTitle = "শ্যাডוב্যান ডিটেক্টর",
        description = "Detect search suppresses, algorithm bans & community guideline strikes.",
        iconName = "shield"
    )
}

data class NicheRPM(
    val name: String,
    val bengaliName: String,
    val averageRpm: Double,
    val iconEmoji: String
)

data class PerViewRevenueTier(
    val views: Long,
    val label: String,
    val youtubeMin: Double,
    val youtubeMax: Double,
    val facebookMin: Double,
    val facebookMax: Double,
    val tiktokMin: Double,
    val tiktokMax: Double,
    val instagramMin: Double,
    val instagramMax: Double
)

data class ChannelAnalysis(
    val platform: Platform,
    val channelName: String,
    val handle: String,
    val channelId: String,
    val avatarUrl: String,
    val bannerUrl: String,
    val subscribers: String,
    val subscriberCountNum: Long,
    val totalViews: String,
    val totalViewsNum: Long,
    val videoCount: String,
    val creationDate: String,
    val country: String,
    val isMonetized: Boolean,
    val eligibilityStatus: String,
    val authenticityStatus: String,
    val authenticityScore: Int,
    val adStatus: String,
    val totalAdsFound: Int,
    val adBreakTimestamps: List<String>,
    val shadowbanStatus: String,
    val isShadowbanned: Boolean,
    val communityStrikes: String,
    val averageRpm: Double,
    val estimatedMonthly: String,
    val estimatedYearly: String,
    val tags: List<String>,
    val loudnessLevel: String,
    val thumbnailMaxResUrl: String,
    val description: String,
    val category: String
)
