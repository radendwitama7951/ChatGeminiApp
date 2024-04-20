package com.example.chatgeminiapp.data.local.repositories

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import coil.size.Size
import com.example.chatgeminiapp.data.local.room.dao.ChatGroupDao
import com.example.chatgeminiapp.data.local.room.dao.ChatItemDao
import com.example.chatgeminiapp.data.local.room.database.AppDatabase
import com.example.chatgeminiapp.data._interfaces.GeminiChatApi
import com.example.chatgeminiapp.domain.models.gemini_chat.ChatGroup
import com.example.chatgeminiapp.domain.models.gemini_chat.ChatItem
import com.example.chatgeminiapp._common.resources.Constants.TEXT_PART
import com.google.ai.client.generativeai.Chat
import com.google.ai.client.generativeai.type.Content
import com.google.ai.client.generativeai.type.asTextOrNull
import com.google.ai.client.generativeai.type.content
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@HiltAndroidTest
class GeminiChatRepositoryTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("test_database")
    lateinit var database: AppDatabase

    @Inject
    lateinit var geminiChatApi: GeminiChatApi

    private lateinit var chatGroupDao: ChatGroupDao
    private lateinit var chatItemDao: ChatItemDao
    private lateinit var chat: Chat

    @Before
    fun setup() {
        hiltRule.inject()
        chatGroupDao = database.chatGroupDao
        chatItemDao = database.chatItemDao

        chat = geminiChatApi.getChat(emptyList())

    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun createAndGetChatGroup() = runTest {
        val title = "New Chat Group"
        val chatHistory = listOf(
            content(role = "user") { text("Hello, I have 2 dogs in my house.") },
            content(role = "model") { text("Great to meet you. What would you like to know?") },
            content(role = "user") { text("How many paw are in my house ?") },
            content(role = "model") { text("There is 8 paws in total assuming your dog is four-legged") },
        )
        val newChatGroup = ChatGroup(title = title)

        val newChatGroupId = chatGroupDao.upsert(newChatGroup)

        Log.d("MyTest", "Chat Group ID ${newChatGroupId}")
        chatHistory.forEach {
            val newChatItem = ChatItem(
                role = it.role,
                prompt = it.parts[0].asTextOrNull(),
                bitmapSource = "",
                chatGroupOwner = newChatGroupId
            )

            Log.d("MyTest", "Chat prompt ${it.parts[0].asTextOrNull()}")
            chatItemDao.upsert(newChatItem)
        }

        val allChatGroup = chatGroupDao.getFlatAll().first()

        Log.d("MyTest", "Chat Group $allChatGroup")


        assertEquals(1, allChatGroup.size)
        assertEquals(chatHistory.size, allChatGroup[0].chatItems.size)

    }

    @Test
    fun getChatImageResponse() = runTest {
        /* @Dependency */
        // val chatImageHandler = geminiChatApi.getChatImage()
        val chatHandler = geminiChatApi.getChat()

        /* @Params */
        val imageUri =
            "content://media/picker/0/com.android.providers.media.photopicker/media/1000002729n"
        val imageUrl = "https://images.dog.ceo/breeds/saluki/n02091831_3400.jpg"


        val imageBitmap = getBitmap(imageUrl)

        val initPrompt = content(role = "user") {
            text("What is this ?")
            image(imageBitmap)
        }
        val followupPrompt = content(role = "user") {
            text("How many paws on that image ?")
        }

        val newChatGroup = ChatGroup(title = "Test Integration")
        val chatGroupId = chatGroupDao.upsert(newChatGroup)
        val initChatItem = ChatItem(
            role = initPrompt.role,
            prompt = initPrompt.parts[TEXT_PART].asTextOrNull(),
            bitmapSource = imageUrl,
            chatGroupOwner = chatGroupId
        )
        val followupChatItem = ChatItem(
            role = followupPrompt.role,
            prompt = followupPrompt.parts[TEXT_PART].asTextOrNull(),
            chatGroupOwner = chatGroupId
        )

        /* @Action */
        chatItemDao.upsert(initChatItem)

        val initChatResponse = geminiChatApi.getResponse(initPrompt)
        val initChatResponseChatItem = ChatItem(
            prompt = initChatResponse.parts[TEXT_PART].asTextOrNull(),
            chatGroupOwner = chatGroupId
        )
        chatItemDao.upsert(initChatResponseChatItem)

        // ChatHandler cannot accept Image Part
        val initPromptWithoutImagePart =
            content("user") { text(initPrompt.parts[0].asTextOrNull() as String) }
        chatHandler.history.addAll(
            listOf(
                initPromptWithoutImagePart,
                initChatResponse
            )
        )

        chatItemDao.upsert(followupChatItem)
        val followupResponse = chatHandler.sendMessage(followupPrompt)
        val followupResponseChatItem = ChatItem(
            prompt = followupResponse.text,
            chatGroupOwner = chatGroupId
        )
        chatItemDao.upsert(followupResponseChatItem)


        chatHandler.history.forEach {
            Log.d("MyTest", "Role ${it.role} Content ${it.parts[0].asTextOrNull()}")
        }

        // Db verification
        val chatGroup = chatGroupDao.getFlatById(chatGroupId)
        Log.d("MyTest", "Chat Group $chatGroup")

        /* @Validation */
        assertEquals(chatGroup.chatItems.size, chatHandler.history.size)
    }

    @Test
    fun addPromptToChat () = runTest {
        val chat = getChatHandler()
        val chatGroupId = createChatGroup("Testing Response Chat")
        val response1 = getResponse(
            chatGroupId = chatGroupId,
            prompt = "Remember this, i have 4 apples and 4 oranges",
            bitmapSource = null
        )
        assertEquals(
            chat.history.last().parts[TEXT_PART].asTextOrNull(),
            response1.parts[TEXT_PART].asTextOrNull()
        )


        val response2 = getResponse(
            chatGroupId = chatGroupId,
            prompt = "I eat 2 of my apples, how many do i have now ?",
            bitmapSource = null
        )
        assertEquals(
            chat.history.last().parts[TEXT_PART].asTextOrNull(),
            response2.parts[TEXT_PART].asTextOrNull()
        )


        /* Validate */
        chat.history.forEach{
            Log.d("MyTest", "Role ${it.role} Content ${it.parts[0].asTextOrNull()}")
        }

        assertEquals(4, chat.history.size)

    }


    @Test
    fun addPromptWithImageToChat () = runTest {
        val chat = getChatHandler()
        val chatGroupId = createChatGroup("Testing Response Chat")

        val response1 = getResponse(
            chatGroupId = chatGroupId,
            prompt = "How many dogs in this picture ?:",
            bitmapSource = "https://images.dog.ceo/breeds/saluki/n02091831_3400.jpg"
        )

        val response2 = getResponse(
            chatGroupId = chatGroupId,
            prompt = "If I adopt 2 more dogs, how many do i have now ?",
            bitmapSource = null
        )


        /* Validate */
        chat.history.forEach{
            Log.d("MyTest", "Role ${it.role} Content ${it.parts[0].asTextOrNull()}")
        }

        assertEquals(chatGroupDao.getFlatById(chatGroupId).chatItems.size, chat.history.size)

    }




    /* Repository Logic */
    private fun initChat (chatHistory: List<Content> = emptyList()): Chat {
        this.chat = geminiChatApi.getChat(chatHistory)
        return this.chat
    }
    private fun getChatHandler(): Chat {
        initChat(emptyList())
        return this.chat
    }


    private suspend fun createChatGroup(title: String): Long {
        database.clearAllTables()
        val newChatGroup = ChatGroup(
            title = title,
        )
        return chatGroupDao.upsert(newChatGroup)
    }

    private suspend fun getResponse(chatGroupId: Long, prompt: String, bitmapSource: String?): Content {
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

            return content {
                text(text = response.text!!)
            }
        } else {
            val bitmap = getBitmap(bitmapSource)
            newContent = content (role = "user") {
                text(prompt)
                image(bitmap)
            }

            val response = geminiChatApi.getResponse(newContent)
            this.chat.history.addAll(
                listOf(
                    newContent,
                    response
                )
            )
            return response
        }



    }

    private suspend fun getBitmap(source: String, context: Context = getApplicationContext()): Bitmap {
        val imageRequest = ImageRequest.Builder(context)
            .data(source)
            .size(Size.ORIGINAL)
            .build()

        val imageLoader = ImageLoader(getApplicationContext())

        val imageResult = (imageLoader.execute(imageRequest) as SuccessResult).drawable
        val imageBitmap = (imageResult as BitmapDrawable).bitmap
        return imageBitmap
    }


}