@file:Suppress("DEPRECATION")

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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
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
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                DribblingGameScreen()
                setHasOptionsMenu(true)
            } } }
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
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("Wybierz umiane zwody:")
                    DribblingTypeRow(
                        dribblingTypes = dribblingList,
                        selectedDribbling = selectedDribbling,
                        onDribblingTypeSelected = { selectedDribbling = it }
                    ) }
                Slider(
                    value = numberOfMinutes.toFloat(),
                    onValueChange = { newValue ->
                        numberOfMinutes = newValue.toInt().coerceIn(1, 60)
                    },
                    valueRange = 1f..60f,
                    steps = 59,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    colors = SliderDefaults.colors(
                        thumbColor = Color(0xFF009688),
                        activeTrackColor = Color(0xFF009688),
                        inactiveTrackColor = Color(0xFFBDBDBD)
                    ))
                Text(text = "Liczba minut: $numberOfMinutes")
                Button(
                    colors = ButtonDefaults.buttonColors(containerColor =Color(0xFFc7b214)),
                    onClick = {
                        scope.launch {
                            gameStarted = true
                            val totalSeconds = numberOfMinutes * 60
                            displayedDribbling = emptyList()
                            val selectedList = selectedDribbling.toList()
                            for (second in 0 until totalSeconds step 30) {
                                val randomDribbling = selectedList.random()
                                displayedDribbling = listOf(randomDribbling)
                                delay(30000L)
                            }
                            gameStarted = false
                        } },
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Text("Zacznij grę")
                } } else {
                DisplayedDribbling(displayedDribbling)
            } } }
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
                                    checkedColor = Color(0xFF009688)
                                ))
                            Text(text = dribblingType)
                        } }
                    Spacer(modifier = Modifier.weight(1f, true))
                } } } }
    @Composable
    fun DisplayedDribbling(displayedDribbling: List<String>) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Wykonuj to ćwiczenie:",
                modifier = Modifier.padding(bottom = 16.dp),
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp))
            displayedDribbling.forEach { dribblingType ->
                AnimatedVisibility(visible = true, enter = fadeIn(), exit = fadeOut()) {
                    Text(
                        text = dribblingType,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = Color(0xFFc7b214), shape = RoundedCornerShape(10.dp))
                            .padding(8.dp)
                            .wrapContentSize(Alignment.Center),
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontSize = 30.sp) ) } } }
    }    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_drawing_game_info, menu)
        Handler(Looper.getMainLooper()).post {
            val menuItem = menu.findItem(R.id.menu_item_draw_info)
            menuItem.setIcon(R.drawable.ic_information) }
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
        builder.setMessage(" wybierz ćwiczenia, kótre umiesz wykonać i dostosuj czas trwania ćwiczenia a po ro rozpoczęciu wykonuj je w kolejności wyświetlania na ekranie")
        builder.setPositiveButton("OK") { dialog, which -> }
        builder.show() } }

