package com.example.first_mgr

import android.app.AlertDialog
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File

@Suppress("DEPRECATION")
class SavedPhotosFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var imageView: ImageView
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.saved_photos_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_item_option1 -> {
                showPopupMenu()
                return true
            }
            R.id.menu_item_option2 -> {
                showDeleteAllConfirmationDialog()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

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
    private fun showPopupMenu() {
        val fileNames = getSavedDrawingFileNames().toTypedArray()

        AlertDialog.Builder(requireContext())
            .setTitle("Select a drawing to delete")
            .setItems(fileNames) { _, which ->
                val selectedFileName = fileNames[which]
                deleteDrawing(selectedFileName)
            }
            .show()
    }

    private fun deleteDrawing(fileName: String) {
        val fileToDelete = File(requireContext().filesDir, fileName)

        if (fileToDelete.exists()) {
            val deleted = fileToDelete.delete()

            findNavController().navigate(R.id.savedPhotosFragment)
        }
    }
    private fun deleteAllDrawings() {
        val fileNames = getSavedDrawingFileNames()
        for (fileName in fileNames) {
            deleteDrawing(fileName)
        }
        findNavController().navigate(R.id.savedPhotosFragment)
    }
    private fun showDeleteAllConfirmationDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Potwierdź usunięcie")
            .setMessage("Czy jesteś pewny że chcesz usunąć wszystkie rysunki?")
            .setPositiveButton("Tak") { dialog, _ ->
                // User clicked Yes, delete all drawings
                deleteAllDrawings()
                dialog.dismiss()
            }
            .setNegativeButton("Nie") { dialog, _ ->
                // User clicked No, do nothing
                dialog.dismiss()
            }
            .show()
    }

    companion object {
        private const val TAG = "SavedPhotosFragment"
    }
}
