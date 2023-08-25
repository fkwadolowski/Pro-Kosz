package com.example.first_mgr
import android.net.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class Game(
    val id: Int,
    val date: String,
    val home_team: Team,
    val home_team_score: Int,
    val period: Int,
    val postseason: Boolean,
    val season: Int,
    val status: String,
    val time: String,
    val visitor_team: Team,
    val visitor_team_score: Int
) {
    // Convert the date string to a Date object
    val formattedDate: Date?
        get() = try {
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).parse(date)
        } catch (e: ParseException) {
            null
        }
}

data class Team(
    val id: Int,
    val abbreviation: String,
    val city: String,
    val conference: String,
    val division: String,
    val full_name: String,
    val name: String
)
