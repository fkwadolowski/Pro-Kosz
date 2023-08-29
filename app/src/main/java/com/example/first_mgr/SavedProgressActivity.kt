package com.example.first_mgr

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
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
        val barValue: List<Float> = listOf(
            0.5f, 0.6f, 0.2f, 0.7f, 0.8f,
            0.3f, 0.4f, 0.6f, 0.9f, 0.2f,
            0.7f, 0.8f, 0.4f, 0.5f, 0.3f,
            0.6f, 0.1f, 0.9f, 0.2f, 0.4f,
            0.7f, 0.3f, 0.8f, 0.5f, 0.6f,
            0.4f, 0.2f, 0.9f, 0.1f, 0.7f,
            0.8f
        )

        val xAxisScale: List<String> = listOf(
            "Label 1", "Label 2", "Label 3", "Label 4", "Label 5",
            "Label 6", "Label 7", "Label 8", "Label 9", "Label 10",
            "Label 11", "Label 12", "Label 13", "Label 14", "Label 15",
            "Label 16", "Label 17", "Label 18", "Label 19", "Label 20",
            "Label 21", "Label 22", "Label 23", "Label 24", "Label 25",
            "Label 26", "Label 27", "Label 28", "Label 29", "Label 30",
            "Label 31"
        )

        setContent {
            val exerciseStatsDao = DatabaseManager.getExerciseStatisticsDao()
            val exerciseStatsList = remember { mutableStateListOf<ExerciseStatistics>() }

            // Fetch the exercise statistics from the database
            LaunchedEffect(true) {
                withContext(Dispatchers.IO) {
                    val stats = exerciseStatsDao.getAllExerciseStats()
                    exerciseStatsList.addAll(stats)
                    Log.d("SavedProgressActivity", "Fetched ${stats.size} exercise statistics")
                }
            }

            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // First part: Header row
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

                // Second part: List of exercise statistics
                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {
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

                // Third part: Another component
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    // Your third component's content goes here
                    Text(text = "Third Component")
                    CustomChart(barValue,xAxisScale,100)
                    Spacer(modifier = Modifier.height(16.dp)) // Adds vertical spacing between components
                }
            }
        }
    }
}
private fun formatTimestamp(timestamp: Long): String {
    val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    return dateFormat.format(Date(timestamp))
}
@Composable
fun CustomChart(
    barValue: List<Float>,
    xAxisScale: List<String>,
    total_amount: Int
) {
    val context = LocalContext.current
    // BarGraph Dimensions
    val barGraphHeight by remember { mutableStateOf(200.dp) }
    val barGraphWidth by remember { mutableStateOf(20.dp) }
    // Scale Dimensions
    val scaleYAxisWidth by remember { mutableStateOf(50.dp) }
    val scaleLineWidth by remember { mutableStateOf(2.dp) }

    Column(
        modifier = Modifier
            .padding(50.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        LazyRow {
            items(1) { // Only one item, since we are scrolling horizontally
                Row(
                    modifier = Modifier
                        .height(barGraphHeight),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.Start
                ) {
                    // scale Y-Axis
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(scaleYAxisWidth),
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        Column(
                            modifier = Modifier.fillMaxHeight(),
                            verticalArrangement = Arrangement.Bottom
                        ) {
                            Text(text = total_amount.toString())
                            Spacer(modifier = Modifier.fillMaxHeight())
                        }

                        Column(
                            modifier = Modifier.fillMaxHeight(),
                            verticalArrangement = Arrangement.Bottom
                        ) {
                            Text(text = (total_amount / 2).toString())
                            Spacer(modifier = Modifier.fillMaxHeight(0.5f))
                        }

                    }

                    // Y-Axis Line
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(scaleLineWidth)
                            .background(Color.Black)
                    )

                    // graph
                    barValue.forEach {
                        Box(
                            modifier = Modifier
                                .padding(start = barGraphWidth, bottom = 5.dp)
                                .clip(CircleShape)
                                .width(barGraphWidth)
                                .fillMaxHeight(it)
                                .background(colorResource(id = R.color.teal_700))
                                .clickable {
                                    Toast
                                        .makeText(context, it.toString(), Toast.LENGTH_SHORT)
                                        .show()
                                }
                        )
                    }
                }
            }
        }
                    // X-Axis Line
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(scaleLineWidth)
                            .background(Color.Black)
                    )
                    // Scale X-Axis
        LazyRow(
            modifier = Modifier
                .padding(start = scaleYAxisWidth + barGraphWidth + scaleLineWidth, top = 4.dp)
        ) {
            items(xAxisScale) { label ->
                Text(
                    modifier = Modifier.width(barGraphWidth),
                    text = label,
                    textAlign = TextAlign.Center
                )
            }
        }

                }
            }
