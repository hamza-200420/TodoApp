package com.example.todoapp.di


import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

import com.example.todoapp.data.local.TodoDatabase
import com.example.todoapp.data.local.dao.TaskDao

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): TodoDatabase {
        return Room.databaseBuilder(
            context,
            TodoDatabase::class.java,
            "taskDatabase"
        ).build()
    }

    @Provides
    fun provideTaskDao(
        database: TodoDatabase
    ): TaskDao {
        return database.taskDao()
    }
}