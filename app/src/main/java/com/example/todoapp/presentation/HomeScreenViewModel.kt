package com.example.todoapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.domain.model.Task
import com.example.todoapp.domain.usecase.ChangeTaskCompletionStatusUseCase
import com.example.todoapp.domain.usecase.GetAllTasksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val getTasksUseCase: GetAllTasksUseCase,
    private val toggleTaskCompletionUseCase: ChangeTaskCompletionStatusUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeScreenUiState())
    val uiState: StateFlow<HomeScreenUiState> = _uiState.asStateFlow()

    init {
        retrieveAllTasks()
    }

    private fun retrieveAllTasks() {
        viewModelScope.launch {
            getTasksUseCase().collect { tasks ->
                _uiState.update { currentState ->
                    val filteredTasks = filterTasksByDate(
                        tasks = tasks,
                        selectedDate = currentState.selectedDate
                    )
                    currentState.copy(
                        isLoading = false,
                        allTasks = tasks,
                        pendingTasks = filteredTasks.filter { !it.isCompleted },
                        completedTasks = filteredTasks.filter { it.isCompleted },
                        error = null
                    )
                }
            }
        }
    }

    fun onDateSelected(date: LocalDate) {
        _uiState.update { currentState ->
            val newSelectedDate = if (currentState.selectedDate == date) {
                null
            } else {
                date
            }
            val filteredTasks = filterTasksByDate(
                tasks = currentState.allTasks,
                selectedDate = newSelectedDate
            )
            currentState.copy(
                selectedDate = newSelectedDate,
                pendingTasks = filteredTasks.filter { !it.isCompleted },
                completedTasks = filteredTasks.filter { it.isCompleted }
            )
        }
    }

    private fun filterTasksByDate(
        tasks: List<Task>,
        selectedDate: LocalDate?
    ): List<Task> {
        if (selectedDate == null) {
            return tasks
        }
        return tasks.filter { task ->
            val taskDate = Instant
                .ofEpochMilli(task.reminderDate)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()

            taskDate == selectedDate
        }
    }

    fun onTaskCheckedChange(
        id: Long,
        isCompleted: Boolean
    ) {
        viewModelScope.launch {
            toggleTaskCompletionUseCase(
                id = id,
                isCompleted = isCompleted
            )
        }
    }
}