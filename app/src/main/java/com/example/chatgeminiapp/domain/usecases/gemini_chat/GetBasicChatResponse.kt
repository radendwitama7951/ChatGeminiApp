package com.example.chatgeminiapp.domain.usecases.gemini_chat

import com.example.chatgeminiapp.domain.models.gemini_chat.LocalChat
import com.example.chatgeminiapp.domain.repositories.GeminiChatRepository
import javax.inject.Inject

class GetBasicChatResponse @Inject constructor (
    private val geminiChatRepository: GeminiChatRepository
) {
    suspend operator fun invoke(prompt: String): LocalChat {
        return geminiChatRepository.getResponse(prompt)
    }

}