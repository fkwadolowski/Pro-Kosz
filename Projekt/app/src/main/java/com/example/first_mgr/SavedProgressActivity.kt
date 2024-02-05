package com.example.first_mgr

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
            val allBarValues = mutableListOf<Float>()
            groupedStats.values.forEach { statsForDay ->
                val basketsMadeSum = statsForDay.sumOf { it.basketsMade }
                val basketShotsSum = statsForDay.sumOf { it.basketShots }
                val averageBarValue = basketsMadeSum.toFloat() / basketShotsSum.toFloat()
                allBarValues.add(averageBarValue)}
            val exerciseNameFrequencies = exerciseStatsList.groupBy { it.exerciseName }
                .mapValues { it.value.size }
            LaunchedEffect(true) {
                withContext(Dispatchers.IO) {
                    val stats = exerciseStatsDao.getAllExerciseStats()
                    exerciseStatsList.addAll(stats) } }
            Column(
                modifier = Modifier.fillMaxSize()
                    .verticalScroll(rememberScrollState())) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Średnia skuteczność danego dnia",
                            textAlign = TextAlign.Center,
                            fontSize = 18.sp) }
                    if (allBarValues.isNotEmpty()) {
                        CustomChart(allBarValues, xAxisLabelsGrouped, 100)
                        Spacer(modifier = Modifier.height(16.dp)) } }
                Column(
                    modifier = Modifier.fillMaxSize().padding(16.dp)) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Rozkład różnorodności rzutów",
                            textAlign = TextAlign.Center,
                            fontSize = 18.sp) }
                    ExerciseNameDistributionChart(
                        exerciseNameFrequencies, modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)) }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center) {
                    Text(
                        text = "Historia treningów",
                        textAlign = TextAlign.Center,
                        fontSize = 18.sp) }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text("Data")
                    Text("Nazwa ćwiczenia")
                    Text("Skuteczność") }
                Column(
                    modifier = Modifier.fillMaxWidth()) {
                    exerciseStatsList.forEach { stats ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Text(formatTimestamp(stats.timestamp))
                            Text(stats.exerciseName)
                            Text("${stats.basketsMade}/${stats.basketShots}")
                        } } } } } }
    private fun formatTimestamp(timestamp: Long): String {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        return dateFormat.format(Date(timestamp)) }

    @Composable
    fun CustomChart(
        barValues: List<Float>,
        xAxisScale: List<String>,
        total_amount: Int
    ) {
        val barGraphHeight by remember { mutableStateOf(200.dp) }
        val barGraphWidth by remember { mutableStateOf(20.dp) }
        val scaleYAxisWidth by remember { mutableStateOf(50.dp) }
        val scaleLineWidth by remember { mutableStateOf(2.dp) }
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
                .horizontalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = Modifier
                    .height(barGraphHeight)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Start) {
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
                        Text(text = total_amount.toString() + "%")
                        Spacer(modifier = Modifier.fillMaxHeight()) }
                    Column(
                        modifier = Modifier.fillMaxHeight(),
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        Text(text = (total_amount / 2).toString() + "%")
                        Spacer(modifier = Modifier.fillMaxHeight(0.5f)) } }
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(scaleLineWidth)
                        .background(Color.Black))
                barValues.forEachIndexed { index, value ->
                    val adjustedValue = if (value == 1.0f) 0.93f else value
                    Column(
                        modifier = Modifier
                            .padding(start = barGraphWidth)) {
                        Text(
                            text = String.format("%.2f", value),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(top = 4.dp))
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .width(barGraphWidth)
                                .fillMaxHeight(adjustedValue)
                                .background(colorResource(id = R.color.teal_700)))
                        Text(
                            text = xAxisScale[index],
                            textAlign = TextAlign.Center) } } } } }

    @Composable
    fun ExerciseNameDistributionChart(
        exerciseNameFrequencies: Map<String, Int>,
        modifier: Modifier = Modifier) {
        val exerciseNames = exerciseNameFrequencies.keys.toList()
        val counts = exerciseNameFrequencies.values.toList()
        val colors = listOf(Color.Red, Color.Green, Color.Blue, Color.Yellow)
        val legendLabels = exerciseNames
        val data = counts.map { it.toFloat() }
        PieChartWithLegend(data, colors, legendLabels, modifier) }

    @Composable
    fun PieChartWithLegend(
        data: List<Float>,
        colors: List<Color>,
        legendLabels: List<String>,
        modifier: Modifier = Modifier) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)) {
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                val canvasSize = size.minDimension
                val radius = canvasSize / 2
                val centerX = size.width / 2
                val centerY = size.height / 2
                val total = data.sum()
                var startAngle = 0f
                data.forEachIndexed { index, value ->
                    val sweepAngle = value / total * 360f
                    val endAngle = startAngle + sweepAngle
                    drawArc(
                        color = colors[index % colors.size],
                        startAngle = startAngle,
                        sweepAngle = sweepAngle,
                        useCenter = true,
                        topLeft = Offset(centerX - radius, centerY - radius),
                        size = Size(radius * 2, radius * 2))
                    startAngle = endAngle } }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        for (i in legendLabels.indices) {
                            val color = colors[i % colors.size]
                            val label = legendLabels[i]
                            Spacer(
                                modifier = Modifier
                                    .size(20.dp)
                                    .background(color = color)
                                    .padding(end = 8.dp))
                            Text(
                                text = label,
                                color = Color.Black,
                                fontSize = 14.sp
                            ) } } } } }

