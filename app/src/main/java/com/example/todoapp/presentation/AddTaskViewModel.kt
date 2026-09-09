package com.example.todoapp.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.domain.model.Task
import com.example.todoapp.domain.usecase.AddTaskUseCase
import com.example.todoapp.domain.usecase.GetTaskByIDUseCase
//import com.example.todoapp.domain.usecase.GetTaskByIdUseCase
import com.example.todoapp.domain.usecase.UpdateTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTaskViewModel @Inject constructor(
    private val addTaskUseCase: AddTaskUseCase,
    private val getTaskByIdUseCase: GetTaskByIDUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddTaskUiState())
    val uiState: StateFlow<AddTaskUiState> = _uiState.asStateFlow()

    private val taskId: Long? =
        savedStateHandle.get<Long>("taskId")
    private val projectId: Long? =
        savedStateHandle.get<Long>("projectId")
    init {
        onScreenOpen()
    }

    private fun onScreenOpen() {
        if (taskId != null) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val task = getTaskByIdUseCase(taskId)
                    _uiState.update {
                        it.copy(
                            title = task.title,
                            details = task.description,
                            selectedDate = task.reminderDate
                        )
                    }
                } catch (e: Exception) {
                    _uiState.update {
                        it.copy(error = true)
                    }
                }
            }
        }
    }

    fun onTitleChange(newTitle: String) {
        _uiState.update {
            it.copy(title = newTitle)
        }
    }

    fun onDetailsChange(newDetails: String) {
        _uiState.update {
            it.copy(details = newDetails)
        }
    }

    fun selectDate(date: Long) {
        _uiState.update {
            it.copy(selectedDate = date)
        }
    }

    fun submitTask(): Boolean {

        val isTitleValid = _uiState.value.title.isNotBlank()
        val isDetailsValid = _uiState.value.details.isNotBlank()
        val isDateValid = _uiState.value.selectedDate != 0L

        _uiState.update {
            it.copy(
                titleError = !isTitleValid,
                detailsError = !isDetailsValid,
                dateError = !isDateValid,
                error = !isTitleValid || !isDetailsValid || !isDateValid
            )
        }

        if (!isTitleValid || !isDetailsValid || !isDateValid) {
            return false
        }

        if (taskId != null) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val existingTask =
                        getTaskByIdUseCase(taskId)
                    updateTaskUseCase(
                        Task(
                            id = taskId,
                            projectId = existingTask.projectId,
                            title = _uiState.value.title,
                            description = _uiState.value.details,
                            isCompleted = existingTask.isCompleted,
                            reminderDate = _uiState.value.selectedDate
                        )
                    )
                    _uiState.update { it.copy(error = false) }
                } catch (e: Exception) {
                    _uiState.update {
                        it.copy(error = true)
                    }
                }
            }

        } else {

            viewModelScope.launch(Dispatchers.IO) {
                try {

                    addTaskUseCase(
                        Task(
                            id = 0,
                            projectId = projectId!!,
                            title = _uiState.value.title,
                            description = _uiState.value.details,
                            isCompleted = false,
                            reminderDate = _uiState.value.selectedDate
                        )
                    )
                    _uiState.update { it.copy(error = false) }

                } catch (e: Exception) {

                    _uiState.update {
                        it.copy(error = true)
                    }
                }
            }
        }
        return true
    }
}