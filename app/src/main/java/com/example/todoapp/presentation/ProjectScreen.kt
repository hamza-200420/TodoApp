package com.example.todoapp.presentation

import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.todoapp.domain.model.Project
import com.example.todoapp.R

@Composable
fun ProjectScreen(
    modifier: Modifier = Modifier,
    onAddProjectClick: () -> Unit,
    onProjectClick: (Long) -> Unit,
    viewModel: ProjectScreenViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

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
                painter = androidx.compose.ui.res.painterResource(
                    R.drawable.icon_date
                ),
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
                painter = androidx.compose.ui.res.painterResource(
                    R.drawable.icon_all_task
                ),
                contentDescription = "All Tasks",
                tint = Color(0xFF6200EE),
                modifier = Modifier.size(22.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 60.dp)
                ) {
                    items(
                        items = uiState.projects,
                        key = { project -> project.id }
                    ) { project ->

                        ProjectItem(
                            project = project,
                            onDeleteClick = { projectId ->
                                viewModel.deleteProject(projectId)
                            },
                            onClick = { projectId ->
                                onProjectClick(projectId)
                            }
                        )
                    }
                }
            }

            Button(
                onClick = onAddProjectClick,
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
fun ProjectItem(
    project: Project,
    onClick: (Long) -> Unit,
    onDeleteClick: (Long) -> Unit
) {
    val progress = if (project.totalTasks > 0) {
        project.completedTasks.toFloat() / project.totalTasks.toFloat()
    } else {
        0f
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick(project.id)
            },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF5F7FF)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = project.projectName,
                    modifier = Modifier.weight(1f),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0560FA)
                )

                IconButton(
                    onClick = {
                        onDeleteClick(project.id)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete project",
                        tint = Color.Red
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Progress",
                    fontSize = 12.sp,
                    color = Color.Gray
                )

                Text(
                    text = "${project.completedTasks}/${project.totalTasks} Tasks Completed",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF6200EE)
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                color = Color(0xFF0560FA),
                trackColor = Color(0xFFDDECFF),
                strokeCap = StrokeCap.Round
            )
        }
    }
}
