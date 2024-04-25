package com.example.chatgeminiapp.domain.usecases.gemini_chat

import com.example.chatgeminiapp.domain.repositories.GeminiChatRepository
import javax.inject.Inject

class CloseChatSession @Inject constructor(
    private val geminiChatRepository: GeminiChatRepository
) {
    operator fun invoke() {
        geminiChatRepository.terminateChat()
    }
}