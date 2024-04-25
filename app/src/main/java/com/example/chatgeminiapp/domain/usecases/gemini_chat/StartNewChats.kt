package com.example.chatgeminiapp.domain.usecases.gemini_chat

import com.example.chatgeminiapp.domain._common.utils.chatGroupToInternal
import com.example.chatgeminiapp.domain.models.gemini_chat.InternalChatGroup
import com.example.chatgeminiapp.domain.repositories.GeminiChatRepository
import javax.inject.Inject

class StartNewChats @Inject constructor (
    private val geminiChatRepository: GeminiChatRepository
) {
    suspend operator fun invoke(chatsTitle: String? = null): InternalChatGroup  {
        geminiChatRepository.initChat()
        val activeChatGroupId = geminiChatRepository.createChatGroup(chatsTitle)
        val newChatGroup = geminiChatRepository.selectChatGroup(activeChatGroupId)

        return chatGroupToInternal(newChatGroup)

    }
}
