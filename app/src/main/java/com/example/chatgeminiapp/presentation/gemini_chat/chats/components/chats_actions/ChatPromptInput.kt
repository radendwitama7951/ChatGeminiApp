package com.example.chatgeminiapp.presentation.gemini_chat.chats.components.chats_actions

import android.content.Context
import android.content.res.Configuration
import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.outlined.AddPhotoAlternate
import androidx.compose.material.icons.outlined.StopCircle
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Badge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.chatgeminiapp.R
import com.example.chatgeminiapp.presentation._common.resources.Dimens
import com.example.chatgeminiapp.presentation._common.resources.Dimens.mediumIconButton3
import com.example.chatgeminiapp.presentation._common.resources.Dimens.mediumPadding1
import com.example.chatgeminiapp.presentation._common.resources.Dimens.smallPadding1
import com.example.chatgeminiapp.presentation._common.resources.Dimens.smallPadding2
import com.example.chatgeminiapp.ui.theme.ChatGeminiAppTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatPromptInput(
    modifier: Modifier = Modifier,
    initialValue: String = "",
    selectedImage: String? = null,
    onValueChange: (tmpPrompt: String) -> Unit = {},
    onSendPrompt: (String) -> Unit = {},
    onImageSelection: () -> Unit = {},
    isSendAllowed: Boolean,

    context: Context = LocalContext.current
) {
    var prompt by rememberSaveable { mutableStateOf(initialValue) }
    Row(
        modifier = modifier
            .padding(bottom = smallPadding1, top = smallPadding1),
    ) {
        TextField(
            value = prompt,
            onValueChange = {
                prompt = it
            },
            modifier = modifier
                .clip(RoundedCornerShape(percent = 50))
                .background(MaterialTheme.colorScheme.surface)
                .weight(1f),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                unfocusedLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                unfocusedTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            ),
            placeholder = {
                Text(text = "Type a prompt")
            },
            maxLines = 3,
            leadingIcon = {
                if (selectedImage != null) {
                    Box(
                        modifier = modifier
                            .padding(smallPadding1)
                    ) {
//                        Image(
//                            modifier = modifier
//                                .padding(smallPadding2)
//                                .size(mediumIconButton3)
//                                .clickable { onImageSelection() }
//                                .clip(RoundedCornerShape(percent = 10)),
//                            bitmap = selectedImage.asImageBitmap(),
//                            contentScale = ContentScale.Crop,
//                            contentDescription = "Selected Image Preview"
//                        )
                        AsyncImage(
                            alignment = Alignment.Center,
                            modifier = Modifier
                                .padding(smallPadding2)
                                .size(mediumIconButton3)
                                .clickable { onImageSelection() }
                                .clip(RoundedCornerShape(percent = 10)),
                            model = ImageRequest.Builder(context)
                                .data(selectedImage)
                                .build(),
                            contentDescription = "Selected Image Preview",
                            error = painterResource(R.drawable.default_image_error_1),
                            contentScale = ContentScale.Crop,
                        )
                        Badge(
                            modifier = Modifier
                                .border(1.dp, color = Color.White, shape = CircleShape)
                                .clip(CircleShape)
                                .size(20.dp)
                                .align(Alignment.TopEnd),
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        ) {
                            Icon(
                                modifier = Modifier
                                    .size(mediumIconButton3),
                                imageVector = Icons.Rounded.Edit,
                                contentDescription = "Add Photo",
                            )
                        }
                    }
                } else {
                    IconButton(onClick = onImageSelection) {
                        Icon(
                            modifier = Modifier
                                .size(mediumIconButton3),
                            imageVector = Icons.Outlined.AddPhotoAlternate,
                            contentDescription = "Add Photo",
                        )

                    }
                }
            },
            trailingIcon = {
                IconButton(
                    enabled = isSendAllowed,
                    onClick = {
                        onSendPrompt(prompt)
                        prompt = ""
                    }
                ) {
                    Icon(
                        modifier = Modifier
                            .size(mediumIconButton3),
                        imageVector = if (isSendAllowed) Icons.AutoMirrored.Filled.Send else Icons.Outlined.StopCircle,
                        contentDescription = "Send prompt",
                    )

                }

            },
        )


    }
}

@Composable
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun ChatPromptInputPreview() {
    ChatGeminiAppTheme(dynamicColor = false) {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            ChatPromptInput(isSendAllowed = true)
        }

    }

}

@Composable
private fun getBitmapComposable(
    uri: String
): Bitmap? {

    val imageState: AsyncImagePainter.State = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(uri)
            .size(Size.ORIGINAL)
            .build()
    ).state

    if (imageState is AsyncImagePainter.State.Success) {
        Log.d("test", "Image Uri $uri")
        Log.d(
            "test",
            "Image Painter Successful ${imageState.result.drawable.toBitmap()} type ${imageState.result.drawable.toBitmap()::class.simpleName}"
        )
        return imageState.result.drawable.toBitmap()
    }

    return null
}
