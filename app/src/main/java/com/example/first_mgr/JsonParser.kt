package com.example.first_mgr

import org.json.JSONException
import org.json.JSONObject

class JsonParser {

     fun parseJsonResponse(responseBody: String): List<String> {
        val tweetsList = mutableListOf<String>()

        try {
            val jsonObject = JSONObject(responseBody)
            val jsonArray = jsonObject.getJSONArray("data")

            for (i in 0 until jsonArray.length()) {
                val tweetObject = jsonArray.getJSONObject(i)
                val text = tweetObject.getString("text")
                tweetsList.add(text)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return tweetsList
    }
}
