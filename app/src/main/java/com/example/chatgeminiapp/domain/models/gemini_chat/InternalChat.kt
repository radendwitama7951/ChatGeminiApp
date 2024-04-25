package com.example.chatgeminiapp.domain.models.gemini_chat

import android.graphics.Bitmap

data class InternalChat(
    val prompt: String,
    val bitmap: Bitmap?,
    val bitmapUri: String?,
    val isFromUser: Boolean
)
