package com.example.chatgeminiapp.domain.repositories

import android.graphics.Bitmap
import com.example.chatgeminiapp.domain.models.gemini_chat.ChatGroup
import com.example.chatgeminiapp.domain.models.gemini_chat.ChatGroupWithChatItems
import com.example.chatgeminiapp.domain.models.gemini_chat.ChatItem
import com.example.chatgeminiapp.domain.models.gemini_chat.InternalChat
import com.example.chatgeminiapp.domain.models.gemini_chat.InternalChatGroup
import com.google.ai.client.generativeai.Chat
import com.google.ai.client.generativeai.type.Content
import kotlinx.coroutines.flow.Flow

interface GeminiChatRepository {
    /* Chat Handler */
    fun initChat (chatHistory: List<Content> = emptyList()): Chat?

    suspend fun initChat(chatHistory: List<ChatItem>): Chat?
    fun getChatHandler (): Chat?

    /* ChatGroup */
    suspend fun generateChatGroupTitle (chatGroupId: Long, prompt: String): InternalChatGroup
    suspend fun createChatGroup (title: String?): Long
    suspend fun getChatGroups(): List<ChatGroup>
    suspend fun selectChatGroup(id: Long): ChatGroupWithChatItems

    /* ChatItem */
    suspend fun getResponse(chatGroupId: Long, prompt: String, bitmap: Bitmap? = null, bitmapSource: String? = null): InternalChat
    ///
    suspend fun getResponse (prompt: String): InternalChat
    suspend fun getResponse (prompt: String, bitmap: Bitmap): InternalChat
    suspend fun updateChatGroup(id: Long, title: String): ChatGroup
    fun terminateChat()
    fun filterChatItems(chatItems: List<ChatItem>): List<ChatItem>
    fun chatItemToInternal(chatItem: ChatItem, bitmap: Bitmap? = null): InternalChat
    fun chatGroupToInternal(chatGroup: ChatGroup): InternalChatGroup
    fun chatGroupToInternal(chatGroup: ChatGroupWithChatItems): InternalChatGroup
    suspend fun deleteChatGroup(id: Long): Long
}

