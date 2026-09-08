package com.example.todoapp.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.todoapp.R
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset
import java.util.Locale


@Composable
fun AddTask(
    viewModel: AddTaskViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var isCalendarOpen by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .padding(16.dp)
            .statusBarsPadding()
    ) {
        Text(text = "New Task", fontSize = 20.sp, color = Color(0xFF0560FA))
        OutlinedTextField(
            value = uiState.title,
            onValueChange = { viewModel.onTitleChange(it) },
            label = { Text("Task Title") },
            placeholder = { Text("Add title", color = Color.Black) },

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
            ), isError = uiState.titleError,
            supportingText = {
                if (uiState.titleError) {
                    Text(
                        text = "Title can't be empty",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        )
        OutlinedTextField(
            value = uiState.details,
            onValueChange = { viewModel.onDetailsChange(it) },
            label = { Text("Task Details") },
            placeholder = { Text("Add Details", color = Color.Black) },
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
            ), isError = uiState.detailsError,
            supportingText = {
                if (uiState.detailsError) {
                    Text(
                        text = "Details can't be empty",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        )
        HorizontalDivider(
            thickness = 1.dp,
            color = Color.LightGray,
            modifier = Modifier.padding(top = 30.dp, bottom = 10.dp)
        )
        Row(modifier = Modifier.fillMaxWidth()) {
            IconButton(onClick = {
                isCalendarOpen = true
            }) {
                Icon(
                    painter = painterResource(R.drawable.icon_date),
                    contentDescription = "CalendarIcon",
                    tint = Color(0xFF6200EE),
                    modifier = Modifier.size(22.dp)
                )
            }
            Text(text = "Date", modifier = Modifier.padding(top = 12.dp), fontSize = 14.sp)
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = if (uiState.selectedDate == 0L) {
                    "Selected Date"
                } else {
                    SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
                        .format(uiState.selectedDate)
                },
                fontSize = 16.sp,
                color = Color(0xFF318FFF),
                modifier = Modifier.padding(top = 12.dp)
            )
        }
        HorizontalDivider(
            thickness = 1.dp,
            color = Color.LightGray,
            modifier = Modifier.padding(top = 15.dp, bottom = 10.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
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

                    if (viewModel.submitTask()) {
                        onNavigateBack()
                    }

                },
                modifier = Modifier.size(height = 44.dp, width = 153.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF318FFF),
                    contentColor = Color.White
                )
            ) {
                Text("Save this task")
            }

        }
    }
    if (isCalendarOpen) {
        val datePickerState = rememberDatePickerState()

        DatePickerDialog(
            onDismissRequest = { isCalendarOpen = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { utcMillis ->
                        val localMillis = Instant.ofEpochMilli(utcMillis)
                            .atZone(ZoneOffset.UTC)
                            .toLocalDate()
                            .atStartOfDay(ZoneId.systemDefault())
                            .toInstant()
                            .toEpochMilli()
                        viewModel.selectDate(localMillis)
                    }
                    isCalendarOpen = false
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { isCalendarOpen = false }) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}