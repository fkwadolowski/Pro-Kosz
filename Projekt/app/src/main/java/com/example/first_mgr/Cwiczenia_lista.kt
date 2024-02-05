package com.example.first_mgr

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.gridlayout.widget.GridLayout
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class Cwiczenia_lista : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cwiczenia_lista)

        val selectedCategory = intent.getStringExtra("category")
        val exerciseList: List<Exercise> = getExercisesForSelectedCategory(selectedCategory)
        val gridLayout: GridLayout = findViewById(R.id.gridlayout)
        val numColumns = 2
        val nazwa_nad_lista: TextView = findViewById(R.id.nazwa_nad_lista)
        nazwa_nad_lista.text = selectedCategory
        val screenWidth = resources.displayMetrics.widthPixels
        val columnWidth = screenWidth / numColumns
        for ((index, exercise) in exerciseList.withIndex()) {
            val exerciseItem = layoutInflater.inflate(R.layout.item_exercise, null)
            val exerciseImageView: ImageView = exerciseItem.findViewById(R.id.exerciseImageView)
            val exerciseNameTextView: TextView = exerciseItem.findViewById(R.id.exerciseNameTextView)
            exerciseNameTextView.text = exercise.exerciseName
            val resourceId = resources.getIdentifier(exercise.imageFileName, "drawable", packageName)
            exerciseImageView.setImageResource(resourceId)
            val params = GridLayout.LayoutParams()
            params.width = columnWidth
            params.height = GridLayout.LayoutParams.WRAP_CONTENT
            params.rowSpec = GridLayout.spec(index / numColumns)
            params.columnSpec = GridLayout.spec(index % numColumns)
            exerciseItem.layoutParams = params
            gridLayout.addView(exerciseItem)
            exerciseItem.setOnClickListener {
                val intent = Intent(this, print_cwiczenia_1::class.java).apply {
                    putExtra("exerciseName", exercise.exerciseName)
                    putExtra("duration", exercise.duration)
                    putExtra("opis", exercise.opis)
                    putExtra("imageFileName", exercise.imageFileName)
                }
                startActivity(intent)
            } } }
    private fun getExercisesForSelectedCategory(selectedCategory: String?): List<Exercise> {
        if (selectedCategory == null) {
            return emptyList()
        }
        val jsonString = loadJsonFromAsset("cwiczenia.json")
        val gson = Gson()
        val allExercises = gson.fromJson(jsonString, Array<Exercise>::class.java)
        val exercisesForSelectedCategory = allExercises.filter { exercise ->
            exercise.category == selectedCategory
        }
        return exercisesForSelectedCategory
    }
    private fun loadJsonFromAsset(fileName: String): String {
        val inputStream: InputStream = assets.open(fileName)
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        return bufferedReader.use { it.readText() }
    }
}

