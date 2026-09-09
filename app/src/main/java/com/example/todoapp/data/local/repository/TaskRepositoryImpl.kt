package com.example.todoapp.data.local.repository

import com.example.todoapp.data.local.dao.TaskDao
import com.example.todoapp.data.local.mapper.toDomain
import com.example.todoapp.data.local.mapper.toEntity
import com.example.todoapp.domain.model.Task
import com.example.todoapp.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(private val taskDao: TaskDao) : TaskRepository {
    override suspend fun addTask(task: Task) {
        taskDao.insertTask(task.toEntity())
    }

    override fun getAllTasks(): Flow<List<Task>> {
        return taskDao.getAllTasks().map { e -> e.map { it.toDomain() } }
    }

    override suspend fun getTaskById(id: Long): Task {
        return taskDao.getTaskById(id).toDomain()
    }

    override suspend fun changeTaskCompletionStatus(id: Long, isCompleted: Boolean) {
        taskDao.changeTaskCompletionStatus(id, isCompleted)
    }

    override suspend fun updateTask(task: Task) {
        taskDao.updateTask(task.toEntity())
    }

    override fun getTasksByProjectId(id: Long): Flow<List<Task>> {
        return taskDao.getTasksByProjectId(id)
            .map { entities ->
                entities.map { it.toDomain() }
            }
    }

    override suspend fun deleteTask(id: Long) {
        taskDao.deleteTask(id)
    }
}