package com.example.todoapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.todoapp.data.local.dao.ProjectDao
import com.example.todoapp.data.local.dao.TaskDao
import com.example.todoapp.data.local.entity.ProjectEntity
import com.example.todoapp.data.local.entity.TaskEntity

@Database(entities = [TaskEntity::class, ProjectEntity::class], version = 2, exportSchema = false)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun projectDao(): ProjectDao
}