package com.example.chatgeminiapp.data.local.dao

import android.util.Log
import androidx.test.filters.SmallTest
import com.example.chatgeminiapp.data.local.room.dao.ChatGroupDao
import com.example.chatgeminiapp.data.local.room.database.AppDatabase
import com.example.chatgeminiapp.domain.models.gemini_chat.ChatGroup
import com.google.ai.client.generativeai.type.content
import org.junit.Before
import org.junit.Test
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Rule
import javax.inject.Inject
import javax.inject.Named


@SmallTest
@HiltAndroidTest
class ChatGroupDaoTest() {

    @get:Rule
    val rule = HiltAndroidRule(this)

    @Inject
    @Named("test_database")
    lateinit var database: AppDatabase

    private lateinit var dao: ChatGroupDao

    @Before
    fun setup() {
        rule.inject()
        dao = database.chatGroupDao

    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun createNewChatGroup () = runTest {
        val newChatGroup = ChatGroup(title = "First Chat Group")
        val newChats = listOf(
            content(role = "user") { text("Hello, I have 2 dogs in my house.") },
            content(role = "model") { text("Great to meet you. What would you like to know?") }
        )




        dao.upsert(newChatGroup)

        val chatGroups = dao.getFlatAll().first()
        Log.d("Instrument Test", "Chat Groups : ${chatGroups}")

        assertEquals(1, chatGroups.size)
    }



}
