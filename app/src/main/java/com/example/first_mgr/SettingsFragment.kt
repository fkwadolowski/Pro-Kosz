package com.example.first_mgr

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment


class SettingsFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        return ComposeView(requireContext()).apply {
            setContent {
                SettingsScreen()
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun SettingsScreen() {
        var clearSingleImage by remember { mutableStateOf(false) }
        var clearAllImages by remember { mutableStateOf(false) }
        var imageToDelete by remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            // Add settings options here
            SettingsOption("Cleaning Saved Drawings")

            // Show options for clearing a single image

            if (clearAllImages) {
                // Show option for clearing all images
                ClearAllImagesOption()
            }

            Button(onClick = { clearAllImages = true }) {
                Text("Clear All Images")
            }
        }
    }

    @Composable
    fun ClearAllImagesOption() {
        // Implement UI for clearing all images here
    }

    @Composable
    fun SettingsOption(optionTitle: String) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 8.dp) // Add padding
                .border(2.dp, Color.Black) // Add border
                .padding(8.dp)
            // Handle click
        ) {
            // Option title
            Text(text = optionTitle)
        }
    }
}
