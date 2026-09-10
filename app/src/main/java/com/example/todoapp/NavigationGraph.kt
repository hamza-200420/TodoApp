package com.example.todoapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.todoapp.presentation.AddProject
import com.example.todoapp.presentation.AddTask
import com.example.todoapp.presentation.HomeScreen
import com.example.todoapp.presentation.ProjectScreen

object NavRoutes {
    const val HOME = "home"
    const val HOME_WITH_ID = "home/{projectId}"
    const val ADD_TASK = "add_task/{projectId}"
    const val ADD_TASK_WITH_ID = "add_task/edit/{taskId}"
    const val PROJECT_HOME = "project_home"
    const val ADD_PROJECT = "add_project"
}

@Composable
fun SetupNavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = NavRoutes.PROJECT_HOME
    ) {
        composable(NavRoutes.PROJECT_HOME) {
            ProjectScreen(
                onAddProjectClick = { navController.navigate(NavRoutes.ADD_PROJECT) },
                onProjectClick = { projectId ->
                    navController.navigate("home/$projectId")
                }
            )
        }
        composable(NavRoutes.ADD_PROJECT) {
            AddProject(onNavigateBack = {navController.popBackStack() })
        }


        composable(
            route = NavRoutes.HOME_WITH_ID,
            arguments = listOf(
                navArgument("projectId") {
                    type = NavType.LongType
                }
            )
        ) { backStackEntry ->
            val projectId = backStackEntry.arguments?.getLong("projectId") ?: 0L
            HomeScreen(
                onAddTaskClick = {
                    navController.navigate("add_task/$projectId")
                },
                onTaskClick = { taskId ->
                    navController.navigate(
                        "add_task/edit/$taskId"
                    )
                }
            )
        }
        composable(
            route = NavRoutes.ADD_TASK,
            arguments = listOf(
                navArgument("projectId") {
                    type = NavType.LongType
                }
            )
        ) {
            AddTask(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        composable(
            route = NavRoutes.ADD_TASK_WITH_ID,
            arguments = listOf(
                navArgument("taskId") {
                    type = NavType.LongType
                }
            )
        ) {
            AddTask(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}