package com.example.todoapp.domain.repository

import com.example.todoapp.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    suspend fun addTask(task: Task)
    suspend fun getTaskById(id: Long): Task

    fun getAllTasks(): Flow<List<Task>>
    suspend fun changeTaskCompletionStatus(id: Long, isCompleted: Boolean)
}