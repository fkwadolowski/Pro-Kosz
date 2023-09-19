package com.example.first_mgr.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.first_mgr.R
import com.example.first_mgr.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            val styledText = """
            <b>Witaj w Pro-Kosz!</b><br><br>

           Oto główne funkcje, które pomogą Ci zostać lepszym koszykarzem:<br><br>

            <b>1. Treningi:</b> Spis ćwiczeń, które pomogą Ci wnieść Twoją grę na nowy poziom.<br><br>

            <b>2. Wyniki NBA:</b> Sprawdź wyniki drużyn z najlepszej ligi świata.<br><br>

            <b>3. Notatnik:</b> Zapisuj swoje wyniki i analizuj co wymaga poprawy.<br><br>

            <b>4. Gry treningowe:</b> Ćwicz przy użyciu prostych gier aby pozbawić trening monotonii .<br><br>

            <b>5. Tablica:</b> Narysuj taktykę, która pomoże ci zrozumieć założenia koszykówki.<br><br>

            Powodzenia w byciu lepszym koszykarzem!
        """.trimIndent()

            // Apply HTML formatting for bold text and line breaks
            textView.text = HtmlCompat.fromHtml(styledText, HtmlCompat.FROM_HTML_MODE_LEGACY)

            // Apply custom text size and color
            textView.textSize = 18f // Change the text size as needed
            textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))

            // Add padding or margin if desired
            textView.setPadding(16, 16, 16, 16) // In pixels, adjust as needed
        }
        return root
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}