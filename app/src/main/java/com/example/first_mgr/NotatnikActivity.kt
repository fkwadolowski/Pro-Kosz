package com.example.first_mgr

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.first_mgr.Constants.preconfiguredNames
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class NotatnikActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DatabaseManager.initialize(applicationContext)
        setContent {
            val exerciseStatsDao = DatabaseManager.getExerciseStatisticsDao()
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                BasketCounterScreen(exerciseStatsDao)
                Spacer(modifier = Modifier.height(16.dp))
                DisplayExerciseStats(exerciseStatsDao)
            }
        }
    }

    // ...

    @Composable
    fun BasketCounterScreen(exerciseStatsDao: ExerciseStatsDao) {
        var basketsMade by remember { mutableStateOf(0) }
        var basketShots by remember { mutableStateOf(0) }
        var isSaveButtonClicked by remember { mutableStateOf(false) }
        var exerciseStatsList by remember { mutableStateOf(emptyList<ExerciseStatistics>()) }
        var selectedExerciseName by remember { mutableStateOf(preconfiguredNames[0]) }
        var selectedText by remember { mutableStateOf(preconfiguredNames[0]) }


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(text = "Baskets Made: $basketsMade / $basketShots")

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                basketsMade++
                basketShots++
            }) {
                Text(text = "Made Basket")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                basketShots++
            }) {
                Text(text = "Missed Basket")
            }

            Spacer(modifier = Modifier.height(16.dp))
            Demo_ExposedDropdownMenuBox(selectedText) { selectedValue ->
                selectedExerciseName = selectedValue
            }
            // Dropdown to select exercise name
//            DropdownMenu(
//                expanded = selectedExerciseName.isNotEmpty(),
//                onDismissRequest = { /* Close the dropdown */ }
//            ) {
//                preconfiguredNames.forEach { name ->
//                    DropdownMenuItem(onClick = {
//                        onItemSelected(name)
//                        expanded = false
//                    }) {
//                        Text(text = name)
//                    }
//                }
//            }

            Spacer(modifier = Modifier.height(16.dp))

            if (isSaveButtonClicked) {
                val exerciseStats = ExerciseStatistics(
                    exerciseName = selectedExerciseName,
                    basketsMade = basketsMade,
                    basketShots = basketShots,
                    timestamp = System.currentTimeMillis()
                )
                saveExerciseStats(exerciseStatsDao, exerciseStats)
                basketsMade = 0
                basketShots = 0
                isSaveButtonClicked = false
            } else {
                Button(onClick = {
                    isSaveButtonClicked = true
                }) {
                    Text(text = "Save Statistics")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Button to print the exercise statistics
            Button(onClick = {
                exerciseStatsList.forEach { stats ->
                    println(
                        "Exercise: ${stats.exerciseName}\n" +
                                "Baskets Made: ${stats.basketsMade}\n" +
                                "Basket Shots: ${stats.basketShots}\n" +
                                "Timestamp: ${stats.timestamp}"
                    )
                }
            }) {
                Text(text = "Print Statistics")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                // Start the new activity
                val intent = Intent(this@NotatnikActivity, SavedProgressActivity::class.java)
                startActivity(intent)
            }) {
                Text(text = "View Statistics")
            }
        }
    }

    @Composable
    fun saveExerciseStats(exerciseStatsDao: ExerciseStatsDao, exerciseStats: ExerciseStatistics) {
        LaunchedEffect(exerciseStats) {
            withContext(Dispatchers.IO) {
                exerciseStatsDao.insert(exerciseStats)
            }
        }
    }

    @Composable
    fun DisplayExerciseStats(exerciseStatsDao: ExerciseStatsDao) {
        var exerciseStatsList by remember { mutableStateOf(emptyList<ExerciseStatistics>()) }

        // Fetch the exercise statistics from the database
        LaunchedEffect(Unit) {
            withContext(Dispatchers.IO) {
                val stats = exerciseStatsDao.getAllExerciseStats()
                exerciseStatsList = stats
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(text = "Exercise Statistics:")

            Spacer(modifier = Modifier.height(16.dp))

            exerciseStatsList.forEach { stats ->
                Text(
                    text = "Exercise: ${stats.exerciseName}\n" +
                            "Baskets Made: ${stats.basketsMade}\n" +
                            "Basket Shots: ${stats.basketShots}\n" +
                            "Timestamp: ${stats.timestamp}"
                )

                Spacer(modifier = Modifier.height(16.dp))
            }

            // Add a button to print the exercise statistics
            Button(onClick = {
                exerciseStatsList.forEach { stats ->
                    Log.d("Print Statistics",
                        "Exercise: ${stats.exerciseName}\n" +
                                "Baskets Made: ${stats.basketsMade}\n" +
                                "Basket Shots: ${stats.basketShots}\n" +
                                "Timestamp: ${stats.timestamp}"
                    )
                }
            }) {
                Text(text = "Print Statistics")
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Demo_ExposedDropdownMenuBox(
    // Define the variable `selectedText` in the scope of the function
    selectedText: String,
    onSelectedTextChange: (String) -> Unit
) {
    val context = LocalContext.current
    val coffeeDrinks = arrayOf("dystans", "pół-dystans", "trumna", "dwutakty")
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp)
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = it
            }
        ) {
            TextField(
                value = selectedText,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                coffeeDrinks.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item) },
                        onClick = {
                            onSelectedTextChange(item) // Update the selected text
                            expanded = false
                            Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }
    }
}