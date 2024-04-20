package com.example.chatgeminiapp.data.remote.api

import android.graphics.Bitmap
import com.example.chatgeminiapp.data._interfaces.GeminiChatApi
import com.example.chatgeminiapp.domain.models.gemini_chat.LocalChat
import com.google.ai.client.generativeai.Chat
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.Content
import com.google.ai.client.generativeai.type.ResponseStoppedException
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GeminiChatApiImpl @Inject constructor(
    private val generativeModel: GenerativeModel,
    private val generativeVisionModel: GenerativeModel
): GeminiChatApi {
    override fun getChat(history: List<Content>): Chat {
        return generativeModel.startChat(
            history = history
        )
    }

    override fun getChatImage(history: List<Content>): Chat {
        return generativeVisionModel.startChat(
            history = history
        )
    }

    override suspend fun getResponse(content: Content): Content {
        val response = withContext(Dispatchers.IO) {
            if (content.parts.size > 1) {
                generativeVisionModel.generateContent(content)
            } else {
                generativeModel.generateContent(content)
            }
        }
        return content (role = "model") {
            text(text = response.text as String)
        }
    }

    override suspend fun getResponse (prompt: String): LocalChat{
        try {
            val response = withContext(Dispatchers.IO) {
                generativeModel.generateContent(prompt)
                }

            return LocalChat(
                prompt = response.text ?: "error",
                bitmap = null,
                isFromUser = false
            )

        } catch (e: ResponseStoppedException) {
            return LocalChat(
                prompt = e.message ?: "error",
                bitmap = null,
                isFromUser = false
            )

        }
    }

    override suspend fun getResponse (prompt: String, bitmap: Bitmap): LocalChat{
        try {
            val inputContent = content {
                image(bitmap)
                text(prompt)
            }
            val response = withContext(Dispatchers.IO) {
                generativeVisionModel.generateContent(inputContent)
            }

            return LocalChat(
                prompt = response.text ?: "error",
                bitmap = null,
                isFromUser = false
            )

        } catch (e: ResponseStoppedException) {
            return LocalChat(
                prompt = e.message ?: "error",
                bitmap = null,
                isFromUser = false
            )

        }
    }
}
