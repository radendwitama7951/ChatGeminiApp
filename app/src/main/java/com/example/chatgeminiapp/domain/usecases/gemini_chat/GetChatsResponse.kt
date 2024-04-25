package com.example.chatgeminiapp.domain.usecases.gemini_chat

import android.graphics.Bitmap
import com.example.chatgeminiapp.domain.models.gemini_chat.InternalChat
import com.example.chatgeminiapp.domain.repositories.GeminiChatRepository
import javax.inject.Inject

class GetChatsResponse @Inject constructor (
    private val geminiChatRepository: GeminiChatRepository
) {
    suspend operator fun invoke (chatGroupId: Long, prompt: String, bitmap: Bitmap? = null, bitmapSource: String? = null): InternalChat {
        val response = geminiChatRepository.getResponse(
            chatGroupId = chatGroupId,
            prompt = prompt,
            bitmap = bitmap,
            bitmapSource = bitmapSource
        )

        return response
    }

}

