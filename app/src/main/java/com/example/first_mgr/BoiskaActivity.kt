//package com.example.first_mgr
//
//import android.os.Bundle
//import androidx.appcompat.app.AppCompatActivity
//import com.google.android.libraries.places.api.Places
//import com.google.android.libraries.places.api.model.Place
//import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest
//import kotlinx.android.synthetic.main.activity_maps.*
//
//class BoiskaActivity : AppCompatActivity() {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_maps)  // Replace with your layout resource ID
//
//        // Initialize Places SDK
//        val mapsApiKey = getMapsApiKey()
//        if (mapsApiKey.isNotEmpty()) {
//            Places.initialize(applicationContext, mapsApiKey)
//        } else {
//            // Handle error: Maps API key is missing or empty
//        }
//
//        // Example of using the Places SDK
//        val placeFields = listOf(Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG)
//        val request = FindCurrentPlaceRequest.builder(placeFields).build()
//
//        val placesClient = Places.createClient(this)
//        placesClient.findCurrentPlace(request).addOnSuccessListener { response ->
//            for (placeLikelihood in response.placeLikelihoods) {
//                val place = placeLikelihood.place
//                // Use place information (e.g., name, address, LatLng)
//            }
//        }.addOnFailureListener { exception ->
//            // Handle failure
//        }
//    }
//
//    private fun getMapsApiKey(): String {
//        val properties = Properties()
//        try {
//            val assets = assets.open("local.properties")
//            properties.load(assets)
//            return properties.getProperty("MAPS_API_KEY", "")
//        } catch (e: IOException) {
//            // Handle error reading properties file
//            return ""
//        }
//    }
//}
