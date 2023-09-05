package com.example.first_mgr

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

// Define an enum for difficulty levels
enum class DifficultyLevel {
    EASY,
    HARD
}

class ReflexGameViewModel : ViewModel() {

    // Property to store the selected difficulty level
    var selectedDifficulty: DifficultyLevel by mutableStateOf(DifficultyLevel.EASY)
        private set

    // Add other properties and methods related to game state as needed
}