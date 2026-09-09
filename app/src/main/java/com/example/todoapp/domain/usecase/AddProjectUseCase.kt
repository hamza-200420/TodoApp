package com.example.todoapp.domain.usecase

import com.example.todoapp.domain.model.Project
import com.example.todoapp.domain.repository.ProjectRepository
import javax.inject.Inject

class AddProjectUseCase @Inject constructor(private val repo: ProjectRepository) {
    suspend operator fun invoke(project: Project){
        return repo.addProject(project)
    }
}