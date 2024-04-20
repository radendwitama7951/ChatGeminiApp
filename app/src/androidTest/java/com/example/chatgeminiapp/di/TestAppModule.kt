package com.example.chatgeminiapp.di

import android.content.Context
import androidx.room.Room
import com.example.chatgeminiapp.data.local.room.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {
    @Provides
    @Named("test_database")
    fun provideTestAppDatabase(
        @ApplicationContext application: Context
    ): AppDatabase {
        return Room.inMemoryDatabaseBuilder(
            context = application,
            klass = AppDatabase::class.java,
        ).allowMainThreadQueries()
            .build()
    }



}
