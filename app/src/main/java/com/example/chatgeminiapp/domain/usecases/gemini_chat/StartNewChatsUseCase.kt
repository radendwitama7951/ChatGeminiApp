package com.example.chatgeminiapp.domain.usecases.gemini_chat

import com.example.chatgeminiapp.domain.models.gemini_chat.ChatGroupWithChatItems
import com.example.chatgeminiapp.domain.repositories.GeminiChatRepository
import javax.inject.Inject

class StartNewChatsUseCase @Inject constructor (
    private val geminiChatRepository: GeminiChatRepository
) {
    suspend operator fun invoke(chatsTitle: String = "Gemini Chat"): ChatGroupWithChatItems {
        geminiChatRepository.initChat()
        val activeChatGroupId = geminiChatRepository.createChatGroup(chatsTitle)

        return geminiChatRepository.selectChatGroup(activeChatGroupId)
    }
}
