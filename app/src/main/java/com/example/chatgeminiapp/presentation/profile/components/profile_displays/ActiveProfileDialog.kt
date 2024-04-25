package com.example.chatgeminiapp.presentation.profile.components.profile_displays

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.Login
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Key
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.chatgeminiapp.R
import com.example.chatgeminiapp.presentation._common.resources.Dimens.largeCard1
import com.example.chatgeminiapp.presentation._common.resources.Dimens.mediumCard2
import com.example.chatgeminiapp.presentation._common.resources.Dimens.mediumPadding2
import com.example.chatgeminiapp.presentation._common.resources.Dimens.smallCard1
import com.example.chatgeminiapp.presentation._common.resources.Dimens.smallPadding1
import com.example.chatgeminiapp.presentation._common.resources.Dimens.smallPadding2
import com.example.chatgeminiapp.presentation.profile.ProfileEvent

@Composable
fun ActiveProfileDialog(
    modifier: Modifier = Modifier,
    event: (ProfileEvent) -> Unit,
    profileId: Long,
    profileName: String,
    apiKey: String,
    avatar: Bitmap? = null,

    dismissOnClickOutside: Boolean = true,
    onEdit: () -> Unit = {},
    onEditApiKey: () -> Unit = {},
    onDismiss: () -> Unit = {},
    onLogIn: (Long) -> Unit
) {
    var isShowDialog by remember {
        mutableStateOf(true)
    }
    if (isShowDialog) {
        Dialog(
            onDismissRequest = {
                isShowDialog = false
                onDismiss()
                event(ProfileEvent.ResetActiveProfile)
            },
            properties = DialogProperties(dismissOnClickOutside = dismissOnClickOutside)
        ) {
            Card(
                modifier = Modifier
                    .defaultMinSize(minHeight = mediumCard2, minWidth = largeCard1),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            ) {
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(mediumPadding2),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row (
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        IconButton(onClick = {
                            isShowDialog = false
                            onDismiss()
                        }) {
                            Icon(
                                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = .5f),
                                imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                                contentDescription = "Back Button to Profile List"
                            )
                        }
                        TextButton(
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                contentColor = MaterialTheme.colorScheme.secondary.copy(alpha = .5f)
                            ),
                            onClick = {
                                isShowDialog = false
                            }
                        ) {
                            Text(
                                style = MaterialTheme.typography.labelSmall,
                                text = "Delete profile"
                            )
                        }
                    }
                    if (avatar != null)
                        Image(
                            modifier = Modifier
                                .padding(smallPadding1)
                                .size(smallCard1)
                                .clip(CircleShape),
                            bitmap = avatar.asImageBitmap(),
                            contentDescription = "Active user avatar"
                        )
                    else
                        Image(
                            modifier = Modifier
                                .padding(smallPadding1)
                                .size(smallCard1)
                                .clip(CircleShape),
                            painter = painterResource(id = R.drawable.default_user_avatar),
                            contentDescription = "Active user avatar"
                        )

                    Text(text = "Hello, $profileName.", style = MaterialTheme.typography.titleLarge)
                    OutlinedButton(
                        onClick = {
                            isShowDialog = false
                            onEdit()
                        }
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(smallPadding1),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary,
                            text = "Manage Profile"
                        )
                    }
                    Row(
                        modifier = Modifier
                            .padding(smallPadding2)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(
                            smallPadding1,
                            alignment = Alignment.CenterHorizontally
                        )
                    ) {
                        OutlinedButton(
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp),
                                contentColor = MaterialTheme.colorScheme.onSurface,
                            ),
                            modifier = Modifier
                                .weight(1f),
                            shape = MirrorHalfEclipseButtonShape(),
                            onClick = {
                                isShowDialog = false
                                onEditApiKey()
                            }
                        ) {
                            Icon(
                                tint = MaterialTheme.colorScheme.primary,
                                imageVector = Icons.Outlined.Key,
                                contentDescription = "Api Key Icon"
                            )
                            Text(
                                modifier = Modifier
                                    .padding(smallPadding2),
                                style = MaterialTheme.typography.labelSmall,
                                text = "API Key"
                            )
                        }
                        OutlinedButton(
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp),
                                contentColor = MaterialTheme.colorScheme.onSurface,
                            ),
                            modifier = Modifier
                                .weight(1f),
                            shape = HalfEclipseButtonShape(),
                            onClick = {
                                event(ProfileEvent.ResetActiveProfile)
                                isShowDialog = false
                                onLogIn(profileId)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Outlined.Login,
                                contentDescription = "Log In Icon"
                            )
                            Text(
                                modifier = Modifier
                                    .padding(smallPadding2),
                                style = MaterialTheme.typography.labelSmall,
                                text = "Login"
                            )
                        }
                    }

                    Text(
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.onBackground.copy(
                                alpha = .5f
                            )
                        ),
                        text = "\n\nChat Gemini Android\nby Raden Dwitama Baliano"
                    )
                }
            }
        }

    }
}

private class MirrorHalfEclipseButtonShape(
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            reset()
            moveTo(size.width, 0f)
            lineTo(size.width, size.height)
            lineTo(size.width * .3f, size.height)
            arcTo(
                rect = Rect(
                    offset = Offset(0f, 0f),
                    size = Size(size.width * .3f, size.height)
                ),
                startAngleDegrees = 90f,
                sweepAngleDegrees = 180f,
                forceMoveTo = false
            )
            close()
        }
        return Outline.Generic(path)
    }

}

private class HalfEclipseButtonShape(
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            reset()
            lineTo(size.width * .7f, 0f)
            arcTo(
                rect = Rect(
                    offset = Offset(size.width * .7f, 0f),
                    size = Size(size.width * .3f, size.height)
                ),
                startAngleDegrees = -90f,
                sweepAngleDegrees = 180f,
                forceMoveTo = false
            )
            lineTo(0f, size.height)
            close()
        }
        return Outline.Generic(path)

    }
}
