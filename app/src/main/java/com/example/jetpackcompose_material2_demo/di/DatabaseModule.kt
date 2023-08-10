package com.example.jetpackcompose_material2_demo.di

import android.app.Application
import androidx.room.Room
import com.example.jetpackcompose_material2_demo.data.local.NoteDao
import com.example.jetpackcompose_material2_demo.data.local.NoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application, callback: NoteDatabase.Callback): NoteDatabase {
        return Room.databaseBuilder(application, NoteDatabase::class.java, "news_database")
            .fallbackToDestructiveMigration()
            .addCallback(callback)
            .build()
    }

    @Provides
    fun provideArticleDao(db: NoteDatabase): NoteDao {
        return db.getArticleDao()
    }
}