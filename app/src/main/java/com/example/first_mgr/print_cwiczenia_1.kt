package com.example.first_mgr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Context
import android.widget.TextView


import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

data class Exercise(
    val category: String,
    val exerciseName: String,
    val duration: String,
    val opis: String
)

private fun loadJsonFromAsset(context: Context, fileName: String): String {
    try {
        val inputStream = context.assets.open(fileName)
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        return String(buffer, Charsets.UTF_8)
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return ""
}
class print_cwiczenia_1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_print_cwiczenia1)

        // Get exercise information from the Intent extras
        val exerciseName = intent.getStringExtra("exerciseName")
        val duration = intent.getStringExtra("duration")
        val opis = intent.getStringExtra("opis")

        // Find the TextView elements
        val nazwacw: TextView = findViewById(R.id.nazwacw)
        val czascw: TextView = findViewById(R.id.czascw)
        val opiscw: TextView = findViewById(R.id.opiscw)

        // Set the values in TextViews
        nazwacw.text = exerciseName
        czascw.text = "Czas wykonywania Ćwiczenia: $duration"
        opiscw.text = opis
    }
}

