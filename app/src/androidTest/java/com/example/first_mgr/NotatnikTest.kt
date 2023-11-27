package com.example.first_mgr

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class NotatnikTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<NotatnikActivity>()


    @Test
    fun test_notatnik_inside() {
        composeTestRule.onNodeWithText("Rzut Trafiony").performClick()
        composeTestRule.onNodeWithText("1 / 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("Zresetuj Kosze").performClick()
        composeTestRule.onNodeWithText("0 / 0").assertIsDisplayed()
        composeTestRule.onNodeWithText("Rzut Chybiony").performClick()
        composeTestRule.onNodeWithText("0 / 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("dystans").performClick()
        composeTestRule.onNodeWithText("pół-dystans").performClick()
        composeTestRule.onNodeWithText("pół-dystans").assertIsDisplayed()
        composeTestRule.onNodeWithText("pół-dystans").performClick()
        composeTestRule.onNodeWithText("trumna").performClick()
        composeTestRule.onNodeWithText("trumna").assertIsDisplayed()
        composeTestRule.onNodeWithText("trumna").performClick()
        composeTestRule.onNodeWithText("dwutakty").performClick()
        composeTestRule.onNodeWithText("dwutakty").assertIsDisplayed()
        val appDatabase = Room.inMemoryDatabaseBuilder(
            composeTestRule.activity,
            AppDatabase::class.java
        ).build()
        val exerciseStatsDao = appDatabase.exerciseStatsDao()
        val stats = ExerciseStatistics(
            exerciseName = "trumna",
            basketsMade = 10,
            basketShots = 15,
            timestamp = System.currentTimeMillis()
        )
        exerciseStatsDao.insert(stats)
        val allStats = exerciseStatsDao.getAllExerciseStats()
        assert(allStats.isNotEmpty())
        exerciseStatsDao.deleteLastEntry()
        val updatedStats = exerciseStatsDao.getAllExerciseStats()
        assert(updatedStats.isEmpty())
        appDatabase.close()
        composeTestRule.onNodeWithText("Zobacz Statystyki").performClick()
        composeTestRule.onNodeWithText("Średnia skuteczność danego dnia").assertIsDisplayed()
    }
}



