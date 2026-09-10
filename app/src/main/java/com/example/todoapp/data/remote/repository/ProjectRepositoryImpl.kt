package com.example.todoapp.data.remote.repository

import com.example.todoapp.data.remote.FirebaseIdGenerator
import com.example.todoapp.data.remote.datasource.ProjectDataSource
import com.example.todoapp.data.remote.datasource.TaskDataSource
import com.example.todoapp.data.remote.mapper.toDto
import com.example.todoapp.domain.model.Project
import com.example.todoapp.domain.repository.ProjectRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class ProjectRepositoryImpl @Inject constructor(
    private val projectDataSource: ProjectDataSource,
    private val taskDataSource: TaskDataSource
) : ProjectRepository{
    override suspend fun addProject(project: Project) {
        val newId = FirebaseIdGenerator.generateId()
        val updatedProject = project.copy(id = newId)
        projectDataSource.addProject(updatedProject.toDto())
    }

    override suspend fun deleteProject(id: Long) {
        projectDataSource.deleteProject(id.toString())
    }

    override fun getAllProjects(): Flow<List<Project>> {
        return combine(
            projectDataSource.getAllProjects(),
            taskDataSource.getAllTasks()
        ) { projects, tasks ->
            projects.map { projects ->
                val tasksForProject = tasks.filter { it.projectId == projects.id }
                Project(
                    id = projects.id.toLong(),
                    projectName = projects.projectName,
                    totalTasks = tasksForProject.size,
                    completedTasks = tasksForProject.count { it.completed }
                )
            }.sortedByDescending { it.id }
        }
    }
}