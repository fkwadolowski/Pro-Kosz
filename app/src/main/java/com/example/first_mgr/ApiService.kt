package com.example.first_mgr
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface BallDontLieApiService {
    @GET("games")
    suspend fun getGames(
        @Query("seasons[]") season: Int,
        @Query("team_ids[]") teamId: Int,
        @Query("per_page") perPage: Int
    ): Response<GamesResponse>
}

object ApiService {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://balldontlie.io/api/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient) // Set the OkHttpClient with the logging interceptor
        .build()

    val apiService: BallDontLieApiService = retrofit.create(BallDontLieApiService::class.java)
}
