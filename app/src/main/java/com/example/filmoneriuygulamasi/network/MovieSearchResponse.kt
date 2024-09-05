package com.example.filmoneriuygulamasi.network

data class MovieSearchResponse(
    val success: Boolean,
    val result: List<MovieSearchResult>
)

data class MovieSearchResult(
    val Title: String,
    val Year: String,
    val imdbID: String,
    val Type: String,
    val Poster: String
)

