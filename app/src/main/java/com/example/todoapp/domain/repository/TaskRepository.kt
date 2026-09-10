package com.example.todoapp.domain.repository

import com.example.todoapp.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    suspend fun addTask(task: Task)
    suspend fun getTaskById(id: Long): Task

    suspend fun changeTaskCompletionStatus(id: Long, isCompleted: Boolean)
    suspend fun updateTask(task: Task)

    fun getTasksByProjectId(id: Long): Flow<List<Task>>
    suspend fun deleteTask(id:Long)

}