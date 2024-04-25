package com.example.chatgeminiapp.presentation.gemini_chat.chats.components.chats_actions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import com.example.chatgeminiapp.presentation._common.resources.Dimens.largePadding1
import com.example.chatgeminiapp.presentation._common.resources.Dimens.largePadding2
import com.example.chatgeminiapp.presentation._common.resources.Dimens.mediumPadding2
import com.example.chatgeminiapp.ui.theme.ChatGeminiAppTheme

@Composable
fun RenameChatsDialog(
    modifier: Modifier = Modifier,
    chatGroupId: Long,
    chatGroupTitle: String,

    onRenameChats: (Long, String) -> Unit,
    onDismiss: () -> Unit = {},
) {
    var newTitle by rememberSaveable { mutableStateOf(  chatGroupTitle) }

    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Dialog(
        onDismissRequest = { onDismiss() }
    ) {
        Card(
            modifier = modifier
                .padding(mediumPadding2),
            shape = RoundedCornerShape(mediumPadding2),
        ) {
            Column(
                modifier = modifier
                    .padding(mediumPadding2)
            ) {
                Text(
                    style = MaterialTheme.typography.headlineSmall,
                    text = "Rename current chat",
                    modifier = modifier
                )
                OutlinedTextField(
                    value = TextFieldValue(newTitle, selection = TextRange(newTitle.length)),
                    modifier = modifier
                        .focusRequester(focusRequester)
                        .padding(vertical = largePadding2),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary
                    ),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    singleLine = true,
                    keyboardActions = KeyboardActions(
                        onDone = {
                            if (newTitle.trimEnd() != chatGroupTitle) {
                                onRenameChats(chatGroupId, newTitle)
                            }
                            onDismiss()
                        }
                    ),
                    onValueChange = {
                        newTitle =  it.text
                    },
                )

                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        modifier = Modifier
                            .padding(vertical = mediumPadding2, horizontal = largePadding1)
                            .noRippleClickable {
                                newTitle = if (newTitle.isNotBlank()) {
                                    ""
                                } else {
                                    chatGroupTitle
                                }
                            },
                        style = MaterialTheme.typography.labelLarge.copy(color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = .25f)),
                        text = "Reset"
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(
                            colors = ButtonDefaults.textButtonColors(
                                contentColor =
                                MaterialTheme.colorScheme.primary
                            ),
                            modifier = Modifier,
                            onClick = { onDismiss() }) {
                            Text(
                                text = "Back"
                            )
                        }
                        TextButton(
                            modifier = Modifier,
                            colors = ButtonDefaults.textButtonColors(
                                contentColor =
                                MaterialTheme.colorScheme.primary
                            ),
                            onClick = {
                                      onRenameChats(chatGroupId, newTitle.trimEnd())
                            },
                            enabled = newTitle.trimEnd() != chatGroupTitle && newTitle.isNotBlank(),
                        ) {
                            Text(
                                text = "Save name"
                            )
                        }
                    }
                }
            }

        }
    }
}
@Composable
@Preview()
fun RenameChatsDialogPreview() {
    ChatGeminiAppTheme {
        RenameChatsDialog(
            chatGroupId = 0,
            chatGroupTitle = "old CHat",
            onRenameChats = { long, string -> })
    }
}


@Composable
private fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier = composed {
    this.clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}
