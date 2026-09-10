package com.example.todoapp.data.remote.datasource

import com.example.todoapp.data.remote.dto.TaskDto
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class TaskDataSource @Inject constructor(private val firestore: FirebaseFirestore) {

    private val tasksCollection = firestore.collection("tasks")

    suspend fun addTask(task: TaskDto) {
        tasksCollection.document(task.id).set(task).await()
    }

    suspend fun getTaskById(id: String): TaskDto {
        val snapshot = tasksCollection.document(id).get().await()
        return snapshot.toObject(TaskDto::class.java)!!
    }


    fun getTasksByProjectId(projectId: String): Flow<List<TaskDto>> = callbackFlow {
        val listener = tasksCollection
            .whereEqualTo("projectId", projectId)
            .addSnapshotListener { snapshot, error ->
                val tasks = snapshot?.documents?.mapNotNull { it.toObject(TaskDto::class.java) }
                    ?: emptyList()
                trySend(tasks)
            }
        awaitClose { listener.remove() }
    }

    suspend fun changeTaskCompletionStatus(id: String, isCompleted: Boolean) {
        tasksCollection.document(id).update("completed", isCompleted).await()
    }

    suspend fun updateTask(task: TaskDto) {
        tasksCollection.document(task.id).set(task).await()
    }

    suspend fun deleteTask(id: String) {
        tasksCollection.document(id).delete().await()
    }

    suspend fun deleteTasksByProjectId(projectId: String) {
        val snapshot = tasksCollection.whereEqualTo("projectId", projectId).get().await()
        if (snapshot.isEmpty) return
        snapshot.documents.chunked(500).forEach { chunk ->
            val batch = firestore.batch()
            chunk.forEach { document ->
                batch.delete(document.reference)
            }
            batch.commit().await()
        }
    }

    fun getAllTasks(): Flow<List<TaskDto>> {
        return callbackFlow {
            val listener = tasksCollection.addSnapshotListener { snapshot, error ->
                val tasks = snapshot?.documents?.mapNotNull { it.toObject(TaskDto::class.java) }
                    ?: emptyList()
                trySend(tasks)
            }
            awaitClose { listener.remove() }
        }
    }
}