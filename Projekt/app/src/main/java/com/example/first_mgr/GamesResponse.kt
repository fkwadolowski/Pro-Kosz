package com.example.first_mgr

data class GamesResponse(
    val data: List<Game>,
    val meta: Meta
)

data class Meta(
    val total_pages: Int,
    val current_page: Int,
    val next_page: Int,
    val per_page: Int,
    val total_count: Int
)

