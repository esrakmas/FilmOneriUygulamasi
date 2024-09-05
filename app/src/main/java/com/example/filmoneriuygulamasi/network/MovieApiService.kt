package com.example.filmoneriuygulamasi.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface MovieApiService {

    @GET("imdb/imdbSearchByName")
    fun imdbSearchByName(
        @Header("content-type") contentType: String,
        @Header("authorization") authorization: String,
        @Query("query") query: String
    ): Call<String>

    @GET("watching/imdb")
    fun moviesImdb(
        @Header("content-type") contentType: String,
        @Header("authorization") authorization: String
    ): Call<TopImdbMoviesResponse>

    @GET("watching/movieSuggest")
    fun movieSuggest(
        @Header("content-type") contentType: String,
        @Header("authorization") authorization: String
    ): Call<MovieSuggestResponse>
}
