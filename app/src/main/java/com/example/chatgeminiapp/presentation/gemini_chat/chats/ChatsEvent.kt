package com.example.chatgeminiapp.presentation.gemini_chat.chats

import android.graphics.Bitmap

sealed class ChatsEvent {
    data class UpdatePrompt(val newPrompt: String): ChatsEvent()
    data class SendPrompt(val prompt: String, val bitmap: Bitmap?): ChatsEvent()
    data object ResetBitmapUri: ChatsEvent()
    data class UpdateBitmapUri(val uri: String): ChatsEvent()
    data class UpdateBitmap(val bitmap: Bitmap?): ChatsEvent()
    data object ResetBitmap: ChatsEvent()
}