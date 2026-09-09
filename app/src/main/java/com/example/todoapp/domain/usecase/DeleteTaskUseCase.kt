package com.example.todoapp.domain.usecase

import com.example.todoapp.domain.repository.TaskRepository
import javax.inject.Inject

class DeleteTaskUseCase @Inject constructor(private val repo: TaskRepository) {
    suspend operator fun invoke(id:Long){
         repo.deleteTask(id)
    }

}