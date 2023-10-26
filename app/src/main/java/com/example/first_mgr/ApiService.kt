package com.example.first_mgr
import retrofit2.Response
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


