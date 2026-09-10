package com.example.todoapp.presentation

data class AddProjectUiState(
    val projectName: String="",
    val emptyFieldError: Boolean =false,
)