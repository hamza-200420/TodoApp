package com.example.todoapp.data.remote

import kotlin.random.Random

object FirebaseIdGenerator {
    fun generateId(): Long {
        return System.currentTimeMillis() * 1000 + Random.nextInt(1000)
    }
}