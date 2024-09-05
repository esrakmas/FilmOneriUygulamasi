package com.example.filmoneriuygulamasi.network

data class TopImdbMoviesResponse(
    val success: Boolean,
    val result: List<TopImdbMovie>
)

data class TopImdbMovie(
    val url: String,
    val img: String,
    val year: String,
    val rate: String,
    val name: String
)

