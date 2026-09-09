package com.example.todoapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.todoapp.data.local.entity.ProjectEntity
import kotlinx.coroutines.flow.Flow


data class ProjectWithTaskCountPojo(
    val id: Long,
    val projectName: String,
    val totalTasks: Int,
    val completedTasks: Int
)
@Dao
interface ProjectDao {

    @Insert
    suspend fun insertProject(project: ProjectEntity)

    @Query("DELETE FROM projects WHERE id = :projectId")
    suspend fun deleteProject(projectId: Long)

    @Query(
        "SELECT p.id AS id, p.projectName AS projectName, COUNT(t.id) AS totalTasks, SUM(CASE WHEN t.isCompleted = 1 THEN 1 ELSE 0 END) AS completedTasks FROM projects p LEFT JOIN tasks t ON t.projectId = p.id GROUP BY p.id ORDER BY p.id DESC "
    )
    fun getProjectsWithTaskCounts(): Flow<List<ProjectWithTaskCountPojo>>
}