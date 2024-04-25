package com.example.chatgeminiapp.domain.usecases.gemini_chat

import com.example.chatgeminiapp.domain.models.gemini_chat.InternalChat
import com.example.chatgeminiapp.domain.repositories.GeminiChatRepository
import javax.inject.Inject

class GetIndependentResponse @Inject constructor (
    private val geminiChatRepository: GeminiChatRepository
) {
    suspend operator fun invoke(prompt: String): InternalChat {
        return geminiChatRepository.getResponse(prompt)
    }

}