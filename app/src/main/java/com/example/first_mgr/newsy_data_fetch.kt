package com.example.first_mgr

import com.example.first_mgr.BallDontLieApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class newsy_data_fetch {
    suspend fun fetchGamesFromApi(teamFullName: String, season: Int): List<Game> {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://balldontlie.io/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(BallDontLieApiService::class.java)
        // Replace the values below with actual season and teamId you want to query

        // Find the team ID corresponding to the selected team full name
        val teamId = getTeamIdByFullName(teamFullName)

        if (teamId == -1) {
            // If team ID is not found, return an empty list
            return emptyList()
        }

        val perPage = 82

        return try {
            val response = service.getGames(season, teamId, perPage)
            if (response.isSuccessful) {
                val gamesResponse = response.body()
                val games = gamesResponse?.data ?: emptyList()

                // Filter out games with null dates (failed to parse)
                val filteredGames = games.filter { it.formattedDate != null }

                // Sort the games by date (oldest to newest)
                val sortedGames = filteredGames.sortedBy { it.formattedDate }

                sortedGames
            } else {
                println("Error: ${response.message()}")
                emptyList()
            }
        } catch (e: Exception) {
            println("Error: ${e.message}")
            emptyList()
        }
    }

        private fun getTeamIdByFullName(teamFullName: String): Int {
        val teamIdMap = mapOf(
            "Atlanta Hawks" to 1,
            "Boston Celtics" to 2,
            "Brooklyn Nets" to 3,
            "Charlotte Hornets" to 4,
            "Chicago Bulls" to 5,
            "Cleveland Cavaliers" to 6,
            "Dallas Mavericks" to 7,
            "Denver Nuggets" to 8,
            "Detroit Pistons" to 9,
            "Golden State Warriors" to 10,
            "Houston Rockets" to 11,
            "Indiana Pacers" to 12,
            "LA Clippers" to 13,
            "Los Angeles Lakers" to 14,
            "Memphis Grizzlies" to 15,
            "Miami Heat" to 16,
            "Milwaukee Bucks" to 17,
            "Minnesota Timberwolves" to 18,
            "New Orleans Pelicans" to 19,
            "New York Knicks" to 20,
            "Oklahoma City Thunder" to 21,
            "Orlando Magic" to 22,
            "Philadelphia 76ers" to 23,
            "Phoenix Suns" to 24,
            "Portland Trail Blazers" to 25,
            "Sacramento Kings" to 26,
            "San Antonio Spurs" to 27,
            "Toronto Raptors" to 28,
            "Utah Jazz" to 29,
            "Washington Wizards" to 30
        )

        return teamIdMap[teamFullName] ?: -1
    }
}