package com.example.first_mgr

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

data class Exercise(
    val category: String,
    val exerciseName: String,
    val duration: String,
    val opis: String,
    val imageFileName: String
)

class print_cwiczenia_1 : AppCompatActivity() {
    @SuppressLint("SetTextI18n", "DiscouragedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_print_cwiczenia1)
        val exerciseName = intent.getStringExtra("exerciseName")
        val duration = intent.getStringExtra("duration")
        val opis = intent.getStringExtra("opis")
        val imageFileName = intent.getStringExtra("imageFileName")

        val nazwacw: TextView = findViewById(R.id.nazwacw)
        val czascw: TextView = findViewById(R.id.czascw)
        val opiscw: TextView = findViewById(R.id.opiscw)
        val imageView: ImageView = findViewById(R.id.exerciseImage)

        nazwacw.text = exerciseName
        czascw.text = "Czas wykonywania Ćwiczenia: $duration"
        opiscw.text = opis

        val resourceId = resources.getIdentifier(imageFileName, "drawable", packageName)
        Glide.with(this)
            .load(resourceId)
            .into(imageView)
    }
}
