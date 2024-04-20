package com.example.chatgeminiapp.domain.usecases.gemini_chat

import android.graphics.Bitmap
import com.example.chatgeminiapp.domain.models.gemini_chat.LocalChat
import com.example.chatgeminiapp.domain.repositories.GeminiChatRepository
import javax.inject.Inject

class GetChatResponseWithImage @Inject constructor(
    private val geminiChatRepository: GeminiChatRepository
) {
    suspend operator fun invoke(prompt: String, bitmap: Bitmap): LocalChat {
        return geminiChatRepository.getResponse(prompt, bitmap)
    }
}

