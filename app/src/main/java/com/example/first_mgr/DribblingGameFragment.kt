package com.example.first_mgr

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DribblingGameFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                DribblingGameScreen()
            }
        }
    }

    @Composable
    fun DribblingGameScreen() {
        var selectedDribbling by remember { mutableStateOf(setOf(
            "Mocny kozioł lewą ręką", "Mocny kozioł prawą ręką", "Crossover", "kozioł crossover kozioł",
            "pod jedną nogą", "ósemka", "ósemka odwrócona", "pomiędzy nogami jednym kozłem",
            "crossover prawą ręką", "crossover lewą ręką", "za plecami",
            "niski kozioł lewą ręką", "niski kozioł prawą ręką","pomiędzy nogami jednym kozłem od tyłu"
        )) }
        var numberOfMinutes by remember { mutableStateOf(1) }
        var gameStarted by remember { mutableStateOf(false) }
        val dribblingList = listOf(
            "Mocny kozioł lewą ręką", "Mocny kozioł prawą ręką", "Crossover", "kozioł crossover kozioł",
            "pod jedną nogą", "ósemka", "ósemka odwrócona", "pomiędzy nogami jednym kozłem",
            "crossover prawą ręką", "crossover lewą ręką", "za plecami",
            "niski kozioł lewą ręką", "niski kozioł prawą ręką","pomiędzy nogami jednym kozłem od tyłu"
        )
        var displayedDribbling by remember { mutableStateOf(emptyList<String>()) }

        val scope = rememberCoroutineScope()

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (!gameStarted) {
                // Checkboxes for selecting the type of dribbling
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("Select Dribbling Type:")
                    DribblingTypeRow(
                        dribblingTypes = dribblingList,
                        selectedDribbling = selectedDribbling,
                        onDribblingTypeSelected = { selectedDribbling = it }
                    )
                }

                // Input for specifying the number of minutes
                Text("Number of Minutes:")
                Slider(
                    value = numberOfMinutes.toFloat(),
                    onValueChange = { newValue ->
                        numberOfMinutes = newValue.toInt().coerceIn(1, 60)
                    },
                    valueRange = 1f..60f,
                    steps = 59,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                )
                Text(text = "Selected Minutes: $numberOfMinutes")

                // Button to start the game
                Button(
                    onClick = {
                        scope.launch {
                            gameStarted = true
                            val totalSeconds = numberOfMinutes * 60
                            displayedDribbling = emptyList() // Clear the displayed dribbling list
                            val selectedList = selectedDribbling.toList()

                            // Randomly display each selected dribbling type for 30 seconds
                            for (second in 0 until totalSeconds step 30) {
                                val randomDribbling = selectedList.random()
                                displayedDribbling = listOf(randomDribbling)
                                delay(30000L)
                            }

                            gameStarted = false
                        }
                    },
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Text("Start Game")
                }
            } else {
                // Display the selected dribbling types

                // Display the dribbling types in the middle of the screen
                DisplayedDribbling(displayedDribbling)
            }
        }
    }

    @Composable
    fun DribblingTypeRow(
        dribblingTypes: List<String>,
        selectedDribbling: Set<String>,
        onDribblingTypeSelected: (Set<String>) -> Unit
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            for (i in dribblingTypes.indices step 2) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    for (j in i until minOf(i + 2, dribblingTypes.size)) {
                        val dribblingType = dribblingTypes[j]
                        val isSelected = selectedDribbling.contains(dribblingType)

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Checkbox(
                                checked = isSelected,
                                onCheckedChange = {
                                    val newSelected = if (isSelected) {
                                        selectedDribbling - dribblingType
                                    } else {
                                        selectedDribbling + dribblingType
                                    }
                                    onDribblingTypeSelected(newSelected)
                                },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = MaterialTheme.colorScheme.primary
                                )
                            )
                            Text(text = dribblingType)
                        }
                    }
                    Spacer(modifier = Modifier.weight(1f, true))
                }
            }
        }
    }
    @Composable
    fun DisplayedDribbling(displayedDribbling: List<String>) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Current Exercise:",
                modifier = Modifier.padding(bottom = 16.dp),
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp)
            // Apply bold style
            )
            displayedDribbling.forEach { dribblingType ->
                // Animate the displayed dribbling text
                AnimatedVisibility(visible = true, enter = fadeIn(), exit = fadeOut()) {
                    Text(
                        text = dribblingType,
                        modifier = Modifier
                            .fillMaxWidth() // Take up maximum available width
                            .background(color = Color(0xFF009688), shape = RoundedCornerShape(10.dp)) // Change background color to teal_700
                            .padding(8.dp)
                            .wrapContentSize(Alignment.Center) // Center the text vertically
                        ,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontSize = 30.sp // Increase the font size
                        )
                    )
                }
            }
        }
    }
}
