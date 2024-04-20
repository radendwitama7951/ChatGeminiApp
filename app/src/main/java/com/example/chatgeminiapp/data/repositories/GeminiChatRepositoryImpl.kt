package com.example.chatgeminiapp.data.repositories

import android.graphics.Bitmap
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import com.example.chatgeminiapp.data.local.room.dao.ChatGroupDao
import com.example.chatgeminiapp.data.local.room.dao.ChatItemDao
import com.example.chatgeminiapp.data._interfaces.GeminiChatApi
import com.example.chatgeminiapp.domain.models.gemini_chat.ChatGroup
import com.example.chatgeminiapp.domain.models.gemini_chat.ChatGroupWithChatItems
import com.example.chatgeminiapp.domain.models.gemini_chat.ChatItem
import com.example.chatgeminiapp.domain.models.gemini_chat.LocalChat
import com.example.chatgeminiapp.domain.repositories.GeminiChatRepository
import com.example.chatgeminiapp._common.resources.Constants.TEXT_PART
import com.example.chatgeminiapp._common.utils.uriToBitmap
import com.google.ai.client.generativeai.Chat
import com.google.ai.client.generativeai.type.Content
import com.google.ai.client.generativeai.type.asTextOrNull
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GeminiChatRepositoryImpl @Inject constructor(
    private val geminiChatApi: GeminiChatApi,
    private val chatGroupDao: ChatGroupDao,
    private val chatItemDao: ChatItemDao
): GeminiChatRepository {
    private var chat: Chat = geminiChatApi.getChat()
    private var activeChatGroupId: Long = 0

    override fun getChatHandler (): Chat {
        return this.chat
    }
    override fun initChat(chatHistory: List<Content>): Chat {
        this.chat = geminiChatApi.getChat(chatHistory)
        return this.chat
    }

    override suspend fun initChat(chatHistory: List<ChatItem>): Chat {
        if (chatHistory.isNotEmpty()) {
            this.chat = geminiChatApi.getChat(chatItemListToContentList(chatHistory))
        } else {
            this.chat = geminiChatApi.getChat()
        }
        return this.chat
    }
    override fun getChatGroups (): Flow<List<ChatGroup>> {
        return chatGroupDao.getAll()
    }

    override suspend fun selectChatGroup (id: Long): ChatGroupWithChatItems {
        this.activeChatGroupId = id
        return chatGroupDao.getFlatById(id)
    }

    override fun getActiveChatGroup (): Long {
        return this.activeChatGroupId
    }

    override suspend fun createChatGroup(title: String): Long {
        val newChatGroup = ChatGroup(
            title = title,
        )
        this.activeChatGroupId = chatGroupDao.upsert(newChatGroup)
        return this.activeChatGroupId
    }

    override suspend fun getResponse(chatGroupId: Long, prompt: String, bitmapSource: String?): String? {
        withContext(Dispatchers.IO) {
            val newChatItem = ChatItem(
                role = "user",
                prompt = prompt,
                bitmapSource = bitmapSource,
                chatGroupOwner = chatGroupId
            )
            chatItemDao.upsert(newChatItem)
        }

        val newContent: Content
        if (bitmapSource.isNullOrEmpty()) {
            newContent = content (role = "user") {
                text(prompt)
            }
            val response = this.chat.sendMessage(newContent)
            val responseChatItem = ChatItem(
                prompt = response.text,
                chatGroupOwner = chatGroupId
            )
            chatItemDao.upsert(responseChatItem)

            return response.text
        } else {
            val bitmap = uriToBitmap(getApplicationContext(), bitmapSource.toUri())
            newContent = content (role = "user") {
                text(prompt)
                image(bitmap!!)
            }

            val response = geminiChatApi.getResponse(newContent)
            this.chat.history.addAll(
                listOf(
                    newContent,
                    response
                )
            )
            return response.parts[TEXT_PART].asTextOrNull()
        }
    }




    override suspend fun getResponse (prompt: String): LocalChat {
        val response: LocalChat = geminiChatApi.getResponse(prompt)
        return response
    }


    override suspend fun getResponse (prompt: String, bitmap: Bitmap): LocalChat{
        val response: LocalChat = geminiChatApi.getResponse(prompt, bitmap)
        return response
    }


    private fun chatItemListToContentList (chatItemList: List<ChatItem>): List<Content> {
        val chatItemsAsContentList = chatItemList.map {
            content (role = it.role) {
               text(it.prompt!!)
            }
        }

        return chatItemsAsContentList


    }

}