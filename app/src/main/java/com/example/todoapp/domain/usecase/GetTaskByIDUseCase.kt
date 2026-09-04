package com.example.todoapp.domain.usecase

import com.example.todoapp.domain.model.Task
import com.example.todoapp.domain.repository.TaskRepository
import javax.inject.Inject

class GetTaskByIDUseCase @Inject constructor(private val repo: TaskRepository) {
    suspend operator fun invoke(id: Long): Task {
        return repo.getTaskById(id)
    }
}