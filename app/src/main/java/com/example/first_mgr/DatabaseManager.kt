package com.example.first_mgr

import android.content.Context
import androidx.room.Room


object DatabaseManager {
    private lateinit var appDatabase: AppDatabase

    fun initialize(context: Context) {
        appDatabase = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "exercise-database"
        ).build()
    }

    fun getExerciseStatisticsDao(): ExerciseStatsDao {
        return appDatabase.exerciseStatsDao()
    }
}