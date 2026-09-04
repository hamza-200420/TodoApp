package com.example.todoapp.domain.usecase

import com.example.todoapp.domain.model.Task
import com.example.todoapp.domain.repository.TaskRepository
import javax.inject.Inject

class AddTaskUseCase @Inject constructor(private val repo: TaskRepository) {
    suspend operator fun invoke(task: Task) {
        return repo.addTask(task)
    }
}