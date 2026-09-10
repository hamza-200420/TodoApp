package com.example.todoapp.data.remote.repository

import com.example.todoapp.data.remote.datasource.TaskDataSource
import com.example.todoapp.data.remote.FirebaseIdGenerator
import com.example.todoapp.data.remote.mapper.toDomain
import com.example.todoapp.data.remote.mapper.toDto
import com.example.todoapp.domain.model.Task
import com.example.todoapp.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(private val taskDataSource: TaskDataSource) :
    TaskRepository {
    override suspend fun addTask(task: Task) {
        val newId = FirebaseIdGenerator.generateId()
        val updatedTask = task.copy(id = newId)
        taskDataSource.addTask(updatedTask.toDto())
    }

    override suspend fun changeTaskCompletionStatus(id: Long, isCompleted: Boolean) {
        taskDataSource.changeTaskCompletionStatus(id.toString(), isCompleted)
    }

    override suspend fun deleteTask(id: Long) {
        taskDataSource.deleteTask(id.toString())
    }

    override suspend fun getTaskById(id: Long): Task {
        return taskDataSource.getTaskById(id.toString()).toDomain()
    }

    override fun getTasksByProjectId(id: Long): Flow<List<Task>> {
        return taskDataSource.getTasksByProjectId(id.toString()).map { e ->
            e.map { it.toDomain() }
        }
    }

    override suspend fun updateTask(task: Task) {
        taskDataSource.updateTask(task.toDto())
    }

}