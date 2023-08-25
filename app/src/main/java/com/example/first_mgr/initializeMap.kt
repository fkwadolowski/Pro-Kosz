//package com.example.first_mgr
//import android.content.Context
//import com.google.android.gms.maps.GoogleMap
//import com.google.android.gms.maps.model.LatLng
//import com.google.android.gms.maps.model.MarkerOptions
//import com.google.android.libraries.places.api.Places
//import com.google.android.libraries.places.api.model.Place
//import com.google.android.libraries.places.api.model.Place.Field
//import com.google.android.libraries.places.api.model.Place.Type
//import com.google.android.libraries.places.api.net.PlacesClient
//
//fun initializeMap(context: Context, key: String, map: GoogleMap) {
//    // Initialize the Places SDK
//    Places.initialize(context, key)
//
//    // Create a PlacesClient instance
//    val placesClient = Places.createClient(context)
//
//    // Set initial map properties
//    map.moveCamera(com.google.android.gms.maps.CameraUpdateFactory.newLatLngZoom(LatLng(37.4419, -122.1419), 15f))
//    map.mapType = GoogleMap.MAP_TYPE_NORMAL
//
//    // Define the Place types to search for
//    val placeTypes = listOf(Place.Type.PARK, Place.Type.STADIUM, Place.Type.GYM)
//
//    // Create a PlacesSearchRequest
//    val request = com.google.android.libraries.places.api.net.FindPlaceRequest.builder()
//        .locationBias(com.google.android.libraries.places.api.model.LocationBias.builder().build())
//        .typeFilter(com.google.android.libraries.places.api.model.TypeFilter.inclusive(placeTypes))
//        .setQuery("boisko")
//        .build()
//
//    // Perform a nearby search
//    placesClient.findPlace(request)
//        .addOnSuccessListener { response ->
//            for (place in response.placeLikelihoods) {
//                val marker = map.addMarker(
//                    MarkerOptions()
//                        .position(place.place.latLng!!)
//                        .title(place.place.name!!)
//                        .snippet(place.place.address!!)
//                )
//            }
//        }
//        .addOnFailureListener { exception ->
//            // Handle any errors
//        }
//}
