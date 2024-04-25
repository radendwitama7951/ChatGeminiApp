package com.example.chatgeminiapp.domain.usecases.gemini_chat

import com.example.chatgeminiapp.domain.repositories.GeminiChatRepository
import javax.inject.Inject

class DeleteSelectedChatGroup @Inject constructor(
    private val geminiChatRepository: GeminiChatRepository
) {
    suspend operator fun invoke(groupId: Long): Long {
        val deletedGroupId = geminiChatRepository.deleteChatGroup(groupId)
        return deletedGroupId
    }
}