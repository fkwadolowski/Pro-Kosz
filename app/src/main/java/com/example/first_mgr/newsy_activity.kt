package com.example.first_mgr

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class newsy_activity : AppCompatActivity() {

    private lateinit var gameAdapter: GameAdapter
    private lateinit var recyclerViewGames: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_games_list)

        // Get references to the Spinners
        val spinnerTeam: Spinner = findViewById(R.id.spinnerTeam)
        val spinnerSeason: Spinner = findViewById(R.id.spinnerSeason)

        // Set up the Spinners
        val teamNames = listOf(
            "Atlanta Hawks", "Boston Celtics", "Brooklyn Nets", "Charlotte Hornets", "Chicago Bulls",
            "Cleveland Cavaliers", "Dallas Mavericks", "Denver Nuggets", "Detroit Pistons",
            "Golden State Warriors", "Houston Rockets", "Indiana Pacers", "LA Clippers",
            "Los Angeles Lakers", "Memphis Grizzlies", "Miami Heat", "Milwaukee Bucks",
            "Minnesota Timberwolves", "New Orleans Pelicans", "New York Knicks",
            "Oklahoma City Thunder", "Orlando Magic", "Philadelphia 76ers", "Phoenix Suns",
            "Portland Trail Blazers", "Sacramento Kings", "San Antonio Spurs", "Toronto Raptors",
            "Utah Jazz", "Washington Wizards"
        )
        val adapterTeam = ArrayAdapter(this, android.R.layout.simple_spinner_item, teamNames)
        adapterTeam.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerTeam.adapter = adapterTeam

        // Set up the seasons from 1970 to 2022
        val years = (1970..2022).toList()
        val adapterSeason = ArrayAdapter(this, android.R.layout.simple_spinner_item, years)
        adapterSeason.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerSeason.adapter = adapterSeason

        // Create the RecyclerView for the games list
        recyclerViewGames = findViewById(R.id.recyclerViewGames)
        recyclerViewGames.layoutManager = LinearLayoutManager(this)

        // Set the item selection listeners for the Spinners
        spinnerTeam.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedTeamFullName = teamNames[position]
                val selectedYear = spinnerSeason.selectedItem as Int

                // Call the fetchGamesFromApi function with the selected team and year from a coroutine
                GlobalScope.launch(Dispatchers.Main) {
                    val games = newsy_data_fetch().fetchGamesFromApi(selectedTeamFullName, selectedYear)

                    gameAdapter = GameAdapter(games)
                    recyclerViewGames.adapter = gameAdapter
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }

        spinnerSeason.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedTeamFullName = spinnerTeam.selectedItem as String
                val selectedYear = years[position]

                // Call the fetchGamesFromApi function with the selected team and year from a coroutine
                GlobalScope.launch(Dispatchers.Main) {
                    val games = newsy_data_fetch().fetchGamesFromApi(selectedTeamFullName, selectedYear)

                    gameAdapter = GameAdapter(games)
                    recyclerViewGames.adapter = gameAdapter
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }
        val teamAdapter = ArrayAdapter<String>(this, R.layout.spinner_item_layout, teamNames)
        val seasonAdapter = ArrayAdapter<Int>(this, R.layout.spinner_item_layout, years)

// Set up the Spinners
        spinnerTeam.adapter = teamAdapter
        spinnerSeason.adapter = seasonAdapter


    }
}
