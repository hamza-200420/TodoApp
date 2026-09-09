package com.example.todoapp.data.local.repository

import com.example.todoapp.data.local.dao.ProjectDao
import com.example.todoapp.data.local.mapper.toDomain
import com.example.todoapp.data.local.mapper.toEntity
import com.example.todoapp.domain.model.Project
import javax.inject.Inject
import com.example.todoapp.domain.repository.ProjectRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProjectRepositoryImpl @Inject constructor(private val projectDao: ProjectDao) :
    ProjectRepository {
    override suspend fun addProject(project: Project){
         projectDao.insertProject(project.toEntity())
    }

    override suspend fun deleteProject(id: Long) {
        projectDao.deleteProject(id)
    }

    override fun getAllProjects(): Flow<List<Project>> {
        return projectDao.getProjectsWithTaskCounts().map { entities ->
            entities.map { it.toDomain() }

        }
    }
}