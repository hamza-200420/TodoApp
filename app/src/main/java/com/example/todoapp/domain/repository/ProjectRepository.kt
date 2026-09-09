package com.example.todoapp.domain.repository

import com.example.todoapp.domain.model.Project
import kotlinx.coroutines.flow.Flow

interface ProjectRepository {
    suspend fun addProject(project: Project)
    suspend fun deleteProject(id: Long)
    fun getAllProjects(): Flow<List<Project>>
}