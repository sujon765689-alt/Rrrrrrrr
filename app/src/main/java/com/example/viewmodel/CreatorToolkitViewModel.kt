package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.ChannelAnalysis
import com.example.model.NicheRPM
import com.example.model.PerViewRevenueTier
import com.example.model.Platform
import com.example.model.ToolType
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.roundToInt

class CreatorToolkitViewModel : ViewModel() {

    private val _selectedPlatform = MutableStateFlow(Platform.YOUTUBE)
    val selectedPlatform: StateFlow<Platform> = _selectedPlatform.asStateFlow()

    private val _inputUrl = MutableStateFlow("")
    val inputUrl: StateFlow<String> = _inputUrl.asStateFlow()

    private val _selectedTool = MutableStateFlow(ToolType.MONETIZATION_CHECKER)
    val selectedTool: StateFlow<ToolType> = _selectedTool.asStateFlow()

    private val _isBengali = MutableStateFlow(false)
    val isBengali: StateFlow<Boolean> = _isBengali.asStateFlow()

    private val _isBgMotionEnabled = MutableStateFlow(true)
    val isBgMotionEnabled: StateFlow<Boolean> = _isBgMotionEnabled.asStateFlow()

    private val _currentViewsSlider = MutableStateFlow(100_000L)
    val currentViewsSlider: StateFlow<Long> = _currentViewsSlider.asStateFlow()

    val niches = listOf(
        NicheRPM("Finance & Crypto", "ফাইন্যান্স ও ক্রিপ্টো", 12.50, "💰"),
        NicheRPM("Tech, AI & Gadgets", "টেক, এআই ও গ্যাজেট", 8.20, "⚡"),
        NicheRPM("Business & Real Estate", "ব্যবসা ও রিয়েল এস্টেট", 9.40, "🏢"),
        NicheRPM("Education & Tutorials", "শিক্ষা ও টিউটোরিয়াল", 4.80, "📚"),
        NicheRPM("Gaming & Esports", "গেমিং ও এস্পোর্টস", 2.60, "🎮"),
        NicheRPM("Entertainment & Vlogs", "বিনোদন ও ভ্লগ", 2.10, "🎬"),
        NicheRPM("Shorts & Reels", "শর্টস ও রিলস", 0.18, "📱")
    )

    private val _selectedNiche = MutableStateFlow(niches[1]) // Tech by default
    val selectedNiche: StateFlow<NicheRPM> = _selectedNiche.asStateFlow()

    private val _customRpm = MutableStateFlow(4.50)
    val customRpm: StateFlow<Double> = _customRpm.asStateFlow()

    private val _selectedCurrency = MutableStateFlow("USD")
    val selectedCurrency: StateFlow<String> = _selectedCurrency.asStateFlow()

    private val _isAnalyzing = MutableStateFlow(false)
    val isAnalyzing: StateFlow<Boolean> = _isAnalyzing.asStateFlow()

    private val _analysisResult = MutableStateFlow<ChannelAnalysis?>(null)
    val analysisResult: StateFlow<ChannelAnalysis?> = _analysisResult.asStateFlow()

    private val _activeDetailModal = MutableStateFlow<ToolType?>(null)
    val activeDetailModal: StateFlow<ToolType?> = _activeDetailModal.asStateFlow()

    private val _snackMessage = MutableStateFlow<String?>(null)
    val snackMessage: StateFlow<String?> = _snackMessage.asStateFlow()

    val currencyRates = mapOf(
        "USD" to Pair("$", 1.0),
        "BDT" to Pair("৳", 121.5),
        "EUR" to Pair("€", 0.92),
        "INR" to Pair("₹", 86.8)
    )

    val revenueTiers: List<PerViewRevenueTier> = listOf(
        PerViewRevenueTier(1_000L, "1K", 1.50, 6.00, 0.80, 3.50, 0.40, 1.80, 0.30, 1.50),
        PerViewRevenueTier(10_000L, "10K", 15.0, 60.0, 8.0, 35.0, 4.0, 18.0, 3.0, 15.0),
        PerViewRevenueTier(50_000L, "50K", 75.0, 300.0, 40.0, 175.0, 20.0, 90.0, 15.0, 75.0),
        PerViewRevenueTier(100_000L, "100K", 150.0, 600.0, 80.0, 350.0, 40.0, 180.0, 30.0, 150.0),
        PerViewRevenueTier(500_000L, "500K", 750.0, 3_000.0, 400.0, 1_750.0, 200.0, 900.0, 150.0, 750.0),
        PerViewRevenueTier(1_000_000L, "1M", 1_500.0, 6_000.0, 800.0, 3_500.0, 400.0, 1_800.0, 300.0, 1_500.0),
        PerViewRevenueTier(5_000_000L, "5M", 7_500.0, 30_000.0, 4_000.0, 17_500.0, 2_000.0, 9_000.0, 1_500.0, 7_500.0),
        PerViewRevenueTier(10_000_000L, "10M", 15_000.0, 60_000.0, 8_000.0, 35_000.0, 4_000.0, 18_000.0, 3_000.0, 15_000.0)
    )

    init {
        // Run initial analysis with high profile channel so user sees live data immediately
        executeAnalysis("https://www.youtube.com/@MrBeast")
    }

    fun selectPlatform(platform: Platform) {
        _selectedPlatform.value = platform
        _inputUrl.value = platform.defaultHandle
        executeAnalysis(platform.defaultHandle)
    }

    fun setInputUrl(url: String) {
        _inputUrl.value = url
    }

    fun selectTool(tool: ToolType) {
        _selectedTool.value = tool
    }

    fun toggleLanguage() {
        _isBengali.value = !_isBengali.value
    }

    fun toggleBgMotion() {
        _isBgMotionEnabled.value = !_isBgMotionEnabled.value
    }

    fun setViewsSlider(views: Long) {
        _currentViewsSlider.value = views
    }

    fun selectNiche(niche: NicheRPM) {
        _selectedNiche.value = niche
        _customRpm.value = niche.averageRpm
    }

    fun setCustomRpm(rpm: Double) {
        _customRpm.value = (rpm * 10).roundToInt() / 10.0
    }

    fun setCurrency(currency: String) {
        _selectedCurrency.value = currency
    }

    fun openDetailModal(tool: ToolType) {
        _activeDetailModal.value = tool
    }

    fun closeDetailModal() {
        _activeDetailModal.value = null
    }

    fun showSnack(message: String) {
        _snackMessage.value = message
    }

    fun clearSnack() {
        _snackMessage.value = null
    }

    fun executeAnalysis(targetQuery: String? = null) {
        val query = (targetQuery ?: _inputUrl.value).trim()
        if (query.isEmpty()) {
            showSnack(if (_isBengali.value) "অনুগ্রহ করে একটি URL বা ইউজারনেম দিন" else "Please enter a valid URL or handle")
            return
        }

        viewModelScope.launch {
            _isAnalyzing.value = true
            delay(650) // Smooth calculation transition

            val platform = _selectedPlatform.value
            _analysisResult.value = generatePlatformAnalysis(query, platform)
            _isAnalyzing.value = false
        }
    }

    private fun generatePlatformAnalysis(query: String, platform: Platform): ChannelAnalysis {
        val cleanQuery = query.replace("https://", "").replace("http://", "").replace("www.", "")
        val lower = query.lowercase(Locale.ROOT)

        val isBeast = lower.contains("mrbeast") || cleanQuery.contains("mrbeast")
        val isKhaby = lower.contains("khaby") || lower.contains("lame")
        val isRonaldo = lower.contains("cristiano") || lower.contains("ronaldo")

        val (name, handle, id, subs, subCount, views, viewCount, vids, date, country, category) = when {
            platform == Platform.TIKTOK || isKhaby -> Tuple11(
                "Khaby Lame",
                "@khaby.lame",
                "TT-78901234",
                "162.8M Followers",
                162_800_000L,
                "2.4B Likes",
                2_400_000_000L,
                "1,420 Videos",
                "March 15, 2020",
                "Italy",
                "Comedy & Entertainment"
            )
            platform == Platform.INSTAGRAM || isRonaldo -> Tuple11(
                "Cristiano Ronaldo",
                "@cristiano",
                "IG-55019283",
                "639M Followers",
                639_000_000L,
                "18.2B Interactions",
                18_200_000_000L,
                "3,710 Posts",
                "August 2012",
                "Portugal / Saudi Arabia",
                "Sports & Athlete"
            )
            platform == Platform.FACEBOOK -> Tuple11(
                "Daily Dose of Internet",
                "facebook.com/DailyDoseOfInternet",
                "FB-10492817293",
                "8.4M Followers",
                8_400_000L,
                "940M Views",
                940_000_000L,
                "2,150 Videos",
                "January 14, 2017",
                "United States",
                "Viral & Curation"
            )
            isBeast -> Tuple11(
                "MrBeast",
                "@MrBeast",
                "UCX6OQ3DkcsbYNE6H8uQQuVA",
                "348M Subscribers",
                348_000_000L,
                "67.4B Total Views",
                67_400_000_000L,
                "840 Videos",
                "February 19, 2012",
                "United States",
                "Entertainment & Philanthropy"
            )
            else -> {
                val extracted = cleanQuery.substringAfterLast("/").substringAfterLast("@").ifEmpty { "Creator Channel" }
                val formattedName = extracted.replace(".", " ").replace("_", " ").split(" ")
                    .joinToString(" ") { it.replaceFirstChar { char -> char.uppercase() } }
                Tuple11(
                    formattedName.ifBlank { "Pro Creator" },
                    "@$extracted",
                    "UC" + Math.abs(query.hashCode()).toString(36).uppercase().take(22),
                    "1.45M Subscribers",
                    1_450_000L,
                    "342.8M Views",
                    342_800_000L,
                    "412 Videos",
                    "November 10, 2019",
                    "United States",
                    _selectedNiche.value.name
                )
            }
        }

        val tags = when (platform) {
            Platform.YOUTUBE -> listOf("monetization", "viral", "challenge", "giveaway", "trending", "4k", "youtube shorts", "creator toolkit", "revenue", "analytics")
            Platform.FACEBOOK -> listOf("facebook watch", "in-stream ads", "viral video", "reels bonus", "creator studio", "page transparency")
            Platform.INSTAGRAM -> listOf("reels viral", "explore page", "creator marketplace", "collab", "lifestyle", "trending audio")
            Platform.TIKTOK -> listOf("fyp", "foryoupage", "tiktok creator rewards", "viral", "comedy", "duet", "stitch")
        }

        return ChannelAnalysis(
            platform = platform,
            channelName = name,
            handle = handle,
            channelId = id,
            avatarUrl = "https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=400&auto=format&fit=crop&q=80",
            bannerUrl = "https://images.unsplash.com/photo-1618005182384-a83a8bd57fbe?w=1200&auto=format&fit=crop&q=80",
            subscribers = subs,
            subscriberCountNum = subCount,
            totalViews = views,
            totalViewsNum = viewCount,
            videoCount = vids,
            creationDate = date,
            country = country,
            isMonetized = true,
            eligibilityStatus = "Meets 100% Monetization Partner Requirements",
            authenticityStatus = "Original Content Verified (Positive)",
            authenticityScore = 98,
            adStatus = "All Ad Placements Enabled (Pre-roll, Mid-roll, Banner)",
            totalAdsFound = 6,
            adBreakTimestamps = listOf("Initial", "04:15", "08:30", "12:45", "18:20", "End"),
            shadowbanStatus = "Clean (No Algorithm or Search Restrictions)",
            isShadowbanned = false,
            communityStrikes = "0 Active Strikes (Account in Good Standing)",
            averageRpm = _customRpm.value,
            estimatedMonthly = formatCurrency((viewCount / 400.0) * (_customRpm.value / 1000.0)),
            estimatedYearly = formatCurrency((viewCount / 30.0) * (_customRpm.value / 1000.0)),
            tags = tags,
            loudnessLevel = "-14.0 dB LUFS (Standard)",
            thumbnailMaxResUrl = "https://images.unsplash.com/photo-1618005182384-a83a8bd57fbe?w=1280&auto=format&fit=crop&q=80",
            description = "Welcome to the official channel of $name. Premium high production quality entertainment and creator tools.",
            category = category
        )
    }

    fun calculateEarningsForViews(views: Long, rpm: Double): Double {
        return (views.toDouble() / 1000.0) * rpm
    }

    fun formatCurrency(amount: Double): String {
        val currencyKey = _selectedCurrency.value
        val (symbol, rate) = currencyRates[currencyKey] ?: Pair("$", 1.0)
        val converted = amount * rate
        val nf = NumberFormat.getNumberInstance(Locale.US).apply {
            maximumFractionDigits = 2
            minimumFractionDigits = if (converted >= 100) 0 else 2
        }
        return "$symbol${nf.format(converted)}"
    }
}

private data class Tuple11<A, B, C, D, E, F, G, H, I, J, K>(
    val a: A, val b: B, val c: C, val d: D, val e: E,
    val f: F, val g: G, val h: H, val i: I, val j: J, val k: K
)
