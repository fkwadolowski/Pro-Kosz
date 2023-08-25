package com.example.first_mgr

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject

class SavedProgressActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences

    companion object {
        const val PROGRESS_KEYS = "progress_keys"
        const val PROGRESS_VALUES = "progress_values"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notatnik_wyniki)



        val progressKeys = intent.getStringArrayListExtra(PROGRESS_KEYS)
        val progressValues = intent.getStringArrayListExtra(PROGRESS_VALUES) ?: emptyList()

        val tableLayout = findViewById<TableLayout>(R.id.tableLayout)

        // Create the header row with column names
        val headerRow = TableRow(this)
        val dateHeaderCell = TextView(this)
        dateHeaderCell.text = "Date"
        headerRow.addView(dateHeaderCell)

        val progressHeaderCell = TextView(this)
        progressHeaderCell.text = "Progress"
        headerRow.addView(progressHeaderCell)

        val exerciseNameHeaderCell = TextView(this)
        exerciseNameHeaderCell.text = "Exercise Name"
        headerRow.addView(exerciseNameHeaderCell)

        tableLayout.addView(headerRow)

        // Populate rows with data
        val entries = sharedPreferences.all
        for ((key, value) in entries) {
            if (key.endsWith("-date")) {
                val exerciseNameKey = key.replace("-date", "-name")
                val progressKey = key.replace("-date", "-progress")

                val date = value as String
                val exerciseName = sharedPreferences.getString(exerciseNameKey, "Unknown")
                val progressJson = JSONObject(value.toString()) // Convert progress to JSON object
                val basketsMade = progressJson.getInt("basketsMade")
                val basketShots = progressJson.getInt("basketShots")

                val row = TableRow(this)

                val dateCell = TextView(this)
                dateCell.text = date
                row.addView(dateCell)

                val progressCell = TextView(this)
                progressCell.text = "$basketsMade/$basketShots"
                row.addView(progressCell)

                val exerciseNameCell = TextView(this)
                exerciseNameCell.text = exerciseName
                row.addView(exerciseNameCell)

                tableLayout.addView(row)
            }
        }
    }
}