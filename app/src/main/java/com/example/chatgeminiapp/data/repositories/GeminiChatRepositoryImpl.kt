package com.example.chatgeminiapp.data.repositories

import android.graphics.Bitmap
import com.example.chatgeminiapp._common.resources.Constants.MODEL_ROLE
import com.example.chatgeminiapp.data.local.room.dao.ChatGroupDao
import com.example.chatgeminiapp.data.local.room.dao.ChatItemDao
import com.example.chatgeminiapp.data.remote.gemini_chat.interfaces.GeminiChatApi
import com.example.chatgeminiapp.domain.models.gemini_chat.ChatGroup
import com.example.chatgeminiapp.domain.models.gemini_chat.ChatGroupWithChatItems
import com.example.chatgeminiapp.domain.models.gemini_chat.ChatItem
import com.example.chatgeminiapp.domain.models.gemini_chat.InternalChat
import com.example.chatgeminiapp.domain.repositories.GeminiChatRepository
import com.example.chatgeminiapp._common.resources.Constants.TEXT_PART
import com.example.chatgeminiapp._common.resources.Constants.USER_ROLE
import com.example.chatgeminiapp.domain.models.gemini_chat.InternalChatGroup
import com.google.ai.client.generativeai.Chat
import com.google.ai.client.generativeai.type.Content
import com.google.ai.client.generativeai.type.asTextOrNull
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class GeminiChatRepositoryImpl @Inject constructor(
    private val geminiChatApi: GeminiChatApi,
    private val chatGroupDao: ChatGroupDao,
    private val chatItemDao: ChatItemDao
) : GeminiChatRepository {
    private var chat: Chat? = geminiChatApi.getChat()

    override fun getChatHandler(): Chat? {
        return this.chat
    }

    override fun initChat(chatHistory: List<Content>): Chat? {
        this.chat = geminiChatApi.getChat(chatHistory)
        return this.chat
    }

    override fun terminateChat() {
        this.chat = null
    }


    override suspend fun initChat(chatHistory: List<ChatItem>): Chat? {
        if (chatHistory.isNotEmpty()) {
            this.chat = geminiChatApi.getChat(chatItemListToContentList(chatHistory))
        } else {
            this.chat = geminiChatApi.getChat()
        }
        return this.chat
    }

    override suspend fun generateChatGroupTitle(
        chatGroupId: Long,
        prompt: String
    ): InternalChatGroup {
        val newTitle = geminiChatApi.getResponse(
            "Create title with 3 to 5 word from this context: $prompt; if no relevant context return \"Gemini Chat\"; return with plain font"
        ).prompt
        val selectedChatGroup = chatGroupDao.getById(chatGroupId)
        val updatedChatGroup = selectedChatGroup.copy(title = newTitle)


        val id = chatGroupDao.upsert(updatedChatGroup)
        val afterUpdate = chatGroupDao.getById(chatGroupId)

        return chatGroupToInternal(updatedChatGroup)
    }


    override suspend fun getChatGroups(): List<ChatGroup> {
        val allChatGroup = chatGroupDao.getAll()

        return allChatGroup
    }

    override suspend fun deleteChatGroup(id: Long): Long {
        val selectedChatGroup = ChatGroup(id = id)
        chatGroupDao.delete(selectedChatGroup)
        return id
    }

    override suspend fun updateChatGroup(id: Long, title: String): ChatGroup {
        val renewedChatGroup = ChatGroup(id = id, title = title)
        chatGroupDao.upsert(renewedChatGroup)
        val updatedChatGroup = chatGroupDao.getById(id)

        return updatedChatGroup
    }

    override suspend fun selectChatGroup(id: Long): ChatGroupWithChatItems {
        return chatGroupDao.getFlatById(id)
    }


    override suspend fun createChatGroup(title: String?): Long {
        val newChatGroup = ChatGroup(
            title = title,
        )
        val activeChatGroupId = chatGroupDao.upsert(newChatGroup)
        return activeChatGroupId
    }

    override suspend fun getResponse(
        chatGroupId: Long,
        prompt: String,
        bitmap: Bitmap?,
        bitmapSource: String?
    ): InternalChat {

        val newChatItem = ChatItem(
            role = USER_ROLE,
            prompt = prompt,
            bitmapSource = bitmapSource,
            chatGroupOwner = chatGroupId
        )
        withContext(Dispatchers.IO) {
            chatItemDao.upsert(newChatItem)
            chatGroupDao.upsert(
                chatGroupDao.getById(chatGroupId).copy(editedAt = System.currentTimeMillis())
            ) // Update editedAt field
        }

        val newContent: Content
        if (bitmap == null) {
            newContent = content(role = "user") {
                text(prompt)
            }
            val response = this.chat?.sendMessage(newContent)
            val responseChatItem = ChatItem(
                prompt = response?.text,
                chatGroupOwner = chatGroupId,
                role = MODEL_ROLE
            )



            withContext(Dispatchers.IO) {
                chatItemDao.upsert(responseChatItem)
                val groupChats = chatGroupDao.getFlatById(chatGroupId).chatItems
            }
            return chatItemToInternal(chatItem = responseChatItem)
        } else {
            val newContentForRequestWithImage = content(role = "user") {
                text(prompt)
                image(bitmap)
            }

            val response = geminiChatApi.getResponse(newContentForRequestWithImage)

            newContent = content(role = "user") {
                text(prompt)
            }
            this.chat?.history?.addAll(
                listOf(
                    newContent,
                    response
                )
            )

            val responseChatItem = ChatItem(
                prompt = response.parts[TEXT_PART].asTextOrNull(),
                chatGroupOwner = chatGroupId,
                role = MODEL_ROLE
            )
            chatItemDao.upsert(responseChatItem)

            return chatItemToInternal(responseChatItem)
        }
    }


    override suspend fun getResponse(prompt: String): InternalChat {
        val response: InternalChat = geminiChatApi.getResponse(prompt)
        return response
    }


    override suspend fun getResponse(prompt: String, bitmap: Bitmap): InternalChat {
        val response: InternalChat = geminiChatApi.getResponse(prompt, bitmap)
        return response
    }


    private fun chatItemListToContentList(chatItemList: List<ChatItem>): List<Content> {
        val chatItemsAsContentList = chatItemList.map {
            content(
                role = if (it.role == USER_ROLE) {
                    "user"
                } else {
                    "model"
                }
            ) {
                text(it.prompt!!)
            }
        }

        return chatItemsAsContentList


    }

    override fun filterChatItems(chatItems: List<ChatItem>): List<ChatItem> {
        if (chatItems.isEmpty()) {
            return chatItems
        }
        /*
        * Sort chat item from latest
        * */
        val chatItemsSorted = chatItems.sortedByDescending { it.createdAt }
        val filteredChatItems = mutableListOf<ChatItem>()

        var prevChatItemRole =
            if (chatItemsSorted.first().role == USER_ROLE) MODEL_ROLE else USER_ROLE

        /*
        * Remove every greedy chat
        * when user chatting 2 times or more in a row
        * */
        for (currChatItem in chatItemsSorted) {
            if (currChatItem.role == prevChatItemRole) {
                continue
            }
            filteredChatItems.add(currChatItem)
            currChatItem.role.also { prevChatItemRole = it }
        }


        /*
        * Remove if there is chat of USER_ROLE last time
        * Happen when model failed to respond last time
        * */
        if (filteredChatItems.first().role == USER_ROLE) {
            filteredChatItems.removeFirst()
        }


        return filteredChatItems.toList()
    }

    override fun chatGroupToInternal(chatGroup: ChatGroup): InternalChatGroup {
        return InternalChatGroup(
            id = chatGroup.id,
            title = chatGroup.title,
            createdAt = formatTimestamp(chatGroup.createdAt),
            editedAt = chatGroup.editedAt
        )
    }

    override fun chatGroupToInternal(chatGroup: ChatGroupWithChatItems): InternalChatGroup {
        return InternalChatGroup(
            id = chatGroup.prop.id,
            title = chatGroup.prop.title,
            createdAt = formatTimestamp(chatGroup.prop.createdAt),
            editedAt = chatGroup.prop.editedAt
        )
    }

    override fun chatItemToInternal(chatItem: ChatItem, bitmap: Bitmap?): InternalChat {
        return InternalChat(
            prompt = chatItem.prompt ?: "Error: null chat !",
            bitmap = bitmap,
            bitmapUri = chatItem.bitmapSource,
            isFromUser = chatItem.role == USER_ROLE
        )
    }

    // Utils
    private fun formatTimestamp(timestamp: Long, format: String = "HH:mm MM-dd-yyyy"): String {
        return SimpleDateFormat(format, Locale.getDefault()).format(Date(timestamp))
    }
}