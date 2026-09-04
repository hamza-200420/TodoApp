package com.example.todoapp.domain.usecase

import com.example.todoapp.domain.model.Task
import com.example.todoapp.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllTasksUseCase @Inject constructor(private val repo : TaskRepository) {
    operator fun invoke() : Flow<List<Task>>{
        return repo.getAllTasks()
    }
}