package com.example.todoapp.data.remote.mapper

import com.example.todoapp.data.remote.dto.ProjectDto
import com.example.todoapp.domain.model.Project

fun Project.toDto(): ProjectDto {
    return ProjectDto(id = id.toString(), projectName = projectName)
}

fun ProjectDto.toDomain(totalTasks: Int, completedTasks: Int): Project {
    return Project(
        id = id.toLong(),
        projectName = projectName,
        totalTasks = totalTasks,
        completedTasks = completedTasks
    )
}