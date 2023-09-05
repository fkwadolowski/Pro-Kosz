package com.example.first_mgr

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment

class ReflexGameFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                ReflexGameContent()
            }
        }
    }

    enum class DifficultyLevel {
        EASY, HARD
    }

    @Composable
    fun ReflexGameContent() {
        var selectedDifficulty by remember { mutableStateOf(DifficultyLevel.EASY) }
        var gameStarted by remember { mutableStateOf(false) }
        var greenScreenVisible by remember { mutableStateOf(true) }
        var startTime by remember { mutableStateOf(0L) }
        var endTime by remember { mutableStateOf(0L) }
        var timeToClick by remember { mutableStateOf(0L) }
        var lowestTime by remember { mutableStateOf(Long.MAX_VALUE) }
        var gameCount by remember { mutableStateOf(0) }
        val radioOptions = DifficultyLevel.values()
        val handler = remember { Handler(Looper.getMainLooper()) }
        val basicColors = arrayOf(Color.Red, Color.Blue, Color.Green, Color.Yellow, Color.Magenta, Color.Cyan, Color.Gray)
        var currentColor by remember { mutableStateOf(Color.Red) }
        var randomDelay by remember { mutableStateOf(0L) }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (gameStarted) {
                if (greenScreenVisible) {
                    startTime = 0L
                    endTime=0L
                        startTime = System.nanoTime()
                    // Display the green screen when it's time
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Green)
                            .clickable {
                                gameCount++
                                endTime = System.nanoTime()
                                timeToClick = (endTime - startTime)
                                if (timeToClick < lowestTime) {
                                    lowestTime = timeToClick
                                }
                                gameStarted = false
                                greenScreenVisible = false
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        // Calculate the time taken to click after the screen turns green
                        Text(
                            text = "Click to stop timer!",
                            style = TextStyle(fontSize = 20.sp)
                        )
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Red),
                        contentAlignment = Alignment.Center
                    ) {
                        // Screen is red
                        Text(
                            text = "Get ready to click!",
                            style = TextStyle(fontSize = 20.sp)
                        )
                    }
                }
            } else {
                // Show the UI for choosing difficulty and starting the game
                Text(
                    text = "Choose Difficulty Level",
                    style = TextStyle(fontSize = 20.sp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    radioOptions.forEach { option ->
                        RadioButton(
                            selected = (option == selectedDifficulty),
                            onClick = {
                                selectedDifficulty = option
                            }
                        )
                        Text(
                            text = option.name,
                            modifier = Modifier.padding(start = 8.dp),
                            style = TextStyle(fontSize = 18.sp)
                        )
                    }
                }
              Spacer(modifier = Modifier.height(16.dp))

                // Add a button to start the game
                Button(
                    onClick = {
                        gameStarted = true
                        greenScreenVisible = false
                        handler.postDelayed({
                            greenScreenVisible = true
                            startTime = 0L // Reset the start time
                        }, (1..5).random() * 1000L)
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(text = "Start Game")
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Display the lowest time
                LaunchedEffect(lowestTime) {
                    Log.d("ReflexGameFragment", "lowestTime: $lowestTime")
                    // This code will run after lowestTime is updated
                }

// Calculate seconds, milliseconds, and microseconds
                val seconds = lowestTime / 1_000_000_000
                val millis = (lowestTime / 1_000_000) % 1_000
                // Format seconds with leading zeros if needed
                val formattedSeconds = String.format("%02d", seconds)

                val lastSeconds = timeToClick / 1_000_000_000
                val lastMillis = (timeToClick / 1_000_000) % 1_000
                val lastFormattedSeconds = String.format("%02d", lastSeconds)

// Display the lowest time in the desired format
                if (gameCount >= 1) {
                    Text(
                        text = "Lowest Time: $formattedSeconds:$millis",
                        style = TextStyle(fontSize = 18.sp)
                    )
                    Text(
                        text = "Last Time: $lastFormattedSeconds:$lastMillis",
                        style = TextStyle(fontSize = 18.sp)
                    )
                }
            }
        }
    }

    @Preview
    @Composable
    fun PreviewReflexGameContent() {
        ReflexGameContent()
    }
}
