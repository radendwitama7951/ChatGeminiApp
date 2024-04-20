package com.example.chatgeminiapp.presentation.gemini_chat.chats.components

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.chatgeminiapp.R
import com.example.chatgeminiapp.presentation._common.resources.Dimens

@Composable
fun ImagePreview(
    modifier: Modifier = Modifier,
    bitmap: Bitmap?
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
        if (bitmap != null) {
            Box(
                modifier = modifier
                    .clip(RoundedCornerShape(Dimens.mediumPadding2)),
            ) {
                Image(
                    modifier = modifier
                        .padding(Dimens.smallPadding2)
                        .clip(RoundedCornerShape(Dimens.mediumPadding1)),
                    bitmap = bitmap.asImageBitmap(),
//                            contentScale = ContentScale.Crop,
                    contentDescription = "Selected Image"
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