package com.example.todoapp.presentation

import com.example.todoapp.domain.model.Project

data class ProjectScreenUiState (
    val projects: List<Project> = emptyList(),
    val isLoading:Boolean=false
)