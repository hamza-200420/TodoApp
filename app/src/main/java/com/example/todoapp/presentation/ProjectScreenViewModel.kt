package com.example.todoapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.domain.usecase.DeleteProjectUseCase
import com.example.todoapp.domain.usecase.GetAllProjectsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProjectScreenViewModel @Inject constructor(
    private val getAllProjectsUseCase: GetAllProjectsUseCase,
    private val deleteProjectUseCase: DeleteProjectUseCase
) :
    ViewModel() {
    private val _uiState = MutableStateFlow(ProjectScreenUiState())
    val uiState: StateFlow<ProjectScreenUiState> = _uiState.asStateFlow()

    init {
        loadProjects()
    }


    private fun loadProjects() {
        viewModelScope.launch {
            getAllProjectsUseCase().collect { projects ->
                _uiState.update { currentState ->
                    currentState.copy(
                        projects = projects
                    )
                }
            }
        }
    }

    fun deleteProject(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteProjectUseCase(id)
        }
    }

}