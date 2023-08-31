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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
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

        setContent {
            val exerciseStatsDao = DatabaseManager.getExerciseStatisticsDao()
            val exerciseStatsList = remember { mutableStateListOf<ExerciseStatistics>() }
            val groupedStats = exerciseStatsList.groupBy { formatTimestamp(it.timestamp) }
            val xAxisLabelsGrouped = groupedStats.keys.toList()
            val aggregatedBarValues = mutableListOf<List<Float>>()
            val allBarValues = mutableListOf<Float>()
            groupedStats.values.forEach { statsForDay ->
                val basketsMadeSum = statsForDay.sumOf { it.basketsMade }
                val basketShotsSum = statsForDay.sumOf { it.basketShots }
                val averageBarValue = basketsMadeSum.toFloat() / basketShotsSum.toFloat()
                allBarValues.add(averageBarValue) // Add the value to the single list
            }

            Log.d("AggregatedBarValues", aggregatedBarValues.toString())
            Log.d("xAxisLabelsGrouped", xAxisLabelsGrouped.toString())
            // Fetch the exercise statistics from the database
            LaunchedEffect(true) {
                withContext(Dispatchers.IO) {
                    val stats = exerciseStatsDao.getAllExerciseStats()
                    exerciseStatsList.addAll(stats)
                    Log.d("SavedProgressActivity", "Fetched ${stats.size} exercise statistics")
                }
            }
            val xAxisLabels = exerciseStatsList.map { formatTimestamp(it.timestamp) }
            val barValues =
                exerciseStatsList.map { it.basketsMade.toFloat() / it.basketShots.toFloat() }
            val adjustedBarValues = barValues.map { if (it == 1.0f) 0.92f else it }
            Column(
                modifier = Modifier.fillMaxSize()
                    .verticalScroll(rememberScrollState())
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
                    Spacer(modifier = Modifier.height(150.dp))
                    Text(text = "Third Component")

                    if (allBarValues.isNotEmpty()) {
                        Log.d("CustomChart", "Creating chart for all days")
                        Log.d("CustomChart", "Values: $allBarValues")
                        CustomChart(allBarValues, xAxisLabelsGrouped, 100)
                        Spacer(modifier = Modifier.height(16.dp)) // Adds vertical spacing between components
                    }
                }
            }

                //4 part
//            Spacer(modifier = Modifier.height(150.dp))
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp)
//            ) {
//                for ((index, barValues) in aggregatedBarValues.withIndex()) {
//                    CustomChart2(exerciseStatsList, 100)
//                    Spacer(modifier = Modifier.height(16.dp)) // Adds vertical spacing between components
//                }                }
            }
        }

    private fun formatTimestamp(timestamp: Long): String {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        return dateFormat.format(Date(timestamp))
    }

    @Composable
    fun CustomChart(
        barValues: List<Float>,
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
            Row(
                modifier = Modifier
                    .height(barGraphHeight)
                    .fillMaxWidth(),
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
                // graph and X-axis labels
                barValues.forEachIndexed { index, value ->
                    val adjustedValue = if (value == 1.0f) 0.93f else value
                    Column(
                        modifier = Modifier
                            .padding(start = barGraphWidth)
                    ) {
                        // Display average bar value above the bar
                        Text(
                            text = String.format("%.2f", value),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(top = 4.dp)
                        )

                        // Graph bar
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .width(barGraphWidth)
                                .fillMaxHeight(adjustedValue)
                                .background(colorResource(id = R.color.teal_700))
                                .clickable {
                                    Toast.makeText(context, value.toString(), Toast.LENGTH_SHORT)
                                        .show()
                                }
                        )

                        // X-axis label using the date label from xAxisScale
                        Text(
                            text = xAxisScale[index],
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }

//    @Composable
//    fun CustomChart2(
//        exerciseStatsList: List<ExerciseStatistics>,
//        total_amount: Int
//    ) {
//        // BarGraph Dimensions
//        val barGraphHeight by remember { mutableStateOf(200.dp) }
//        val barGraphWidth by remember { mutableStateOf(20.dp) }
//        // Scale Dimensions
//        val scaleYAxisWidth by remember { mutableStateOf(50.dp) }
//        val scaleLineWidth by remember { mutableStateOf(2.dp) }
//
//        Column(
//            modifier = Modifier
//                .padding(50.dp)
//                .fillMaxSize(),
//            verticalArrangement = Arrangement.Top
//        ) {
//            Row(
//                modifier = Modifier
//                    .height(barGraphHeight)
//                    .fillMaxWidth(),
//                verticalAlignment = Alignment.Bottom,
//                horizontalArrangement = Arrangement.Start
//            ) {
//                // scale Y-Axis
//                Box(
//                    modifier = Modifier
//                        .fillMaxHeight()
//                        .width(scaleYAxisWidth),
//                    contentAlignment = Alignment.BottomCenter
//                ) {
//                    Column(
//                        modifier = Modifier.fillMaxHeight(),
//                        verticalArrangement = Arrangement.Bottom
//                    ) {
//                        Text(text = total_amount.toString())
//                        Spacer(modifier = Modifier.fillMaxHeight())
//                    }
//
//                    Column(
//                        modifier = Modifier.fillMaxHeight(),
//                        verticalArrangement = Arrangement.Bottom
//                    ) {
//                        Text(text = (total_amount / 2).toString())
//                        Spacer(modifier = Modifier.fillMaxHeight(0.5f))
//                    }
//                }
//
//                // Y-Axis Line
//                Box(
//                    modifier = Modifier
//                        .fillMaxHeight()
//                        .width(scaleLineWidth)
//                        .background(Color.Black)
//                )
//
//                // Graph and X-axis labels
//                exerciseStatsList.groupBy { it.exerciseName }
//                    .forEach { (exerciseName, statsForExercise) ->
//                        val barValues =
//                            statsForExercise.map { it.basketsMade.toFloat() / it.basketShots.toFloat() }
//                        val adjustedBarValues = barValues.map { if (it == 1.0f) 0.93f else it }
//
//                        Column(
//                            modifier = Modifier
//                                .padding(start = barGraphWidth)
//                        ) {
//                            // Graph bars for each exerciseName
//                            adjustedBarValues.forEach { barValue ->
//                                Box(
//                                    modifier = Modifier
//                                        .clip(CircleShape)
//                                        .width(barGraphWidth)
//                                        .fillMaxHeight(barValue)
//                                        .background(colorResource(id = R.color.teal_700))
//                                )
//                            }
//
//                            // X-axis label for each exerciseName
//                            Text(
//                                text = exerciseName,
//                                textAlign = TextAlign.Center
//                            )
//                        }
//                    }
//            }
//        }
//    }

    }