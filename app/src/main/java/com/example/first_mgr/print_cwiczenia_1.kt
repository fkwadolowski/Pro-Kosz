package com.example.first_mgr

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import java.io.IOException

data class Exercise(
    val category: String,
    val exerciseName: String,
    val duration: String,
    val opis: String,
    val imageFileName: String
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
        val imageFileName = intent.getStringExtra("imageFileName")

        // Dodane logi
        Log.d("print_cwiczenia_1", "Exercise Name: $exerciseName")
        Log.d("print_cwiczenia_1", "Duration: $duration")
        Log.d("print_cwiczenia_1", "Opis: $opis")
        Log.d("print_cwiczenia_1", "Image File Name: $imageFileName")

        // Find the TextView and ImageView elements
        val nazwacw: TextView = findViewById(R.id.nazwacw)
        val czascw: TextView = findViewById(R.id.czascw)
        val opiscw: TextView = findViewById(R.id.opiscw)
        val imageView: ImageView = findViewById(R.id.exerciseImage)

        // Set the values in TextViews
        nazwacw.text = exerciseName
        czascw.text = "Czas wykonywania Ćwiczenia: $duration"
        opiscw.text = opis

        // Set the image using Glide
        val resourceId = resources.getIdentifier(imageFileName, "drawable", packageName)
        Glide.with(this)
            .load(resourceId) // Ładuje obraz z resource ID
            .into(imageView)

        // Dodane logi
        Log.d("print_cwiczenia_1", "Resource ID: $resourceId")
    }
}
