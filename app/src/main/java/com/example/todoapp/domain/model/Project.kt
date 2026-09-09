package com.example.todoapp.domain.model

data class Project (
    val id: Long,
    val projectName: String,
    val totalTasks: Int=0,
    val completedTasks: Int=0
)