package com.example.first_mgr

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SavedPhotosAdapter(private val fileNames: List<String>) :
    RecyclerView.Adapter<SavedPhotosAdapter.ViewHolder>() {

    private var onItemClickListener: ((String) -> Unit)? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val fileNameTextView: TextView = itemView.findViewById(R.id.file_name_text_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_saved_photo, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val fileName = fileNames[position]
        holder.fileNameTextView.text = fileName

        // Handle item click
        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(fileName)
        }
    }

    override fun getItemCount(): Int {
        return fileNames.size
    }

    // Setter for item click listener
    fun setOnItemClickListener(listener: (String) -> Unit) {
        onItemClickListener = listener
    }
}
