package com.example.chatgeminiapp.presentation.profile.components.profile_actions

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.test.platform.tracing.Tracer.Span
import com.example.chatgeminiapp.presentation._common.resources.Dimens.largeCard1
import com.example.chatgeminiapp.presentation._common.resources.Dimens.largePadding1
import com.example.chatgeminiapp.presentation._common.resources.Dimens.largePadding2
import com.example.chatgeminiapp.presentation._common.resources.Dimens.mediumCard2
import com.example.chatgeminiapp.presentation._common.resources.Dimens.mediumPadding1
import com.example.chatgeminiapp.presentation._common.resources.Dimens.mediumPadding2
import com.example.chatgeminiapp.presentation._common.resources.Dimens.smallPadding1
import com.example.chatgeminiapp.presentation._common.resources.Dimens.smallPadding2
import com.example.chatgeminiapp.presentation._common.resources.Dimens.smallPadding3
import com.example.chatgeminiapp.ui.theme.ChatGeminiAppTheme

@Composable
fun ProfileApiKeyInput(
    modifier: Modifier = Modifier,
    initialValue: String = "",

    onDismiss: () -> Unit = {},
    onBack: () -> Unit = {},
    onNext: (String) -> Unit = {}
) {
    var isShowDialog by remember {
        mutableStateOf(true)
    }
    var apiKey by remember { mutableStateOf(TextFieldValue(text = initialValue)) }
    var isValid by remember { mutableStateOf(true) }

    if (isShowDialog) {
        Dialog(
            onDismissRequest = {
                isShowDialog = false
                onDismiss()
            },
            properties = DialogProperties(dismissOnClickOutside = false)
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
                    Text(
                        modifier = Modifier
                            .padding(smallPadding1),
                        text = "Gemini",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = "Insert your personal API Key",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(largePadding2))
                    OutlinedTextField(
                        value = apiKey,
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            errorContainerColor = Color.Transparent,
                            unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = .5f),
                            focusedTrailingIconColor = MaterialTheme.colorScheme.onSurface.copy(
                                alpha = .5f
                            ),
                        ),
                        visualTransformation = PasswordVisualTransformation(),
                        label = {
                            Text(
                                text = "Insert API Key"
                            )
                        },
                        trailingIcon = {
                            if (apiKey.text.isNotBlank())
                                IconButton(
                                    onClick = {
                                        apiKey = TextFieldValue()
                                        isValid = false
                                    }) {
                                    Icon(
                                        imageVector = Icons.Outlined.Delete,
                                        contentDescription = "Delete API Key"
                                    )
                                }
                        },
                        isError = !isValid,
                        onValueChange = {
                            apiKey = it
                            isValid = apiKey.text.isNotBlank()
                        }
                    )

                    if (!isValid) {
                        Row(
                            modifier = Modifier
                                .padding(smallPadding1)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                modifier = Modifier.size(mediumPadding1),
                                tint = MaterialTheme.colorScheme.error,
                                imageVector = Icons.Filled.Warning,
                                contentDescription = "Validation error"
                            )
                            Text(
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.error,
                                text = " Insert a valid GeminiAI API Key"
                            )

                        }
                    }
                    Spacer(modifier = Modifier.height(largePadding1))
                    val getApiKeyText =
                        buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    color = MaterialTheme.colorScheme.onSurface,
                                )
                            ) {
                                append("Get your Gemini AI API Key for personal use ")
                            }
                            withStyle(
                                style = SpanStyle(
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.Bold
                                )
                            ) {
                                pushStringAnnotation(
                                    tag = "Gemini AI API Key",
                                    annotation = "Gemini AI API Key"
                                )
                                append("here")
                            }
                        }

                    ClickableText(
                        modifier = Modifier.fillMaxWidth(),
                        text = getApiKeyText,
                        onClick = { offset ->
                            getApiKeyText.getStringAnnotations(offset, offset)
                                .firstOrNull()?.let { span ->
                                    Log.d("test", "Clicked on ${span.item}")
                                }
                        })

                    Spacer(modifier = Modifier.height(largePadding1))
                    Row(
                        modifier = Modifier
                            .padding(vertical = largePadding1)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(vertical = largePadding1 / 2)
                                .clickable {
                                    isShowDialog = false
                                    onDismiss()
                                },
                            color = MaterialTheme.colorScheme.primary,
                            text = "Back",
                        )
                        Button(
                            modifier = Modifier
                                .defaultMinSize(minHeight = largePadding1),
                            contentPadding = PaddingValues(5.dp),
                            shape = RoundedCornerShape(percent = 5),
                            enabled = apiKey.text.isNotBlank(),
                            onClick = {
                                isShowDialog = false
                                onNext(apiKey.text)
                            }
                        ) {
                            Text(text = "Next")
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun ProfileApiKeyInputPreview() {
    ChatGeminiAppTheme(dynamicColor = false) {
        Surface {
            ProfileApiKeyInput()
        }
    }
}
