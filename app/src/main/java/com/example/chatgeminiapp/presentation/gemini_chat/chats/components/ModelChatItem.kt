package com.example.chatgeminiapp.presentation.gemini_chat.chats.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import com.example.chatgeminiapp.R
import com.example.chatgeminiapp.presentation._common.resources.Dimens.mediumIconButton3
import com.example.chatgeminiapp.presentation._common.resources.Dimens.mediumPadding1
import com.example.chatgeminiapp.presentation._common.resources.Dimens.mediumPadding2
import com.example.chatgeminiapp.presentation._common.resources.Dimens.smallPadding2
import dev.jeziellago.compose.markdowntext.MarkdownText

@Composable
fun ModelChatItem(response: String) {
    Column(
        modifier = Modifier.padding(vertical = smallPadding2, horizontal = mediumPadding2)
    ) {
        Image(
            painter = painterResource(id = R.drawable.google_gemini_icon), // Replace with your avatar image
            contentDescription = "User Avatar",
            modifier = Modifier
                .size(mediumIconButton3) // Adjust avatar size as needed
                .clip(CircleShape) // Clip for rounded avatar
        )

        Spacer(modifier = Modifier.height(mediumPadding1)) // Spacing between avatar and message bubble

        MarkdownText(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background),
            markdown = response,
            fontSize = 17.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
    Spacer(modifier = Modifier.height(mediumPadding2)) // Spacing between avatar and message bubble
}
