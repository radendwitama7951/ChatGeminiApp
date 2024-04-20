package com.example.chatgeminiapp.domain.repositories

import android.graphics.Bitmap
import com.example.chatgeminiapp.domain.models.gemini_chat.ChatGroup
import com.example.chatgeminiapp.domain.models.gemini_chat.ChatGroupWithChatItems
import com.example.chatgeminiapp.domain.models.gemini_chat.ChatItem
import com.example.chatgeminiapp.domain.models.gemini_chat.LocalChat
import com.google.ai.client.generativeai.Chat
import com.google.ai.client.generativeai.type.Content
import kotlinx.coroutines.flow.Flow

interface GeminiChatRepository {
    /* Chat Handler */
    fun initChat (chatHistory: List<Content> = emptyList()): Chat

    suspend fun initChat(chatHistory: List<ChatItem>): Chat
    fun getChatHandler (): Chat

    /* ChatGroup */
    suspend fun createChatGroup (title: String): Long
    fun getChatGroups(): Flow<List<ChatGroup>>
    suspend fun selectChatGroup(id: Long): ChatGroupWithChatItems

    /* ChatItem */
    suspend fun getResponse(chatGroupId: Long, prompt: String, bitmapSource: String?): String?
    ///
    suspend fun getResponse (prompt: String): LocalChat
    suspend fun getResponse (prompt: String, bitmap: Bitmap): LocalChat
    fun getActiveChatGroup(): Long
}

