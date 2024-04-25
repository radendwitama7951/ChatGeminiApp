package com.example.chatgeminiapp.presentation.gemini_chat._common.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.chatgeminiapp.R
import com.example.chatgeminiapp.presentation._common.resources.Dimens
import com.example.chatgeminiapp.presentation._common.resources.Dimens.mediumPadding2
import com.example.chatgeminiapp.presentation._common.resources.Dimens.smallPadding2
import com.example.chatgeminiapp.ui.theme.ChatGeminiAppTheme






@Composable
fun AppChatShimmerEffect(
    modifier: Modifier = Modifier
) {


    Column(
        modifier = modifier.padding(vertical = smallPadding2, horizontal = mediumPadding2),
        verticalArrangement = Arrangement.spacedBy(mediumPadding2)
    ) {
        Image(
            painter = painterResource(id = R.drawable.google_gemini_icon), // Replace with your avatar image
            contentDescription = "Model Avatar",
            modifier = Modifier
                .rotateAnim(5000)
                .size(Dimens.mediumIconButton3) // Adjust avatar size as needed
                .clip(CircleShape) // Clip for rounded avatar
        )
        GeminiChatSkeletonShimmer()

    }
}


private fun Modifier.rotateAnim (durationMillis: Int) = composed {
    val infiniteTransition = rememberInfiniteTransition(label = "shimmer")
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis, easing = LinearEasing),
        ),
        label = "shimmer"
    )
    graphicsLayer{
        rotationZ = angle
    }
}
private fun Modifier.shimmerEffect(durationMillis: Int) = composed {
    val transition = rememberInfiniteTransition(label = "shimmer")
    val alpha = transition.animateFloat(
        initialValue = 0.2f, targetValue = .9f, animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = durationMillis, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "shimmer"
    ).value
    alpha(alpha)
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun AppChatShimmerEffectPreview(
) {
    ChatGeminiAppTheme {
        Surface (modifier = Modifier.fillMaxSize()) {
            AppChatShimmerEffect()
        }
    }
}

