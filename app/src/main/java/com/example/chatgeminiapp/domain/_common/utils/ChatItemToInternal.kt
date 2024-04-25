package com.example.chatgeminiapp.domain._common.utils

import android.graphics.Bitmap
import com.example.chatgeminiapp._common.resources.Constants.USER_ROLE
import com.example.chatgeminiapp.domain.models.gemini_chat.ChatItem
import com.example.chatgeminiapp.domain.models.gemini_chat.InternalChat

fun chatItemToInternal(chatItem: ChatItem, bitmap: Bitmap? = null): InternalChat {
    return InternalChat(
        prompt = chatItem.prompt ?: "",
        bitmap = bitmap,
        bitmapUri = chatItem.bitmapSource,
        isFromUser = chatItem.role == USER_ROLE
    )
}