package com.example.first_mgr

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class trening_menu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trening_menu)

        val rozciaganieButton = findViewById<Button>(R.id.button)
        val rzutButton = findViewById<Button>(R.id.button4)
        val rozgrzewkaButton = findViewById<Button>(R.id.button5)
        val kozlowanieButton = findViewById<Button>(R.id.button6)
        val zagrywkiButton = findViewById<Button>(R.id.button3)
        val podawanieButton = findViewById<Button>(R.id.button2)

        rozciaganieButton.setOnClickListener {
            navigateToExercises("Rozciąganie")
        }

        rzutButton.setOnClickListener {
            navigateToExercises("Rzut")
        }

        rozgrzewkaButton.setOnClickListener {
            navigateToExercises("Rozgrzewka")
        }

        kozlowanieButton.setOnClickListener {
            navigateToExercises("Kozłowanie")
        }

        zagrywkiButton.setOnClickListener {
            navigateToExercises("Zagrywki")
        }

        podawanieButton.setOnClickListener {
            navigateToExercises("Podawanie")
        }
    }

    private fun navigateToExercises(category: String) {
        val intent = Intent(this, Cwiczenia_lista::class.java)
        intent.putExtra("category", category)
        startActivity(intent)
    }
}

