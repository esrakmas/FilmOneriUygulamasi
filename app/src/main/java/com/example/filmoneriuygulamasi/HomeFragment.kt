package com.example.filmoneriuygulamasi

import MovieSuggestionsAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmoneriuygulamasi.network.ApiClient
import com.example.filmoneriuygulamasi.network.MovieSuggestResponse

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private lateinit var movieSuggestionsAdapter: MovieSuggestionsAdapter
    private lateinit var recyclerViewMovieSuggestions: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // RecyclerView tanımları
        recyclerViewMovieSuggestions = view.findViewById(R.id.recyclerViewMovieSuggestions)


        // GridLayoutManager ve Adapter ayarları
        recyclerViewMovieSuggestions.layoutManager = GridLayoutManager(context, 3)

        // API'den veri yükleme fonksiyonları
        loadMovieSuggestions()


        return view
    }

    private fun loadMovieSuggestions() {
        val call = ApiClient.movieApiService.movieSuggest(
            contentType = "application/json",
            authorization = "apikey 0zXunVCs4qN1zD5YpNZ88V:7kztaQJhg1xKov9tvLjaGZ"
        )

        call.enqueue(object : Callback<MovieSuggestResponse> {
            override fun onResponse(call: Call<MovieSuggestResponse>, response: Response<MovieSuggestResponse>) {
                if (response.isSuccessful) {
                    val movieSuggestions = response.body()?.result ?: emptyList()
                    Log.d("HomeFragment", "Movie Suggestions: $movieSuggestions")
                    movieSuggestionsAdapter = MovieSuggestionsAdapter(movieSuggestions)
                    recyclerViewMovieSuggestions.adapter = movieSuggestionsAdapter
                } else {
                    Log.e("HomeFragment", "Movie Suggestions Error: ${response.code()} - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<MovieSuggestResponse>, t: Throwable) {
                Log.e("HomeFragment", "Movie Suggestions Failure: ${t.message}")
            }
        })
    }


}
