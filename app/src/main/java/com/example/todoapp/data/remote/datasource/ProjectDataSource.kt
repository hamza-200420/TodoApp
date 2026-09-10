package com.example.todoapp.data.remote.datasource

import com.example.todoapp.data.remote.dto.ProjectDto
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ProjectDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    private val projectsCollection = firestore.collection("projects")

    suspend fun addProject(project: ProjectDto) {
        projectsCollection.document(project.id).set(project).await()
    }

    suspend fun deleteProject(id: String) {
        projectsCollection.document(id).delete().await()
    }

    fun getAllProjects(): Flow<List<ProjectDto>> {
        return callbackFlow {
            val listener = projectsCollection.addSnapshotListener { snapshot, error ->
                val projects = snapshot?.documents?.mapNotNull {
                    it.toObject(ProjectDto::class.java)
                } ?: emptyList()
                trySend(projects)
            }
            awaitClose { listener.remove() }
        }
    }
}