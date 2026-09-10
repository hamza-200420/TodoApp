package com.example.todoapp.di

import com.example.todoapp.data.remote.repository.ProjectRepositoryImpl
import com.example.todoapp.data.remote.repository.TaskRepositoryImpl
import com.example.todoapp.domain.repository.ProjectRepository
import com.example.todoapp.domain.repository.TaskRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindTaskRepo(
        impl: TaskRepositoryImpl
    ): TaskRepository

    @Binds
    @Singleton
    abstract fun bindProjectRepo(
        impl: ProjectRepositoryImpl
    ): ProjectRepository
}