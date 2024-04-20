package com.example.chatgeminiapp.presentation.gemini_chat._common.components

import android.annotation.SuppressLint
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import com.example.chatgeminiapp.presentation._common.resources.Dimens
import com.example.chatgeminiapp.presentation._common.resources.Dimens.mediumPadding1
import com.example.chatgeminiapp.ui.theme.ChatGeminiAppTheme

private const val animDuration = 3000
@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun GeminiChatSkeletonShimmer (
    modifier: Modifier = Modifier
) {

    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(mediumPadding1)
    ) {
        BoxWithConstraints (
            modifier = modifier.fillMaxWidth(),
        )
        {
            Box(
                modifier = Modifier
                    .entranceWidthAnim(0)
                    .clip(RoundedCornerShape(percent = 25))
                    .height(mediumPadding1)
                    .geminiBackgroundAnim(0)
            )
            Box(
                modifier = Modifier
                    .entranceWidthAnim(0)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(percent = 25))
                    .height(mediumPadding1)
                    .geminiBackgroundAnim(animDuration / 2)
            )
        }

        BoxWithConstraints (
            modifier = modifier.fillMaxWidth()
        )
        {
            Box(
                modifier = Modifier
                    .entranceWidthAnim(400)
                    .clip(RoundedCornerShape(percent = 25))
                    .height(mediumPadding1)
                    .geminiBackgroundAnim(animDuration / 100)
            )
            Box(
                modifier = Modifier
                    .entranceWidthAnim(400)
                    .clip(RoundedCornerShape(percent = 25))
                    .height(mediumPadding1)
                    .geminiBackgroundAnim(animDuration / 2 + animDuration / 100)
            )
        }

        BoxWithConstraints (
            modifier = modifier.fillMaxWidth()
            ,
        )
        {
            Box(
                modifier = modifier
                    .entranceWidthAnim(800, .66f)
                    .clip(RoundedCornerShape(percent = 25))
                    .height(Dimens.mediumPadding1)
                    .geminiBackgroundAnim(animDuration / 10)
            )
            Box(
                modifier = modifier
                    .entranceWidthAnim(800, .66f)
                    .clip(RoundedCornerShape(percent = 25))
                    .height(Dimens.mediumPadding1)
                    .geminiBackgroundAnim(animDuration / 2 + animDuration / 10)
            )
        }

    }

}
private fun Modifier.entranceWidthAnim (delayMillis: Int, targetValue: Float = 1f) = composed {
    val widthAnim = remember {
        Animatable(0f)
    }
    LaunchedEffect (Unit) {
        widthAnim.animateTo(
            targetValue = targetValue,
            animationSpec = tween(
                durationMillis = 2000,
                easing = LinearOutSlowInEasing,
                delayMillis = delayMillis
            )
        )
    }
    fillMaxWidth(widthAnim.value)
}
private fun Modifier.geminiBackgroundAnim(delayMillis: Int) = composed {
    val colors = listOf(
        MaterialTheme.colorScheme.background.copy(alpha = .1f),
        MaterialTheme.colorScheme.background.copy(alpha = .25f),
        MaterialTheme.colorScheme.primary.copy(alpha = .75f),
        MaterialTheme.colorScheme.primary.copy(alpha = 1f),
        MaterialTheme.colorScheme.primary.copy(alpha = .75f),
        MaterialTheme.colorScheme.background.copy(alpha = .25f),
        MaterialTheme.colorScheme.background.copy(alpha = .1f),
    )

    val transition = rememberInfiniteTransition(label = "shimmer")
    val translateAnim = transition.animateFloat(
        initialValue = -600f,
        targetValue = 2600f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = animDuration,
                easing = LinearEasing,
                delayMillis = delayMillis,
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmer"
    )

    val geminiLinearGradient = Brush.linearGradient(
        colors = colors,
        start = Offset.Zero,
        end = Offset(x = translateAnim.value, y = translateAnim.value)
    )

    background(geminiLinearGradient)
}
@Composable
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
private fun GeminiChatSkeletonShimmerPreview () {
    ChatGeminiAppTheme(
        dynamicColor = false,
    ) {
        Surface (
            modifier = Modifier.fillMaxSize(),
        ) {
            GeminiChatSkeletonShimmer()
        }
    }
}
