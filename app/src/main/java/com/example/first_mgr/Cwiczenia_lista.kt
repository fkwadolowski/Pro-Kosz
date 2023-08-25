package com.example.first_mgr

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.gridlayout.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import com.example.first_mgr.Exercise
import com.example.first_mgr.R
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class Cwiczenia_lista : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cwiczenia_lista)

        val selectedCategory = intent.getStringExtra("category")

        // Assuming you have a list of Exercise objects for the selected category
        val exerciseList: List<Exercise> = getExercisesForSelectedCategory(selectedCategory)

        // Get the reference to the GridLayout container
        val gridLayout: GridLayout = findViewById(R.id.gridlayout)

        // Define the number of columns for the GridLayout (adjust as needed)
        val numColumns = 2

        val nazwa_nad_lista: TextView = findViewById(R.id.nazwa_nad_lista)
        nazwa_nad_lista.text = selectedCategory

        // Calculate the width of each column dynamically based on the screen width
        val screenWidth = resources.displayMetrics.widthPixels
        val columnWidth = screenWidth / numColumns

        // Iterate over the exercises list and create exercise items dynamically
        for ((index, exercise) in exerciseList.withIndex()) {
            val exerciseItem = layoutInflater.inflate(R.layout.item_exercise, null)

            // Set the exercise image and name in the layout
            val exerciseImageView: ImageView = exerciseItem.findViewById(R.id.exerciseImageView)
            val exerciseNameTextView: TextView = exerciseItem.findViewById(R.id.exerciseNameTextView)

            // Set the exercise name
            exerciseNameTextView.text = exercise.exerciseName

            // Set the exercise image (You can replace placeholder_image with actual image drawable resource)
            exerciseImageView.setImageResource(R.drawable.login)

            // Create GridLayout.LayoutParams for each exercise item to dynamically set the position in the grid
            val params = GridLayout.LayoutParams()
            params.width = columnWidth
            params.height = GridLayout.LayoutParams.WRAP_CONTENT
            params.rowSpec = GridLayout.spec(index / numColumns)
            params.columnSpec = GridLayout.spec(index % numColumns)
            exerciseItem.layoutParams = params

            // Add the exercise item to the GridLayout container
            gridLayout.addView(exerciseItem)
            exerciseItem.setOnClickListener {
                // Start the print_cwiczenia_1 activity and pass the selected exercise information
                val intent = Intent(this, print_cwiczenia_1::class.java).apply {
                    putExtra("exerciseName", exercise.exerciseName)
                    putExtra("duration", exercise.duration)
                    putExtra("opis", exercise.opis)
                }
                startActivity(intent)
            }
        }
    }

    private fun getExercisesForSelectedCategory(selectedCategory: String?): List<Exercise> {
        if (selectedCategory == null) {
            // Handle the case when no category is selected (e.g., display an error message)
            return emptyList()
        }

        // Read the JSON data file containing all exercises
        val jsonString = loadJsonFromAsset("cwiczenia.json")

        // Parse the JSON data into a list of Exercise objects
        val gson = Gson()
        val allExercises = gson.fromJson(jsonString, Array<Exercise>::class.java)

        // Filter the list of exercises based on the selected category
        val exercisesForSelectedCategory = allExercises.filter { exercise ->
            exercise.category == selectedCategory
        }

        return exercisesForSelectedCategory
    }

    // Helper function to read JSON data from the asset file
    private fun loadJsonFromAsset(fileName: String): String {
        val inputStream: InputStream = assets.open(fileName)
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        return bufferedReader.use { it.readText() }
    }

}
