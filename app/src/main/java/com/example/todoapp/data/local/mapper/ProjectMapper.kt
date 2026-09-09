package com.example.todoapp.data.local.mapper

import com.example.todoapp.data.local.dao.ProjectWithTaskCountPojo
import com.example.todoapp.data.local.entity.ProjectEntity
import com.example.todoapp.domain.model.Project

fun Project.toEntity(): ProjectEntity {
    return ProjectEntity(id, projectName)
}

fun ProjectWithTaskCountPojo.toDomain(): Project {
    return Project(
        id = id,
        projectName = projectName,
        totalTasks = totalTasks,
        completedTasks = completedTasks
    )
}