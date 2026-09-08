package com.example.todoapp.presentation

data class AddTaskUiState(
    val title: String = "",
    val details: String = "",
    val selectedDate: Long = 0,
    val isSubmitting: Boolean=false,
    val error : Boolean=false,
    val titleError: Boolean=false,
    val detailsError:Boolean = false,
    val dateError: Boolean=false
)