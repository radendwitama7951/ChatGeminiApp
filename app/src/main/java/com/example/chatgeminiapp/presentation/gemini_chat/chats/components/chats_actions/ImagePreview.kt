package com.example.chatgeminiapp.presentation.gemini_chat.chats.components.chats_actions

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.chatgeminiapp.R
import com.example.chatgeminiapp.presentation._common.resources.Dimens
import com.example.chatgeminiapp.presentation._common.resources.Dimens.smallPadding1

@Composable
fun ImagePreview(
    modifier: Modifier = Modifier,
    bitmapSourceUri: String?,

    context: Context = LocalContext.current
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = Dimens.smallPadding2)
    ) {
        Text(
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = .5f),
            text = stringResource(R.string.Preview),
            style = MaterialTheme.typography.labelLarge
        )
    }
    Row(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surface),
//                        .fillMaxHeight(.5f)

    ) {
        if (bitmapSourceUri != null) {
            Box(
                modifier = modifier
                    .padding(smallPadding1)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(Dimens.mediumPadding2)),
            ) {
//                Image(
//                    modifier = modifier
//                        .padding(Dimens.smallPadding2)
//                        .clip(RoundedCornerShape(Dimens.mediumPadding1)),
//                    bitmap = bitmapSourceUri.asImageBitmap(),
////                            contentScale = ContentScale.Crop,
//                    contentDescription = "Selected Image"
//                )
                AsyncImage(
                    alignment = Alignment.Center,
                    modifier = Modifier
                        .padding(Dimens.smallPadding2)
                        .clip(RoundedCornerShape(Dimens.mediumPadding1)),
                    model = ImageRequest.Builder(context)
                        .data(bitmapSourceUri)
                        .build(),
                    contentDescription = "Selected Image",
                    error = painterResource(R.drawable.default_image_error_1),
                    contentScale = ContentScale.Fit,
                )
            }
        } else {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(Dimens.smallPadding2)
                    .height(260.dp)
                    .clip(RoundedCornerShape(Dimens.mediumPadding2)),
            )
        }
    }
}