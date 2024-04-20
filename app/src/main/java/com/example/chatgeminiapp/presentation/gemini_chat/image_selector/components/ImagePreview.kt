package com.example.chatgeminiapp.presentation.gemini_chat.image_selector.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.chatgeminiapp.R

@Composable
fun ImagePreview(modifier: Modifier = Modifier, uri: String) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(uri)
            .crossfade(true)
            .build(),
        placeholder = painterResource(id = R.drawable.default_image_1),
        contentDescription = "Selected Image",
        contentScale = ContentScale.Crop,
        modifier = modifier.fillMaxWidth()
    )
}