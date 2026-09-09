package com.example.todoapp.domain.usecase

import com.example.todoapp.domain.model.Project
import com.example.todoapp.domain.repository.ProjectRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllProjectsUseCase @Inject constructor(private val repo: ProjectRepository) {
    operator fun invoke(): Flow<List<Project>> {
        return repo.getAllProjects()
    }
}