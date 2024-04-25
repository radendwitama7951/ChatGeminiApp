package com.example.chatgeminiapp._di

import android.app.Application
import androidx.room.Room
import com.example.chatgeminiapp.data.local.room.dao.ChatGroupDao
import com.example.chatgeminiapp.data.local.room.dao.ChatItemDao
import com.example.chatgeminiapp.data.local.room.database.AppDatabase
import com.example.chatgeminiapp.data.remote.gemini_chat.api.GeminiChatApiImpl
import com.example.chatgeminiapp._common.resources.Constants.GEMINI_API_KEY
import com.example.chatgeminiapp._common.resources.Constants.GEMINI_MODEL
import com.example.chatgeminiapp._common.resources.Constants.GEMINI_MODEL_VISION
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.BlockThreshold
import com.google.ai.client.generativeai.type.HarmCategory
import com.google.ai.client.generativeai.type.SafetySetting
import com.google.ai.client.generativeai.type.generationConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        application: Application
    ): AppDatabase {
        return Room.databaseBuilder(
            context = application,
            klass = AppDatabase::class.java,
            name = "gemini_chat_db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideChatGroupDao(
        appDatabase: AppDatabase
    ): ChatGroupDao = appDatabase.chatGroupDao
    @Provides
    @Singleton
    fun provideChatItemDao(
        appDatabase: AppDatabase
    ): ChatItemDao = appDatabase.chatItemDao

    @Provides
    @Singleton
    fun provideGeminiChatsApi (
        @Impl1 generativeModel: GenerativeModel,
        @Impl2 generativeVisionModel: GenerativeModel
    ): GeminiChatApiImpl = GeminiChatApiImpl(generativeModel, generativeVisionModel)

    @Impl1
    @Provides
    @Singleton
    fun provideGeminiGenerativeModel (
    ): GenerativeModel {
        return GenerativeModel(
            modelName = GEMINI_MODEL,
            apiKey = GEMINI_API_KEY,
            generationConfig = generationConfig{
                temperature = .9f
                topK = 16
                topP = .1f
            },
            safetySettings = listOf(
                SafetySetting(HarmCategory.HARASSMENT, BlockThreshold.NONE),
                SafetySetting(HarmCategory.SEXUALLY_EXPLICIT, BlockThreshold.NONE),
                SafetySetting(HarmCategory.HATE_SPEECH, BlockThreshold.NONE),
                SafetySetting(HarmCategory.DANGEROUS_CONTENT, BlockThreshold.NONE),
            )
        )
    }


    @Impl2
    @Provides
    @Singleton
    fun provideGeminiGenerativeVisionModel (
    ): GenerativeModel {
        return GenerativeModel(
            modelName = GEMINI_MODEL_VISION,
            apiKey = GEMINI_API_KEY,
            generationConfig = generationConfig{
                temperature = .9f
                topK = 16
                topP = .1f
            },
            safetySettings = listOf(
                SafetySetting(HarmCategory.HARASSMENT, BlockThreshold.NONE),
                SafetySetting(HarmCategory.SEXUALLY_EXPLICIT, BlockThreshold.NONE),
                SafetySetting(HarmCategory.HATE_SPEECH, BlockThreshold.NONE),
                SafetySetting(HarmCategory.DANGEROUS_CONTENT, BlockThreshold.NONE),
            )
        )
    }



}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Impl1

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Impl2
