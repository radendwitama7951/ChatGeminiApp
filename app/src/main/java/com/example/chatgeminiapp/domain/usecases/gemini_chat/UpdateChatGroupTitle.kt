package com.example.chatgeminiapp.domain.usecases.gemini_chat

import com.example.chatgeminiapp.domain.models.gemini_chat.InternalChatGroup
import com.example.chatgeminiapp.domain.repositories.GeminiChatRepository
import javax.inject.Inject

class UpdateChatGroupTitle @Inject constructor(
    private val geminiChatRepository: GeminiChatRepository
) {
    suspend operator fun invoke(chatGroupId: Long, newTitle: String): InternalChatGroup {
        val updatedChatGroup = geminiChatRepository.updateChatGroup(chatGroupId, newTitle)

        return geminiChatRepository.chatGroupToInternal(updatedChatGroup)
    }
}