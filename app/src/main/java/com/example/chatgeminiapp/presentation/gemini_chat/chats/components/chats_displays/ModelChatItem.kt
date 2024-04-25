package com.example.chatgeminiapp.presentation.gemini_chat.chats.components.chats_displays

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ContentCopy
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import com.example.chatgeminiapp.R
import com.example.chatgeminiapp.presentation._common.resources.Dimens.mediumIconButton2
import com.example.chatgeminiapp.presentation._common.resources.Dimens.mediumIconButton3
import com.example.chatgeminiapp.presentation._common.resources.Dimens.mediumPadding1
import com.example.chatgeminiapp.presentation._common.resources.Dimens.mediumPadding2
import com.example.chatgeminiapp.presentation._common.resources.Dimens.smallPadding2
import com.example.chatgeminiapp.presentation._common.resources.Dimens.smallPadding3
import com.example.chatgeminiapp.ui.theme.ChatGeminiAppTheme
import com.yazantarifi.compose.library.MarkdownConfig
import com.yazantarifi.compose.library.MarkdownViewComposable

@Composable
fun ModelChatItem(
    modifier: Modifier = Modifier.background(MaterialTheme.colorScheme.surface),
    response: String,
    localUriHandler: UriHandler = LocalUriHandler.current,
    clipboardManager: ClipboardManager  = LocalClipboardManager.current
) {
    Column(
        modifier = Modifier.padding(vertical = smallPadding2, horizontal = mediumPadding2)
    ) {

        Row {
            Image(
                painter = painterResource(id = R.drawable.google_gemini_icon), // Replace with your avatar image
                contentDescription = "User Avatar",
                modifier = Modifier
                    .size(mediumIconButton3) // Adjust avatar size as needed
                    .clip(CircleShape), // Clip for rounded avatar
            )

        }
        Spacer(modifier = Modifier.height(mediumPadding1)) // Spacing between avatar and message bubble


        Card(
            modifier = Modifier.padding(horizontal = smallPadding3),
            colors = CardDefaults.cardColors(
                MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(
                smallPadding2
            )
        ) {
            SelectionContainer {

                MarkdownViewComposable(
                    modifier = modifier
                        .padding(smallPadding2)
                        .fillMaxWidth(),
                    content = response,
                    config = MarkdownConfig(
                        isLinksClickable = true,
                        isImagesClickable = false,
                        isScrollEnabled = false,
                        colors = HashMap<String, Color>().apply {
                            this[MarkdownConfig.CHECKBOX_COLOR] = MaterialTheme.colorScheme.surface
                            this[MarkdownConfig.LINKS_COLOR] = MaterialTheme.colorScheme.secondary
                            this[MarkdownConfig.TEXT_COLOR] =
                                MaterialTheme.colorScheme.onSurface
                            this[MarkdownConfig.HASH_TEXT_COLOR] =
                                MaterialTheme.colorScheme.onSurface
                            this[MarkdownConfig.CODE_BACKGROUND_COLOR] =
                                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = .25f)
                            this[MarkdownConfig.CODE_BLOCK_TEXT_COLOR] =
                                MaterialTheme.colorScheme.onSurfaceVariant
                        }
                    ),
                    onLinkClickListener = { link, type ->
                        when (type) {
                            MarkdownConfig.LINK_TYPE -> {
                                localUriHandler.openUri(link)
                            }
                            MarkdownConfig.IMAGE_TYPE -> {}

                        }
                    }
                )
            }
            ResponseAction(
                modifier = modifier.padding(smallPadding2),
                onContentCopy = {
                    clipboardManager.setText(AnnotatedString((response)))
                }
            )
        }
        Spacer(modifier = Modifier.height(mediumPadding2)) // Spacing between avatar and message bubble
    }

}

@Composable
fun ResponseAction(
    modifier: Modifier = Modifier,
    onContentCopy: () -> Unit = {}
) {
    Row(
        modifier = modifier
    ) {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
            IconButton(
                modifier = Modifier
                    .size(mediumIconButton2),
                onClick = onContentCopy
            ) {
                Icon(
                    imageVector = Icons.Rounded.ContentCopy,
                    contentDescription = "Copy Response",
                    tint = MaterialTheme.colorScheme.onBackground.copy(alpha = .6f)
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun ModelChatItemPreview() {
    ChatGeminiAppTheme(dynamicColor = false) {
        Surface {
            ModelChatItem(response = "Hello world !")
        }
    }
}