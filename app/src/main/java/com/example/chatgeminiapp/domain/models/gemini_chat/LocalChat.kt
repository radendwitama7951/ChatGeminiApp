package com.example.chatgeminiapp.domain.models.gemini_chat

import android.graphics.Bitmap

data class LocalChat(
    val prompt: String,
    val bitmap: Bitmap?,
    val isFromUser: Boolean
)
