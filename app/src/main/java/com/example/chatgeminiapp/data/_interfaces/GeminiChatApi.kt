package com.example.chatgeminiapp.data._interfaces

import android.graphics.Bitmap
import com.example.chatgeminiapp.domain.models.gemini_chat.LocalChat
import com.google.ai.client.generativeai.Chat
import com.google.ai.client.generativeai.type.Content

interface GeminiChatApi {
    fun getChat (history: List<Content> = emptyList()): Chat

    fun getChatImage (history: List<Content> = emptyList()): Chat

    suspend fun getResponse (content: Content): Content

    suspend fun getResponse (prompt: String): LocalChat
    suspend fun getResponse (prompt: String, bitmap: Bitmap): LocalChat

}