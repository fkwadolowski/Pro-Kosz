package com.example.first_mgr

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.first_mgr.Constants.preconfiguredNames
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
            } } }
    @Composable
    fun BasketCounterScreen(exerciseStatsDao: ExerciseStatsDao) {
        var basketsMade by remember { mutableStateOf(0) }
        var basketShots by remember { mutableStateOf(0) }
        var isSaveButtonClicked by remember { mutableStateOf(false) }
        var selectedExerciseName by remember { mutableStateOf(preconfiguredNames[0]) }
        var selectedText by remember { mutableStateOf(preconfiguredNames[0]) }
        Demo_ExposedDropdownMenuBox(selectedText) { selectedValue ->
            selectedExerciseName = selectedValue
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "$basketsMade / $basketShots",
                modifier = Modifier.padding(bottom = 16.dp),
                fontSize = 24.sp
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {
                        basketsMade++
                        basketShots++
                    },
                    colors = ButtonDefaults.buttonColors(containerColor =Color(0xFF009688)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                ) {
                    Text(text = "Rzut Trafiony", fontSize = 20.sp)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = {
                        basketShots++
                    },
                    colors = ButtonDefaults.buttonColors(containerColor =Color(0xFF009688)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                ) {
                    Text(text = "Rzut Chybiony", fontSize = 20.sp)
                } }
            Spacer(modifier = Modifier.height(16.dp))
            if (isSaveButtonClicked) {
                val exerciseStats = ExerciseStatistics(
                    exerciseName = selectedExerciseName,
                    basketsMade = basketsMade,
                    basketShots = basketShots,
                    timestamp = System.currentTimeMillis()
                )
                SaveExerciseStats(exerciseStatsDao, exerciseStats)
                basketsMade = 0
                basketShots = 0
                isSaveButtonClicked = false
            } else {
                Button(
                    onClick = {
                        isSaveButtonClicked = true
                    },
                    colors = ButtonDefaults.buttonColors(containerColor =Color(0xFFc7b214)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                ) {
                    Text(text = "Zapisz Statystyki", fontSize = 20.sp)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    val intent = Intent(this@NotatnikActivity, SavedProgressActivity::class.java)
                    startActivity(intent)
                },
                colors = ButtonDefaults.buttonColors(containerColor =Color(0xFF009688)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
            ) {
                Text(text = "Zobacz Statystyki", fontSize = 20.sp)
            }
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        CoroutineScope(Dispatchers.IO).launch {
                            exerciseStatsDao.deleteLastEntry()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor =Color(0xFFc7b214)),
                    modifier = Modifier
                        .height(60.dp)
                ) {
                    Text(text = "Usuń Ostatni Wpis", fontSize = 19.sp)
                }
                Button(
                    onClick = {
                        basketsMade = 0
                        basketShots = 0
                    },
                    colors = ButtonDefaults.buttonColors(containerColor =Color(0xFFc7b214)),
                    modifier = Modifier
                        .height(60.dp)
                ) {
                    Text(text = "Zresetuj Kosze", fontSize = 19.sp)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
    @Composable
    fun SaveExerciseStats(exerciseStatsDao: ExerciseStatsDao, exerciseStats: ExerciseStatistics) {
        LaunchedEffect(exerciseStats) {
            withContext(Dispatchers.IO) {
                exerciseStatsDao.insert(exerciseStats)
            }
        }
    }
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Demo_ExposedDropdownMenuBox(
        selectedText: String,
        onSelectedTextChange: (String) -> Unit
    ) {
        val Exercisetype = arrayOf("dystans", "pół-dystans", "trumna", "dwutakty")
        var expanded by remember { mutableStateOf(false) }
        var selectedItem by remember { mutableStateOf(selectedText) }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .padding(32.dp)
            ) {
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = {
                        expanded = it
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextField(
                        colors = ExposedDropdownMenuDefaults.textFieldColors(focusedIndicatorColor= Color(0xFF009688)),
                        value = selectedItem,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Exercisetype.forEach { item ->
                            DropdownMenuItem(
                                text = { Text(text = item, modifier = Modifier.align(Alignment.CenterHorizontally)) },
                                onClick = {
                                    selectedItem = item
                                    onSelectedTextChange(item)
                                    expanded = false
                                } ) } } } } } } }



