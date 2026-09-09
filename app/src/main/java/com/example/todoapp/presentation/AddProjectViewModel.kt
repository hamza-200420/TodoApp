package com.example.todoapp.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.domain.model.Project
import com.example.todoapp.domain.usecase.AddProjectUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddProjectViewModel @Inject constructor(private val addProjectUseCase: AddProjectUseCase) :
    ViewModel() {
    private val _uiState = MutableStateFlow(AddProjectUiState())
    val uiState: StateFlow<AddProjectUiState> = _uiState.asStateFlow()

    fun onProjectNameChange(newName: String) {
        _uiState.update { currentState ->
            currentState.copy(projectName = newName)
        }
    }

    fun onSave(): Boolean {
        if (uiState.value.projectName.isEmpty()) {
            _uiState.update { it.copy(emptyFieldError = true) }
            return false
        }
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("PROJECT_SAVE", "Before insert")
            addProjectUseCase(Project(id = 0, projectName = uiState.value.projectName))
            Log.d("PROJECT_SAVE", "After insert")
        }
        return true
    }
}