package com.example.first_mgr

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import java.io.File
import java.io.IOException

@Suppress("DEPRECATION")
class DrawingFragment : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences
    private var drawingCount: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        // Initialize shared preferences
        sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)

        // Check if there are saved drawings and set drawingCount accordingly
        val savedDrawingFileNames = getSavedDrawingFileNames()
        if (savedDrawingFileNames.isNotEmpty()) {
            // Find the highest drawing number and increment it
            drawingCount = savedDrawingFileNames.mapNotNull { it.substringAfterLast('_').substringBeforeLast('.') }.maxOrNull()?.toIntOrNull()?.plus(1) ?: 1
        }

        // Load the drawing count from shared preferences
        drawingCount = sharedPreferences.getInt(KEY_DRAWING_COUNT, drawingCount)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_drawing, container, false)

        // Find the DrawingView
        val drawingView = view.findViewById<DrawingView>(R.id.drawing_view)

        // Find the "Save" button and set a click listener
        val saveButton = view.findViewById<Button>(R.id.btn_save)
        saveButton.setOnClickListener {
            saveDrawing(drawingView)
        }
        val clearButton = view.findViewById<Button>(R.id.btn_clear)
        clearButton.setOnClickListener {
            drawingView.clear()
        }
        val savedButton = view.findViewById<Button>(R.id.btn_saved)
        savedButton.setOnClickListener {
            // Navigate to the SavedPhotosFragment
            findNavController().navigate(R.id.action_drawingFragment_to_savedPhotosFragment)
        }
        val changeBackgroundButton = view.findViewById<Button>(R.id.btn_change_background)
        changeBackgroundButton.setOnClickListener {
            drawingView.changeBackground()
        }
        return view
    }

    private fun saveDrawing(drawingView: DrawingView) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Zapisz rysunek")

        val input = EditText(requireContext())
        input.inputType = InputType.TYPE_CLASS_TEXT
        input.hint = "Wpisz nazwę rysunku"
        builder.setView(input)

        builder.setPositiveButton("Zapisz") { dialog, which ->
            val drawingName = input.text.toString()

            // Define a regular expression to allow only letters, digits, underscores, and spaces
            val regex = Regex("^[a-zA-Z0-9_ ]+$")

            if (drawingName.isNotBlank() && regex.matches(drawingName)) {
                // Construct the file name based on the user-entered drawing name
                val fileName = "$drawingName.png"

                val bitmap = Bitmap.createBitmap(
                    drawingView.width,
                    drawingView.height,
                    Bitmap.Config.ARGB_8888
                )
                val canvas = Canvas(bitmap)
                drawingView.draw(canvas)

                try {
                    val fileOutputStream = requireContext().openFileOutput(fileName, Context.MODE_PRIVATE)
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
                    fileOutputStream.close()

                    // Clear the drawing view
                    drawingView.clear()

                    // Increment drawingCount after successful save
                    drawingCount++

                    // Save the updated drawing count to shared preferences
                    sharedPreferences.edit().putInt(KEY_DRAWING_COUNT, drawingCount).apply()

                    Toast.makeText(requireContext(), "Rysunek zapisano jako: $fileName", Toast.LENGTH_SHORT).show()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            } else {
                Toast.makeText(requireContext(), "Wprowadź nazwę bez znaków specjalnych", Toast.LENGTH_SHORT).show()
            }
        }

        builder.setNegativeButton("Anuluj") { dialog, which ->
            dialog.cancel()
        }

        builder.show()
    }






    companion object {
        private const val KEY_DRAWING_COUNT = "drawing_count"
    }
     fun deleteImage(imageNumber: Int) {
        Log.d("DrawingFragment", "Deleting image with number $imageNumber")
        // Construct the file name based on the image number
        val fileName = "Drawing_$imageNumber.png"
        val file = File(requireContext().filesDir, fileName)
        if (file.exists()) {
            file.delete()
            Log.d("DrawingFragment", "Image deleted successfully")
        }
    }
    private fun getSavedDrawingFileNames(): List<String> {
        val directory = requireContext().filesDir
        val files = directory.list { _, name -> name.endsWith(".png") } // Filter for .png files
        return files?.toList() ?: emptyList()
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_drawing_game_info, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_item_draw_info -> {
                showInfoDialog()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
    private fun showInfoDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Informacje")
        builder.setMessage(" Narysuj zagrywkę i zapisz ją na później, guziki kolejno odpowiedzialne są za:\nczyszczenie ekranu\nzapisywanie obrazu\nmenu zapisanych obrazów\nzmiana wyglądu boiska")

        builder.setPositiveButton("OK") { dialog, which ->
            // Handle positive button click (if needed)
        }

        builder.show()
    }
}
