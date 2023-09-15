package com.example.first_mgr

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class SavedPhotosFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var imageView: ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_saved_photos, container, false)
        recyclerView = view.findViewById(R.id.recycler_view)
        imageView = view.findViewById(R.id.image_view)

        // Set up RecyclerView
        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager

        // Fetch the list of file names from the internal storage directory where drawings are saved
        val fileNames = getSavedDrawingFileNames()

        val adapter = SavedPhotosAdapter(fileNames)
        recyclerView.adapter = adapter

        // Handle item click in RecyclerView
        adapter.setOnItemClickListener { fileName ->
            // Load and display the selected image in the ImageView
            val filePath = requireContext().filesDir.absolutePath + File.separator + fileName
            val bitmap = BitmapFactory.decodeFile(filePath)
            imageView.setImageBitmap(bitmap)

            // Show the ImageView and hide the RecyclerView
            imageView.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        }

        // Handle click on the displayed image
        imageView.setOnClickListener {
            // Hide the ImageView and show the RecyclerView
            imageView.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }

        return view
    }
private fun getSavedDrawingFileNames(): List<String> {
    val directory = requireContext().filesDir
    val files = directory.list { _, name -> name.endsWith(".png") } // Filter for .png files
    return files?.toList() ?: emptyList()
}
}
