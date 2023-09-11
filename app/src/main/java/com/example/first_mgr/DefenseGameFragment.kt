package com.example.first_mgr

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DefenseGameFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                DefenseGameContent()
            }
        }
    }

    @Composable
    fun DefenseGameContent() {
        var arrowCount by remember { mutableStateOf(1) }
        var gameStarted by remember { mutableStateOf(false) }
        var gameRunning by remember { mutableStateOf(false) }

        val scope = rememberCoroutineScope()

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (!gameStarted) {
                Slider(
                    value = arrowCount.toFloat(),
                    onValueChange = { newValue ->
                        arrowCount = newValue.toInt().coerceIn(1, 50)
                    },
                    valueRange = 1f..50f,
                    steps = 50,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                )
                Text(text = "Selected Value: $arrowCount")
                Button(
                    onClick = {
                        scope.launch {
                            delay(5000L) // Add a 2-second delay before the game starts
                            gameStarted = true
                            gameRunning = true
                            repeat(arrowCount) {
                                delay((1..10).random() * 1000L)
                                gameRunning = false
                                delay(5000L)
                                gameRunning = true
                            }
                            gameStarted = false
                        }
                    },
                    modifier = Modifier
                        .size(200.dp)
                ) {
                    Text("Start Game")
                }
            } else {
                if (gameRunning) {
                    DisplayArrow(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                            .background(color = Color.White)
                    )
                } else {
                    Text("Wait for next sign", modifier = Modifier.padding(top = 8.dp))
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.White)
                        .padding(0.dp)
                ) {
                    // You can place other composable elements here
                }
            }
        }
    }

    @Composable
    fun DisplayArrow(modifier: Modifier = Modifier) {
        val painter = painterResource(id = R.drawable.arrow_left)
        val rotateDegrees = if ((0..1).random() == 0) 0f else 180f

        Image(
            painter = painter,
            contentDescription = "Arrow",
            modifier = modifier
                .rotate(rotateDegrees)
                .fillMaxHeight()
        )
    }
}
