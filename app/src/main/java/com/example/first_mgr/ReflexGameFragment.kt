package com.example.first_mgr

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

@Suppress("DEPRECATION")
class ReflexGameFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                setHasOptionsMenu(true)
                ReflexGameContent() } } }
    @Composable
    fun ReflexGameContent() {
        var gameStarted by remember { mutableStateOf(false) }
        var greenScreenVisible by remember { mutableStateOf(true) }
        var startTime by remember { mutableStateOf(0L) }
        var endTime by remember { mutableStateOf(0L) }
        var timeToClick by remember { mutableStateOf(0L) }
        var lowestTime by remember { mutableStateOf(Long.MAX_VALUE) }
        var gameCount by remember { mutableStateOf(0) }
        val handler = remember { Handler(Looper.getMainLooper()) }
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (gameStarted) {
                    if (greenScreenVisible) {
                        startTime = 0L
                        endTime = 0L
                        startTime = System.nanoTime()
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Green)
                                .clickable {
                                    gameCount++
                                    endTime = System.nanoTime()
                                    timeToClick = (endTime - startTime)
                                    if (timeToClick < lowestTime) {
                                        lowestTime = timeToClick }
                                    gameStarted = false
                                    greenScreenVisible = false },
                            contentAlignment = Alignment.Center) {
                            Text(
                                text = "kliknij aby zakończyć!",
                                style = TextStyle(fontSize = 20.sp)) } } else {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Red),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "przyszykuj się do kliknięcia!",
                                style = TextStyle(fontSize = 20.sp)
                            ) } } } else {
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        colors = ButtonDefaults.buttonColors(containerColor =Color(0xFFc7b214)),
                        onClick = {
                            gameStarted = true
                            greenScreenVisible = false
                            handler.postDelayed({
                                greenScreenVisible = true
                                startTime = 0L
                            }, (1..30).random() * 1000L)
                        },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                            .size(200.dp)
                    ) {
                        Text(text = "Zacznij grę ") }
                    Spacer(modifier = Modifier.height(16.dp))
                    val seconds = lowestTime / 1_000_000_000
                    val millis = (lowestTime / 1_000_000) % 1_000
                    val formattedSeconds = String.format("%02d", seconds)
                    val lastSeconds = timeToClick / 1_000_000_000
                    val lastMillis = (timeToClick / 1_000_000) % 1_000
                    val lastFormattedSeconds = String.format("%02d", lastSeconds)
                    if (gameCount >= 1) {
                        Text(
                            text = "Najniższy czas: $formattedSeconds:$millis",
                            style = TextStyle(fontSize = 18.sp)
                        )
                        Text(
                            text = "Ostatni czas: $lastFormattedSeconds:$lastMillis",
                            style = TextStyle(fontSize = 18.sp)
                        ) }} } }
        @Preview
        @Composable
        fun PreviewReflexGameContent() {
            ReflexGameContent() }
    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_drawing_game_info, menu)
        super.onCreateOptionsMenu(menu, inflater) }
    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_item_draw_info -> {
                showInfoDialog()
                return true }
            else -> return super.onOptionsItemSelected(item) } }
    private fun showInfoDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Informacje")
        builder.setMessage(" naciśnij ekran kiedy zaświeci się na zielono by wygrać. Aby dodać trudności temu zadaniu skup się na innej czynności koszykarskiej aby rozwijać podzielność uwagi")
        builder.setPositiveButton("OK") { dialog, which ->
        }
        builder.show() } }



