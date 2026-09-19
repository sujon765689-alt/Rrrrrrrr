package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.ui.theme.CrimsonNeon
import com.example.ui.theme.CyanNeon
import com.example.ui.theme.ObsidianDeep
import kotlin.random.Random

private data class Particle(
    val initialX: Float,
    val initialY: Float,
    val radius: Float,
    val speed: Float,
    val color: Color,
    val baseAlpha: Float
)

@Composable
fun AnimatedCinematicBackground(
    modifier: Modifier = Modifier,
    isMotionEnabled: Boolean = true,
    content: @Composable () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "cinematicMotion")

    // Ken Burns scale animation
    val scaleAnim by if (isMotionEnabled) {
        infiniteTransition.animateFloat(
            initialValue = 1.02f,
            targetValue = 1.15f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 16000, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "kenBurnsScale"
        )
    } else {
        remember { androidx.compose.runtime.mutableFloatStateOf(1.04f) }
    }

    // Ken Burns pan translation
    val panX by if (isMotionEnabled) {
        infiniteTransition.animateFloat(
            initialValue = -18f,
            targetValue = 18f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 20000, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "kenBurnsPanX"
        )
    } else {
        remember { androidx.compose.runtime.mutableFloatStateOf(0f) }
    }

    val panY by if (isMotionEnabled) {
        infiniteTransition.animateFloat(
            initialValue = -12f,
            targetValue = 14f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 14000, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "kenBurnsPanY"
        )
    } else {
        remember { androidx.compose.runtime.mutableFloatStateOf(0f) }
    }

    // Particle progress
    val particleProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 8000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "particleProgress"
    )

    // Pre-calculated particle seeds
    val particles = remember {
        val colors = listOf(
            CyanNeon,
            CrimsonNeon,
            Color(0xFFFFD700),
            Color(0xFF8B5CF6)
        )
        List(30) {
            Particle(
                initialX = Random.nextFloat(),
                initialY = Random.nextFloat(),
                radius = Random.nextFloat() * 4f + 2f,
                speed = Random.nextFloat() * 0.4f + 0.6f,
                color = colors[Random.nextInt(colors.size)],
                baseAlpha = Random.nextFloat() * 0.45f + 0.25f
            )
        }
    }

    Box(modifier = modifier.fillMaxSize().background(ObsidianDeep)) {
        // 1. Photo Layer with Ken Burns movement
        Image(
            painter = painterResource(id = R.drawable.img_cinematic_bg),
            contentDescription = "Cinematic 4K Background",
            modifier = Modifier
                .fillMaxSize()
                .scale(scaleAnim)
                .offset(x = panX.dp, y = panY.dp),
            contentScale = ContentScale.Crop
        )

        // 2. Dark Obsidian Vignette & Lighting Gradient
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            ObsidianDeep.copy(alpha = 0.70f),
                            ObsidianDeep.copy(alpha = 0.85f),
                            ObsidianDeep.copy(alpha = 0.94f)
                        )
                    )
                )
        )

        // 3. Ambient Neon Mesh Glows
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            CrimsonNeon.copy(alpha = 0.14f),
                            Color.Transparent
                        ),
                        center = Offset(100f, 200f),
                        radius = 600f
                    )
                )
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            CyanNeon.copy(alpha = 0.12f),
                            Color.Transparent
                        ),
                        center = Offset(900f, 1200f),
                        radius = 800f
                    )
                )
        )

        // 4. Floating Glowing Particles Canvas
        if (isMotionEnabled) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val canvasW = size.width
                val canvasH = size.height

                particles.forEach { p ->
                    val y = (p.initialY * canvasH - particleProgress * p.speed * canvasH)
                    val wrappedY = if (y < 0) y + canvasH else y
                    val x = p.initialX * canvasW

                    drawCircle(
                        color = p.color.copy(alpha = p.baseAlpha),
                        radius = p.radius,
                        center = Offset(x, wrappedY)
                    )
                }
            }
        }

        // 5. Content Foreground
        content()
    }
}
