package com.example.todoapp.presentation

import com.example.todoapp.domain.model.Task
import java.time.LocalDate

data class HomeScreenUiState(
    val isLoading: Boolean = false,
    val allTasks: List<Task> = emptyList(),
    val pendingTasks: List<Task> = emptyList(),
    val completedTasks: List<Task> = emptyList(),
    val selectedDate: LocalDate? = null,
    val error: String? = null
)