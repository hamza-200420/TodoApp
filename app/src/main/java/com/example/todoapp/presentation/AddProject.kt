package com.example.todoapp.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun AddProject(onNavigateBack: () -> Unit, viewModel: AddProjectViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .statusBarsPadding()
    ) {
        Text(
            text = "New Project",
            fontSize = 20.sp,
            color = Color(0xFF0560FA)
        )

        OutlinedTextField(
            value = uiState.projectName,
            onValueChange = { name ->
                viewModel.onProjectNameChange(name)
            },
            label = { Text("Project Name") },
            placeholder = { Text("Add project name", color = Color.Gray) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedLabelColor = Color(0xFF0560FA),
                unfocusedLabelColor = Color(0xFF0560FA),
                errorTextColor = Color.Black
            ),
            isError = uiState.emptyFieldError,
            supportingText = {
                if (uiState.emptyFieldError) {
                    Text(
                        text = "Please Enter Project Name",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        )

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = onNavigateBack,
                modifier = Modifier.size(height = 44.dp, width = 153.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color(0xFF318FFF)
                )
            ) {
                Text("Cancel")
            }

            Button(
                onClick = {
                    if (viewModel.onSave()) {
                        onNavigateBack()
                    }
                },
                modifier = Modifier.size(height = 44.dp, width = 153.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF318FFF),
                    contentColor = Color.White
                )
            ) {
                Text("Save project")
            }
        }
    }
}