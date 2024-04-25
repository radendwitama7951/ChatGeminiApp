package com.example.chatgeminiapp.presentation.gemini_chat.chats

import android.graphics.Bitmap

sealed class ChatsEvent {
    data class ToggleLoading(val isLoading: Boolean?): ChatsEvent()
    data class ToggleRequest(val isRequest: Boolean?): ChatsEvent()
    data object StartNewChat: ChatsEvent()
    data object RefreshChatGroup: ChatsEvent()

    data class DeleteChatGroup (val id: Long): ChatsEvent()
    data class SwitchChatGroup (val id: Long): ChatsEvent()
    data class RenameChatGroup (val id: Long, val title: String): ChatsEvent()
    data class UpdatePrompt(val newPrompt: String): ChatsEvent()
    data class SendPrompt(val selectedGroup: Long?, val prompt: String, val bitmap: Bitmap?, val bitmapSource: String?): ChatsEvent()
    data object ResetBitmapUri: ChatsEvent()
    data class UpdateBitmapUri(val uri: String): ChatsEvent()
    data class UpdateBitmapCroppedUri(val bitmapCroppedUri: String?): ChatsEvent()
    data object ResetBitmap: ChatsEvent()
}