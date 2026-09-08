package com.example.todoapp.domain.usecase

import com.example.todoapp.domain.model.Task
import com.example.todoapp.domain.repository.TaskRepository
import javax.inject.Inject

class UpdateTaskUseCase @Inject constructor(
    private val repository: TaskRepository
) {

    suspend operator fun invoke(task: Task) {
        repository.updateTask(task)
    }
}