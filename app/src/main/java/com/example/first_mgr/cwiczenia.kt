//package com.example.first_mgr
//
//import android.content.ContentValues
//import android.content.Context
//import android.database.sqlite.SQLiteDatabase
//import android.database.sqlite.SQLiteOpenHelper
//import android.provider.BaseColumns
//import java.io.File
//import java.io.FileInputStream
//import java.io.FileReader
//
//fun readCSVFile(filePath: 'xml/roz_cw1.csv'): List<Array<String>> {
//    val data: MutableList<Array<String>> = mutableListOf()
//
//    try {
//        val fileReader = FileReader(filePath)
//        val csvReader = CSVReader(fileReader)
//
//        var line: Array<String>?
//        while (csvReader.readNext().also { line = it } != null) {
//            data.add(line!!)
//        }
//
//        csvReader.close()
//        fileReader.close()
//    } catch (e: Exception) {
//        e.printStackTrace()
//    }
//
//    return data
//}
//
//
//// ExerciseDatabaseHelper.kt - Database helper class
//class ExerciseDatabaseHelper(context: Context) :
//    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
//
//    override fun onCreate(db: SQLiteDatabase) {
//        val createTableQuery = "cwiczenia"
//
//            CREATE TABLE cwiczenia (
//                numer INTEGER ,
//                kategoria TEXT,
//                nazwa TEXT,
//                czas TEXT,
//                opis TEXT,
//                zalozenie text
//            )
//        """.trimIndent()
//        db.execSQL(createTableQuery)
//    }
//
//    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
//        // Implement database schema changes if needed
//    }
////
////    fun insertExercise(cwiczenia: cwiczenia) {
////        val values = ContentValues().apply {
////            put(COLUMN_NAME, cwiczenia.name)
////            put(COLUMN_DESCRIPTION, cwiczenia.description)
////            put(COLUMN_CATEGORY, cwiczenia.category)
////        }
////        writableDatabase.insert(TABLE_NAME, null, values)
////    }
//
//    // Add other database operations as needed
//
//    companion object {
//        private const val DATABASE_NAME = "exercise.db"
//        private const val DATABASE_VERSION = 1
//        private const val TABLE_NAME = "exercises"
//        private const val COLUMN_ID = "nr"
//        private const val COLUMN_NAME = "nazwa"
//        private const val COLUMN_DESCRIPTION = "opis"
//        private const val COLUMN_CATEGORY = "kategoria"
//    }
//    val dbHelper = ExerciseDatabaseHelper(context)
//    val db = dbHelper.writableDatabase
//
//    val exerciseValues = ContentValues().apply {
//        put(ExerciseContract.ExerciseEntry.COLUMN_NAME, "Jumping Jacks")
//        put(ExerciseContract.ExerciseEntry.COLUMN_DESCRIPTION, "Stand with your feet together and hands at your sides. Jump up while spreading your legs and raising your arms above your head.")
//        // Add other column-value pairs as needed
//    }
//
//    val newRowId = db.insert(
//        ExerciseContract.ExerciseEntry.TABLE_NAME,
//        null,
//        exerciseValues
//    )
//
//    if (newRowId == -1L) {
//        // Insertion failed
//    } else {
//        // Insertion successful, newRowId contains the ID of the newly inserted row
//    }
//}
//{
//}