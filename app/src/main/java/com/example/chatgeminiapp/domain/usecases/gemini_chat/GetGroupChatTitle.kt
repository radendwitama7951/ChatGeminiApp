package com.example.chatgeminiapp.domain.usecases.gemini_chat

import com.example.chatgeminiapp.domain.models.gemini_chat.InternalChatGroup
import com.example.chatgeminiapp.domain.repositories.GeminiChatRepository
import javax.inject.Inject

class GetGroupChatTitle @Inject constructor(
    private val geminiChatRepository: GeminiChatRepository
) {
    suspend operator fun invoke(id: Long, prompt: String): InternalChatGroup {
        return geminiChatRepository.generateChatGroupTitle(id, prompt)
    }

}