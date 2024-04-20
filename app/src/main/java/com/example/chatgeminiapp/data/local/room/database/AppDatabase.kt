package com.example.chatgeminiapp.data.local.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.chatgeminiapp.data.local.room.dao.ChatGroupDao
import com.example.chatgeminiapp.data.local.room.dao.ChatItemDao
import com.example.chatgeminiapp.domain.models.gemini_chat.ChatGroup
import com.example.chatgeminiapp.domain.models.gemini_chat.ChatItem

@Database(entities = [ChatGroup::class, ChatItem::class], version = 3)
abstract class AppDatabase: RoomDatabase() {
    abstract val chatGroupDao: ChatGroupDao
    abstract val chatItemDao: ChatItemDao
}
