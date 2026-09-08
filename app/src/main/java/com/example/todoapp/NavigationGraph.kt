package com.example.todoapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.todoapp.presentation.AddTask
import com.example.todoapp.presentation.HomeScreen

object NavRoutes {
    const val HOME = "home"
    const val ADD_TASK = "add_task"
    const val ADD_TASK_WITH_ID = "add_task/{taskId}"
}

@Composable
fun SetupNavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = NavRoutes.HOME
    ) {
        composable(NavRoutes.HOME) {
            HomeScreen(
                onAddTaskClick = {
                    navController.navigate(NavRoutes.ADD_TASK)
                },
                onTaskClick = { taskId ->
                    navController.navigate(
                        "add_task/$taskId"
                    )
                }
            )
        }
        composable(
            route = NavRoutes.ADD_TASK
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
        ) { backStackEntry ->
            val taskId =
                backStackEntry.arguments?.getLong("taskId")
            AddTask(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}