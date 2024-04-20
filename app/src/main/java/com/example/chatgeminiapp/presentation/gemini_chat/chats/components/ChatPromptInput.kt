package com.example.chatgeminiapp.presentation.gemini_chat.chats.components

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.outlined.AddPhotoAlternate
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.graphics.drawable.toBitmap
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.chatgeminiapp.presentation._common.resources.Dimens.mediumIconButton3
import com.example.chatgeminiapp.presentation._common.resources.Dimens.mediumPadding1


@Composable
fun ChatPromptInput(
//    state: ChatsState,
//    event: (ChatsEvent) -> Unit,
    initialValue: String = "",
    onValueChange: (tmpPrompt: String) -> Unit = {},
    onSendPrompt: () -> Unit = {},
    onImageSelection: () -> Unit = {},
) {
    Row(
        modifier = Modifier
            .padding(bottom = mediumPadding1)
    ) {
        TextField(
            modifier = Modifier
                .clip(RoundedCornerShape(percent = 50))
                .background(MaterialTheme.colorScheme.surface)
                .weight(1f),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
            ),
            placeholder = {
                Text(text = "Type a prompt")
            },
            leadingIcon = {
                IconButton(onClick = onImageSelection) {
                    Icon(
                        modifier = Modifier
                            .size(mediumIconButton3),
                        imageVector = Icons.Outlined.AddPhotoAlternate,
                        contentDescription = "Add Photo",
                        tint = MaterialTheme.colorScheme.onSurface
                    )

                }
            },
            trailingIcon = {
                IconButton(onClick = {
                    onSendPrompt()
                }) {
                    Icon(
                        modifier = Modifier
                            .size(mediumIconButton3),
                        imageVector = Icons.Filled.Send,
                        contentDescription = "Send prompt",
                        tint = MaterialTheme.colorScheme.onSurface
                    )

                }

            },
            value = initialValue,
            onValueChange = {
                onValueChange(it)
            },
        )


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

    Log.d("test", "Image Painter failed ${uri} ")
    return null
}
