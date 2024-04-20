package com.example.chatgeminiapp.domain.usecases.gemini_chat

import com.example.chatgeminiapp.domain.repositories.GeminiChatRepository
import com.google.ai.client.generativeai.Chat
import javax.inject.Inject


class GetMultiTurnChat @Inject constructor(
    private val geminiChatRepository: GeminiChatRepository
) {
    operator fun invoke(): Chat {
        return geminiChatRepository.getChatHandler()
    }
}