package com.example.chatgeminiapp.presentation.gemini_chat.image_selector

sealed class ImageSelectorEvent {
    data class UpdateBitmapUri (val uri: String): ImageSelectorEvent()

}