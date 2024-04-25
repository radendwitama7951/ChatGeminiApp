package com.example.chatgeminiapp.presentation.gemini_chat.chats.components.chats_layouts

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.chatgeminiapp.R
import com.example.chatgeminiapp.presentation._common.resources.Dimens
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.unit.Dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatTopBar(
    modifier: Modifier = Modifier,
    showActionsButton: Boolean = true,
    onDrawerToggle: () -> Unit = {},
    onProfileToggle: () -> Unit = {},
    onNewChatGroup: () -> Unit = {},
    onMoreAction: () -> Unit = {},
    isLoading: Boolean,

//    scope: CoroutineScope = rememberCoroutineScope()
) {
    val scrollBehavior =
        TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())// Adjust avatar size as needed
    val loadingBorderColor = listOf(
        MaterialTheme.colorScheme.background.copy(alpha = .1f),
        MaterialTheme.colorScheme.background.copy(alpha = .25f),
        MaterialTheme.colorScheme.primary.copy(alpha = .75f),
        MaterialTheme.colorScheme.primary.copy(alpha = 1f),
        MaterialTheme.colorScheme.primary.copy(alpha = .75f),
        MaterialTheme.colorScheme.background.copy(alpha = .25f),
        MaterialTheme.colorScheme.background.copy(alpha = .1f),
    )
// Replace with your avatar image
    /* do something */
    /* do something */
    TopAppBar(
        modifier = if (isLoading) {
            modifier
                .animatedBorder(
                    borderColors = loadingBorderColor,
                    backgroundColor = MaterialTheme.colorScheme.surface,
                    borderWidth = 2.dp
                )
        }
        else modifier,
        title = {
            Text(
                text = "Gemini",
                style = MaterialTheme.typography.titleLarge,
//                modifier = Modifier.padding(mediumPadding2)
            )
        },
        navigationIcon = {
            IconButton(onClick = onDrawerToggle) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Localized description"
                )
            }
        },
        actions = {
            if (showActionsButton) {
                IconButton(onClick = onMoreAction, enabled = !isLoading) {
                    Icon(
                        imageVector = Icons.Rounded.MoreVert,
                        contentDescription = "Localized description"
                    )
                }
                IconButton(onClick = onNewChatGroup, enabled = !isLoading) {
                    Icon(
                        imageVector = Icons.Rounded.Add,
                        contentDescription = "Localized description"
                    )
                }

            }
            IconButton(onClick = { /* do something */ }) {
                Image(
                    painter = painterResource(id = R.drawable.default_user_avatar), // Replace with your avatar image
                    contentDescription = "User Avatar",
                    modifier = Modifier
                        .size(Dimens.mediumIconButton3) // Adjust avatar size as needed
                        .clip(CircleShape) // Clip for rounded avatar
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.onBackground,
        ),
        scrollBehavior = scrollBehavior

        )
}

//@Composable
//private fun ActionMenus () {
//    DropdownMenu(expanded = , onDismissRequest = { /*TODO*/ }) {
//
//    }
//}


@Composable
private fun Modifier.animatedBorder(
    borderColors: List<Color>,
    backgroundColor: Color,
    shape: Shape =  RectangleShape,
    borderWidth: Dp = 1.dp,
    animationDurationInMillis: Int = 1000,
    easing: Easing = LinearEasing
): Modifier {
    val brush = Brush.sweepGradient(borderColors)
    val infiniteTransition = rememberInfiniteTransition(label = "animatedBorder")
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = animationDurationInMillis, easing = easing),
            repeatMode = RepeatMode.Restart
        ), label = "angleAnimation"
    )

    return this
        .clip(shape)
        .padding(bottom = borderWidth)
        .drawWithContent {
            rotate(-angle) {
                drawCircle(
                    brush = brush,
                    radius = size.width,
                    blendMode = BlendMode.SrcIn,
                )
            }
            drawContent()
        }
        .background(color = backgroundColor, shape = shape)
}
