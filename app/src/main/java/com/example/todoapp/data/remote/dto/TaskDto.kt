package com.example.todoapp.data.remote.dto

data class TaskDto(
    val id: String = "",
    val projectId: String = "",
    val title: String = "",
    val description: String = "",
    val completed: Boolean = false,
    val createdAt: Long = 0L,
    val reminderDate: Long = 0L
)