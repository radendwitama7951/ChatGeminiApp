package com.example.chatgeminiapp.domain.usecases.gemini_chat

import com.example.chatgeminiapp.domain.models.gemini_chat.ChatGroupWithChatItems
import com.example.chatgeminiapp.domain.repositories.GeminiChatRepository
import javax.inject.Inject

class SwitchChatsUseCase @Inject constructor(
    private val geminiChatRepository: GeminiChatRepository
) {
    suspend operator fun invoke(chatGroupId: Long): ChatGroupWithChatItems {
        val targetChatGroup = geminiChatRepository.selectChatGroup(chatGroupId)
        geminiChatRepository.initChat(targetChatGroup.chatItems)

        return targetChatGroup
    }

}