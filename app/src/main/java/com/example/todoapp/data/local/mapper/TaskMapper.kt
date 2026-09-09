package com.example.todoapp.data.local.mapper

import com.example.todoapp.data.local.entity.TaskEntity
import com.example.todoapp.domain.model.Task

fun TaskEntity.toDomain(): Task {
    return Task(id, projectId, title, description, isCompleted, createdAt, reminderDate)
}

fun Task.toEntity(): TaskEntity {
    return TaskEntity(id, projectId, title, description, isCompleted, createdAt, reminderDate)
}