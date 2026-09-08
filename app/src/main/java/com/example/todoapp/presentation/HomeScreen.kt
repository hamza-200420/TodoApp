package com.example.todoapp.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.todoapp.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onAddTaskClick: () -> Unit,
    viewModel: HomeScreenViewModel = hiltViewModel(),
    onTaskClick: (Long) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val calendarScrollState = rememberScrollState()
    val dates = (0 until 30).map {
        LocalDate.now().plusDays(it.toLong())
    }

    val dayFormatter = DateTimeFormatter.ofPattern("EEE")
    Column(
        modifier = modifier
            .statusBarsPadding()
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                painter = painterResource(R.drawable.icon_date),
                contentDescription = "CalendarIcon",
                tint = Color(0xFF6200EE),
                modifier = Modifier.size(22.dp)
            )

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = "Today",
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp,
                color = Color(0xFF0560FA)
            )

            Spacer(modifier = Modifier.weight(1f))

            Icon(
                painter = painterResource(R.drawable.icon_all_task),
                contentDescription = "All Tasks",
                tint = Color(0xFF6200EE),
                modifier = Modifier.size(22.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(calendarScrollState),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            dates.forEach { date ->
                ScrollingCalendarWidget(
                    day = date.format(dayFormatter),
                    date = date.dayOfMonth,
                    isSelected = date == uiState.selectedDate,

                    onClick = {
                        viewModel.onDateSelected(date)
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(6.dp),
                contentPadding = PaddingValues(vertical = 4.dp)
            ) {

                if (uiState.pendingTasks.isNotEmpty()) {

                    item {

                        Text(
                            text = "Pending",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF0560FA),
                            modifier = Modifier.padding(
                                top = 4.dp,
                                bottom = 8.dp
                            )
                        )
                    }

                    items(
                        items = uiState.pendingTasks,
                        key = { task -> task.id }
                    ) { task ->

                        TaskItem(
                            title = task.title,
                            isCompleted = task.isCompleted,
                            onCheckedChange = { checked ->
                                viewModel.onTaskCheckedChange(
                                    id = task.id,
                                    isCompleted = checked
                                )
                            },

                            onClick = {
                                onTaskClick(task.id)
                            },
                        )

                        HorizontalDivider(
                            thickness = 1.dp,
                            color = Color.LightGray,
                            modifier = Modifier.padding(
                                top = 20.dp,
                                bottom = 20.dp
                            )
                        )
                    }
                }

                if (uiState.completedTasks.isNotEmpty()) {
                    item {
                        Text(
                            text = "Completed",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Gray,
                            modifier = Modifier.padding(
                                top = 16.dp,
                                bottom = 8.dp
                            )
                        )
                    }

                    items(
                        items = uiState.completedTasks,
                        key = { task -> task.id }
                    ) { task ->

                        TaskItem(
                            title = task.title,
                            isCompleted = task.isCompleted,

                            onCheckedChange = { checked ->

                                viewModel.onTaskCheckedChange(
                                    id = task.id,
                                    isCompleted = checked
                                )
                            },

                            onClick = {
                                onTaskClick(task.id)
                            },
                        )

                        HorizontalDivider(
                            thickness = 1.dp,
                            color = Color.LightGray,
                            modifier = Modifier.padding(
                                top = 20.dp,
                                bottom = 20.dp
                            )
                        )
                    }
                }
            }

            Button(
                onClick = onAddTaskClick,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(50.dp),
                shape = RoundedCornerShape(12.dp),
                contentPadding = PaddingValues(0.dp)
            ) {

                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Composable
fun ScrollingCalendarWidget(
    day: String,
    date: Int,
    isSelected: Boolean = false,
    onClick: () -> Unit
) {

    val bgColor =
        if (isSelected) {
            Color(0xFF0560FA)
        } else {
            Color(0xFF6200EE)
        }

    val numBgColor =
        if (isSelected) {
            Color(0xFF0560FA)
        } else {
            Color(0xFF318FFF)
        }

    Column(
        modifier = Modifier
            .width(44.dp)
            .clickable {
                onClick()
            }
    ) {

        Text(
            text = day,
            modifier = Modifier
                .fillMaxWidth()
                .height(22.dp)
                .background(
                    color = bgColor,
                    shape = RoundedCornerShape(
                        topStart = 7.dp,
                        topEnd = 7.dp
                    )
                ),
            color = Color.White,
            fontSize = 10.sp,
            textAlign = TextAlign.Center
        )

        Text(
            text = date.toString(),
            modifier = Modifier
                .fillMaxWidth()
                .height(34.dp)
                .background(
                    color = numBgColor,
                    shape = RoundedCornerShape(
                        bottomStart = 7.dp,
                        bottomEnd = 7.dp
                    )
                ),
            color = Color.White,
            fontSize = 16.sp,
            fontWeight =
                if (isSelected) {
                    FontWeight.Bold
                } else {
                    FontWeight.Normal
                },
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun TaskItem(
    title: String,
    isCompleted: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onClick: () -> Unit,
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = onClick
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Checkbox(
            checked = isCompleted,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(
                checkedColor = Color(0xFF54A2FF),
                uncheckedColor = Color(0xFFDDECFF)
            ),
            modifier = Modifier
                .size(24.dp)
                .clip(RoundedCornerShape(6.dp))
        )

        Text(
            text = title,
            modifier = Modifier.padding(start = 10.dp),
            fontSize = 14.sp,
            textDecoration =
                if (isCompleted) {
                    TextDecoration.LineThrough
                } else {
                    TextDecoration.None
                },
            color =
                if (isCompleted) {
                    Color(0xFF76B5FF)
                } else {
                    Color.Black
                }
        )
    }
}