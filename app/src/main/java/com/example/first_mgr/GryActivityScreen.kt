package com.example.first_mgr
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class GryActivityScreen : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                GryActivityScreenContent()
            }
        }
    }

    @Composable
    fun GryActivityScreenContent() {
        val navController = findNavController()

        Column {
            GameCard(
                title = "Reflex Game",
                onClick = {
                    // Navigate to the Reflex Game destination
                    navController.navigate(R.id.nav_reflex_game)
                }
            )
            // Add more GameCard Composables for other mini-games
        }
    }

    @Composable
    fun GameCard(
        title: String,
        onClick: () -> Unit
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clickable(onClick = onClick), // Make the Card clickable
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Text(text = title, style = TextStyle(fontSize = 18.sp))
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Click to play", style = TextStyle(fontSize = 14.sp))
            }
        }
    }
}
