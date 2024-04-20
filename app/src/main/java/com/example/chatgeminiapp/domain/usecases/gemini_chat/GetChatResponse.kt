package com.example.chatgeminiapp.domain.usecases.gemini_chat

import com.example.chatgeminiapp.domain.models.gemini_chat.ChatItem
import com.example.chatgeminiapp.domain.repositories.GeminiChatRepository
import javax.inject.Inject

class GetResponseUseCase @Inject constructor (
    private val geminiChatRepository: GeminiChatRepository
) {
    suspend fun invoke(prompt: String, bitmapSource: String? = null): ChatItem {
        val responseText = geminiChatRepository.getResponse(
            chatGroupId = geminiChatRepository.getActiveChatGroup(),
            prompt = prompt,
            bitmapSource = bitmapSource
        )


        return ChatItem(
            prompt = responseText,
            chatGroupOwner = geminiChatRepository.getActiveChatGroup()
        )
    }

}

