package com.example.first_mgr

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SavedProgressActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val exerciseStatsDao = DatabaseManager.getExerciseStatisticsDao()
        setContent {
            val exerciseStatsList = remember { mutableStateListOf<ExerciseStatistics>() }

            // Fetch the exercise statistics from the database
            LaunchedEffect(true) {
                withContext(Dispatchers.IO) {
                    val stats = exerciseStatsDao.getAllExerciseStats()
                    exerciseStatsList.addAll(stats)
                    Log.d("SavedProgressActivity", "Fetched ${stats.size} exercise statistics")
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top

            ) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text("Data")
                        Text("Nazwa ćwiczenia")
                        Text("Skuteczność")


                    }
                }

                items(exerciseStatsList) { stats ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(formatTimestamp(stats.timestamp))
                        Text(stats.exerciseName)
                        Text("${stats.basketsMade}/${stats.basketShots}")
                        Text("")
                    }
                }
            }
        }
    }
}
private fun formatTimestamp(timestamp: Long): String {
    val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    return dateFormat.format(Date(timestamp))
}