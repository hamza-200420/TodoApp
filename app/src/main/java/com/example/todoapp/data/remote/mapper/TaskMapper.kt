package com.example.todoapp.data.remote.mapper

import com.example.todoapp.data.remote.dto.TaskDto
import com.example.todoapp.domain.model.Task

fun TaskDto.toDomain(): Task {
    return Task(
        id = id.toLong(),
        projectId = projectId.toLong(),
        title = title,
        description = description,
        isCompleted = completed,
        createdAt = createdAt,
        reminderDate = reminderDate
    )
}

fun Task.toDto(): TaskDto {
    return TaskDto(
        id = id.toString(),
        projectId = projectId.toString(),
        title = title,
        description = description,
        completed = isCompleted,
        createdAt = createdAt,
        reminderDate = reminderDate
    )
}