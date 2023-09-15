package com.example.first_mgr

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import java.io.IOException

class DrawingFragment : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences
    private var drawingCount: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize shared preferences
        sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)

        // Load the drawing count from shared preferences
        drawingCount = sharedPreferences.getInt(KEY_DRAWING_COUNT, 1)
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

        return view
    }

    private fun saveDrawing(drawingView: DrawingView) {
        if (drawingCount <= 50) {
            val bitmap = Bitmap.createBitmap(
                drawingView.width,
                drawingView.height,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            drawingView.draw(canvas)

            val fileName = "Drawing_$drawingCount.png"

            try {
                val fileOutputStream = requireContext().openFileOutput(fileName, Context.MODE_PRIVATE)
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
                fileOutputStream.close()

                // Increment drawingCount after successful save
                drawingCount++

                // Save the updated drawing count to shared preferences
                sharedPreferences.edit().putInt(KEY_DRAWING_COUNT, drawingCount).apply()

                Toast.makeText(requireContext(), "Drawing saved as $fileName", Toast.LENGTH_SHORT).show()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } else {
            Toast.makeText(requireContext(), "You've reached the maximum limit for drawings.", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val KEY_DRAWING_COUNT = "drawing_count"
    }
}
