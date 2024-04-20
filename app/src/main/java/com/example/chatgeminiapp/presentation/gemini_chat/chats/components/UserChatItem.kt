package com.example.chatgeminiapp.presentation.gemini_chat.chats.components

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatgeminiapp.R
import com.example.chatgeminiapp.presentation._common.resources.Dimens.mediumIconButton3
import com.example.chatgeminiapp.presentation._common.resources.Dimens.mediumPadding1
import com.example.chatgeminiapp.presentation._common.resources.Dimens.mediumPadding2
import com.example.chatgeminiapp.presentation._common.resources.Dimens.smallPadding2

@Composable
fun UserChatItem(prompt: String, bitmap: Bitmap?) {
    Column(
        modifier = Modifier.padding(vertical = smallPadding2, horizontal = mediumPadding2)
    ) {

        Spacer(modifier = Modifier.height(mediumPadding2)) // Spacing between avatar and message bubble
        Image(
            painter = painterResource(id = R.drawable.default_user_avatar), // Replace with your avatar image
            contentDescription = "User Avatar",
            modifier = Modifier
                .size(mediumIconButton3) // Adjust avatar size as needed
                .clip(CircleShape) // Clip for rounded avatar
        )

        Spacer(modifier = Modifier.height(mediumPadding1)) // Spacing between avatar and message bubble



        Text(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background),
            text = prompt,
            fontSize = 17.sp,

        )

        bitmap?.let {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
//                    .height(260.dp)
                    .padding(bottom = 2.dp)
                    .clip(RoundedCornerShape(mediumPadding1)),
                contentDescription = "image",
                contentScale = ContentScale.Crop,
                bitmap = it.asImageBitmap()
            )
        }

    }
}


