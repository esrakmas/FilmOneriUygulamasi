package com.example.filmoneriuygulamasi

data class MovieSuggestResponse(
    val result: List<MovieSuggestion>
)

data class MovieSuggestion(
    val url: String,
    val data: MovieData
)

data class MovieData(
    val lines: List<MovieLine>,
    val comment: String
)

data class MovieLine(
    val name: String,
    val year: String,
    val sty: String,
    val times: String,
    val dir: String,
    val sce: String,
    val pro: String,
    val pla: String,
    val img: String,
    val teaser: String
)

