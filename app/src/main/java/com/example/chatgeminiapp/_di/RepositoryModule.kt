package com.example.chatgeminiapp._di

import com.example.chatgeminiapp.data.remote.api.GeminiChatApiImpl
import com.example.chatgeminiapp.data._interfaces.GeminiChatApi
import com.example.chatgeminiapp.data.repositories.GeminiChatRepositoryImpl
import com.example.chatgeminiapp.domain.repositories.GeminiChatRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindGeminiChatsRepository(
        geminiChatsRepositoryImpl: GeminiChatRepositoryImpl,
    ): GeminiChatRepository

    @Singleton
    @Binds
    abstract fun bindGeminiChatsApi(
        geminiChatsApiImpl: GeminiChatApiImpl
    ): GeminiChatApi
}