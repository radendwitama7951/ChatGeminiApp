package com.example.chatgeminiapp.presentation.gemini_chat.chats.components.chats_actions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.window.Dialog
import com.example.chatgeminiapp.presentation._common.resources.Dimens.largePadding1
import com.example.chatgeminiapp.presentation._common.resources.Dimens.mediumPadding2

@Composable
fun DeleteChatsDialog(
    modifier: Modifier = Modifier,
    chatGroupId: Long,

    localUriHandler: UriHandler = LocalUriHandler.current,

    onDelete: (Long) -> Unit = {},
    onDismiss: () -> Unit = {},
) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Card(
            modifier = Modifier
                .padding(mediumPadding2),
            shape = RoundedCornerShape(mediumPadding2),
        ) {
            Column (
                modifier = modifier
                    .padding(mediumPadding2)
            ) {
                Text(
                    style = MaterialTheme.typography.headlineSmall,
                    text = "Delete chat?",
                    modifier = modifier.padding(bottom = mediumPadding2)
                )

                Text(
                    style = MaterialTheme.typography.bodySmall,
                    text = "You'll no longer see this chat here. This will also delete related activity like prompts, responses, and feedback from your Gemini Apps Activity.\n" +
                            "\n",
                    modifier = modifier.padding()
                )

                Text(
                    style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.secondary, textDecoration = TextDecoration.Underline),
                    text = "Learn more",
                    modifier = modifier
                        .padding(bottom = largePadding1)
                        .clickable {
                            localUriHandler.openUri("https://support.google.com/gemini/answer/13666746")
                        }
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    ActionButton(
                        selectedChatGroupId = chatGroupId,
                        onDismiss = onDismiss,
                        onDelete = onDelete
                    )
                }
            }
        }
    }
}

@Composable
private fun ActionButton(
    selectedChatGroupId: Long,
    onDismiss: () -> Unit = {},
    onDelete: (Long) -> Unit = {}
) {
    TextButton(
        colors = ButtonDefaults.textButtonColors(
            contentColor =
            MaterialTheme.colorScheme.primary
        ),
        modifier = Modifier,
        onClick = onDismiss
    ) {
        Text(
            text = "Cancel"
        )
    }
    TextButton(
        modifier = Modifier,
        colors = ButtonDefaults.textButtonColors(
            contentColor =
            MaterialTheme.colorScheme.primary
        ),
        onClick = {
            onDelete(selectedChatGroupId)
            onDismiss()
        },
    ) {
        Text(
            text = "Delete"
        )
    }
}
