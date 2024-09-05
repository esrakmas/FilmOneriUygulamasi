package com.example.filmoneriuygulamasi.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object ApiClient {
    private const val BASE_URL = "https://api.collectapi.com/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create()) //metin
        .addConverterFactory(GsonConverterFactory.create()) //json
        .build()

    val movieApiService: MovieApiService = retrofit.create(MovieApiService::class.java)
}