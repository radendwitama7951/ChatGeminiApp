package com.example.chatgeminiapp.presentation.gemini_chat.chats.components.chats_actions

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.chatgeminiapp.presentation._common.resources.Dimens
import com.example.chatgeminiapp.presentation._common.resources.Dimens.largeCard1
import com.example.chatgeminiapp.presentation._common.resources.Dimens.largePadding1
import com.example.chatgeminiapp.presentation._common.resources.Dimens.mediumCard2
import com.example.chatgeminiapp.ui.theme.ChatGeminiAppTheme


@Composable
fun ChatsProfileNameInput(
    modifier: Modifier = Modifier,
    initialValue: String = "",

    onDismiss: () -> Unit = {},
    onNext: (String) -> Unit
) {
    var profileName by remember { mutableStateOf(TextFieldValue(text = initialValue)) }
    var isValid by remember { mutableStateOf(true)  }

    Dialog(onDismissRequest = onDismiss, properties = DialogProperties(dismissOnClickOutside = false)) {
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
                    .padding(Dimens.mediumPadding2),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .padding(Dimens.smallPadding1),
                    text = "Gemini",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = "Login to Gemini Chat",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Create a Name For Your Session",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = .5f)
                )

                OutlinedTextField(
                    value = profileName,
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        errorContainerColor = Color.Transparent,
                        unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = .5f),
                        focusedTrailingIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = .5f),
                    ),
                    label = {
                        Text(
                            text = "Profile name"
                        )
                    },
                    isError = !isValid,
                    onValueChange = {
                        profileName = it
                        isValid = profileName.text.isNotBlank()
                    }
                )
                if (!isValid) {
                    Row(
                        modifier = Modifier
                            .padding(Dimens.smallPadding1)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier.size(Dimens.mediumPadding1),
                            tint = MaterialTheme.colorScheme.error,
                            imageVector = Icons.Filled.Warning,
                            contentDescription = "Validation error"
                        )
                        Text(
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.error,
                            text = " Profile name cannot be empty"
                        )

                    }
                }
                Spacer(modifier = Modifier.height(largePadding1))
                val userAgreementText =
                    buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = MaterialTheme.colorScheme.onSurface,
                            )
                        ) {
                            append("I have read ")
                        }
                        withStyle(
                            style = SpanStyle(
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            pushStringAnnotation(
                                tag = "Term and Condition",
                                annotation = "Term and Condition"
                            )
                            append("Terms and Condition ")
                        }
                        withStyle(
                            style = SpanStyle(
                                color = MaterialTheme.colorScheme.onSurface,
                            )
                        ) {
                            append("and ")
                        }
                        withStyle(
                            style = SpanStyle(
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            pushStringAnnotation(
                                tag = "Privacy Policy",
                                annotation = "Privacy Policy"
                            )
                            append("Privacy Policy")
                        }
                    }

                ClickableText(
                    modifier = Modifier.fillMaxWidth(),
                    text = userAgreementText,
                    onClick = { offset ->
                        userAgreementText.getStringAnnotations(offset, offset)
                            .firstOrNull()?.let { span ->
                                Log.d("test", "Clicked on ${span.item}")
                            }
                    })

                Row(
                    modifier = Modifier
                        .padding(vertical = largePadding1)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        modifier = Modifier
                            .padding(vertical = largePadding1/2)
                            .clickable{ onDismiss() },
                        color = MaterialTheme.colorScheme.primary,
                        text = "Cancel",
                    )
                    Button(
                        modifier = Modifier
                            .defaultMinSize(minHeight = largePadding1),
                        contentPadding = PaddingValues(5.dp),
                        shape = RoundedCornerShape(percent = 5),
                        enabled = profileName.text.isNotBlank(),
                        onClick = { onNext(profileName.text) }
                    ) {
                        Text(text = "Next")
                    }

                }
            }
        }
    }
}

@Composable
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
fun CreateProfileDialogPreview() {
    ChatGeminiAppTheme {
        Surface(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.surface)
                .fillMaxSize()
        ) {
            ChatsProfileNameInput(onNext = {})
        }
    }
}