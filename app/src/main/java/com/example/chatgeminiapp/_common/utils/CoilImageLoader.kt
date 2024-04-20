package com.example.chatgeminiapp._common.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import coil.Coil
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import coil.size.Size
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CoilImageLoader @Inject constructor() {
    suspend fun getBitmap(source: String, context: Context): Bitmap {
        val imageRequest = ImageRequest.Builder(context)
            .data(source)
            .size(Size.ORIGINAL)
            .build()

        val imageLoader = ImageLoader(getApplicationContext())

        val imageResult = (imageLoader.execute(imageRequest) as SuccessResult).drawable
        val imageBitmap = (imageResult as BitmapDrawable).bitmap
        return imageBitmap
    }

}

