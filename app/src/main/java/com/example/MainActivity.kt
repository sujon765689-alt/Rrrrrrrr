package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ToolType
import com.example.ui.components.AnimatedCinematicBackground
import com.example.ui.components.AppHeader
import com.example.ui.components.HeroSearchBar
import com.example.ui.components.MonetizationResultCard
import com.example.ui.components.PerViewEarningsCard
import com.example.ui.components.ToolDetailsSheet
import com.example.ui.components.ToolkitBentoGrid
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.TextMuted
import com.example.viewmodel.CreatorToolkitViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: CreatorToolkitViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MyApplicationTheme {
                MainToolkitScreen(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun MainToolkitScreen(viewModel: CreatorToolkitViewModel) {
    val selectedPlatform by viewModel.selectedPlatform.collectAsState()
    val inputUrl by viewModel.inputUrl.collectAsState()
    val selectedTool by viewModel.selectedTool.collectAsState()
    val isBengali by viewModel.isBengali.collectAsState()
    val isBgMotionEnabled by viewModel.isBgMotionEnabled.collectAsState()
    val currentViews by viewModel.currentViewsSlider.collectAsState()
    val selectedNiche by viewModel.selectedNiche.collectAsState()
    val customRpm by viewModel.customRpm.collectAsState()
    val selectedCurrency by viewModel.selectedCurrency.collectAsState()
    val isAnalyzing by viewModel.isAnalyzing.collectAsState()
    val analysisResult by viewModel.analysisResult.collectAsState()
    val activeDetailModal by viewModel.activeDetailModal.collectAsState()
    val snackMessage by viewModel.snackMessage.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(snackMessage) {
        snackMessage?.let { msg ->
            snackbarHostState.showSnackbar(msg)
            viewModel.clearSnack()
        }
    }

    AnimatedCinematicBackground(isMotionEnabled = isBgMotionEnabled) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Color.Transparent,
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            snackbarHost = {
                SnackbarHost(
                    hostState = snackbarHostState,
                    modifier = Modifier.navigationBarsPadding()
                )
            }
        ) { paddingValues ->
            Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .statusBarsPadding()
                        .testTag("main_toolkit_scroll"),
                    contentPadding = PaddingValues(bottom = 36.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // 1. App Header & Brand Row
                    item {
                        AppHeader(
                            selectedPlatform = selectedPlatform,
                            onSelectPlatform = { viewModel.selectPlatform(it) },
                            isBengali = isBengali,
                            onToggleLanguage = { viewModel.toggleLanguage() },
                            isBgMotionEnabled = isBgMotionEnabled,
                            onToggleBgMotion = { viewModel.toggleBgMotion() },
                            selectedCurrency = selectedCurrency,
                            onSelectCurrency = { viewModel.setCurrency(it) }
                        )
                    }

                    // 2. Search & Preset Bar
                    item {
                        HeroSearchBar(
                            inputUrl = inputUrl,
                            onUrlChange = { viewModel.setInputUrl(it) },
                            onAnalyze = { viewModel.executeAnalysis() },
                            isAnalyzing = isAnalyzing,
                            selectedPlatform = selectedPlatform,
                            isBengali = isBengali
                        )
                    }

                    // 3. Per-View Earnings Calculator ("কত ভিউতে কত টাকা দিলো" - 100% accurate)
                    item {
                        PerViewEarningsCard(
                            currentViews = currentViews,
                            onViewsChange = { viewModel.setViewsSlider(it) },
                            customRpm = customRpm,
                            onRpmChange = { viewModel.setCustomRpm(it) },
                            niches = viewModel.niches,
                            selectedNiche = selectedNiche,
                            onSelectNiche = { viewModel.selectNiche(it) },
                            revenueTiers = viewModel.revenueTiers,
                            formatCurrency = { viewModel.formatCurrency(it) },
                            isBengali = isBengali,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                    }

                    // 4. Monetization Status & Authenticity Card
                    item {
                        analysisResult?.let { result ->
                            MonetizationResultCard(
                                analysis = result,
                                isBengali = isBengali,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                        }
                    }

                    // 5. Bento Grid of Tools (Monetization, ID Finder, Tags, Data, Images, Shadowban)
                    item {
                        ToolkitBentoGrid(
                            selectedTool = selectedTool,
                            onSelectTool = { tool ->
                                viewModel.selectTool(tool)
                                if (tool != ToolType.MONETIZATION_CHECKER && tool != ToolType.EARNINGS_CALCULATOR) {
                                    viewModel.openDetailModal(tool)
                                }
                            },
                            isBengali = isBengali
                        )
                    }

                    // 6. Footer Info
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp, bottom = 24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Om Rakib isLam • 4K PRO Creator Toolkit",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextMuted
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = if (isBengali) "© ২০২৬ সর্বস্বত্ব সংরক্ষিত • ইউটিউব, ফেসবুক, ইনস্টাগ্রাম ও টিকটক টুলস"
                                else "© 2026 All Rights Reserved • Multi-Platform Analytics Engine",
                                fontSize = 10.sp,
                                color = TextMuted.copy(alpha = 0.7f)
                            )
                        }
                    }
                }
            }

            // Interactive Tool Details Bottom Sheet
            ToolDetailsSheet(
                activeTool = activeDetailModal,
                analysis = analysisResult,
                onDismiss = { viewModel.closeDetailModal() },
                onShowSnack = { viewModel.showSnack(it) },
                isBengali = isBengali
            )
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(text = "Om Rakib isLam: $name", modifier = modifier)
}
