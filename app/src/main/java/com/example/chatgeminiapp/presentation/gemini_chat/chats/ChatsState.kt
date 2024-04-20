package com.example.chatgeminiapp.presentation.gemini_chat.chats

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.example.chatgeminiapp.R
import com.example.chatgeminiapp.domain.models.gemini_chat.LocalChat

data class ChatsState(
    val chatList: List<LocalChat> = mutableListOf(),
    val prompt: String = "",
    val bitmap: Bitmap? = null,
    val bitmapUri: String = ""
)
