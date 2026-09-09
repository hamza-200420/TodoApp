package com.example.todoapp.domain.usecase

import com.example.todoapp.domain.repository.ProjectRepository
import javax.inject.Inject

class DeleteProjectUseCase @Inject constructor(private val repo: ProjectRepository) {
    suspend operator fun invoke(id:Long){
        return repo.deleteProject(id)
    }
}