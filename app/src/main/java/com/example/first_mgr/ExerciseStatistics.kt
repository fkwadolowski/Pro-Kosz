package com.example.first_mgr



import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase

@Entity(tableName = "exercise_statistics")
data class ExerciseStatistics(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val exerciseName: String,
    val basketsMade: Int,
    val basketShots: Int,
    val timestamp: Long // Store the timestamp as a Unix timestamp
)
@Dao
abstract class ExerciseStatsDao {

    @Insert
    abstract  fun insert(exerciseStats: ExerciseStatistics)

    @Query(value = "SELECT * FROM exercise_statistics")
    abstract  fun getAllExerciseStats(): List<ExerciseStatistics>
    @Query("DELETE FROM exercise_statistics")
    abstract fun clearAll()

}


@Database(entities = [ExerciseStatistics::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun exerciseStatsDao(): ExerciseStatsDao
}