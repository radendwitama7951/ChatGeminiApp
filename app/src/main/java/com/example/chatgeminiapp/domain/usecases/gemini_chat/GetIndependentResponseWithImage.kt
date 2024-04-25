package com.example.chatgeminiapp.domain.usecases.gemini_chat

import android.graphics.Bitmap
import com.example.chatgeminiapp.domain.models.gemini_chat.InternalChat
import com.example.chatgeminiapp.domain.repositories.GeminiChatRepository
import javax.inject.Inject

class GetIndependentResponseWithImage @Inject constructor(
    private val geminiChatRepository: GeminiChatRepository
) {
    suspend operator fun invoke(prompt: String, bitmap: Bitmap): InternalChat {
        return geminiChatRepository.getResponse(prompt, bitmap)
    }
}

