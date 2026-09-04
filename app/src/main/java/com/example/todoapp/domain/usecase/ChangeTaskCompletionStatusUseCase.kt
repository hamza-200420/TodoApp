package com.example.todoapp.domain.usecase

import com.example.todoapp.domain.repository.TaskRepository
import javax.inject.Inject

class ChangeTaskCompletionStatusUseCase @Inject constructor(private val repo: TaskRepository) {
    suspend operator fun invoke(id: Long, isCompleted: Boolean) {
        return repo.changeTaskCompletionStatus(id, isCompleted)
    }
}