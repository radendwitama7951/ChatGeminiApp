package com.example.chatgeminiapp.presentation.gemini_chat._common.utils

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.graphics.drawable.toBitmap
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
@Composable
fun getBitmap(source: String, context: Context = LocalContext.current): Bitmap? {

    val imageState: AsyncImagePainter.State = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context)
            .data(source)
            .size(Size.ORIGINAL)
            .build()
    ).state
    Log.d("test", "getBitmap imageState ${imageState}")
    if (imageState is AsyncImagePainter.State.Success) {
        Log.d("test", "getBitmap imageState ${imageState.result.drawable.toBitmap()}")
        return imageState.result.drawable.toBitmap()
    }

    return null
}
