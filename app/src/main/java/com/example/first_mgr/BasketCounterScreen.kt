package com.example.first_mgr

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

//@Composable
//fun BasketCounterScreen(exerciseStatsDao: ExerciseStatsDao) {
//    var basketsMade by remember { mutableStateOf(0) }
//    var basketShots by remember { mutableStateOf(0) }
//    var isSaveButtonClicked by remember { mutableStateOf(false) }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//    ) {
//        Text(text = "Baskets Made: $basketsMade / $basketShots")
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Button(onClick = {
//            basketsMade++
//            basketShots++
//        }) {
//            Text(text = "Made Basket")
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Button(onClick = {
//            basketShots++
//        }) {
//            Text(text = "Missed Basket")
//        }
//
//        Spacer(modifier = Modifier.height(32.dp))
//
//        if (isSaveButtonClicked) {
//            val exerciseStats = ExerciseStatistics(
//                exerciseName = "Example Exercise",
//                basketsMade = basketsMade,
//                basketShots = basketShots,
//                timestamp = System.currentTimeMillis()
//            )
//            saveExerciseStats(exerciseStatsDao, exerciseStats)
//            Text(text = "Data saved!")
//        } else {
//            Button(onClick = {
//                isSaveButtonClicked = true
//            }) {
//                Text(text = "Save Statistics")
//            }
//        }
//    }
//}
//
//@Composable
//fun saveExerciseStats(exerciseStatsDao: ExerciseStatsDao, exerciseStats: ExerciseStatistics) {
//    LaunchedEffect(exerciseStats) {
//        exerciseStatsDao.insert(exerciseStats)
//    }
//}
