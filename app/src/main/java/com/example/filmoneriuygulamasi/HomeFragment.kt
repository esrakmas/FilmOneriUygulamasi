package com.example.filmoneriuygulamasi

import MovieSuggestionsAdapter
import TopImdbMoviesAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmoneriuygulamasi.network.ApiClient
import com.example.filmoneriuygulamasi.network.MovieSuggestResponse
import com.example.filmoneriuygulamasi.network.TopImdbMoviesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private lateinit var movieSuggestionsAdapter: MovieSuggestionsAdapter
    private lateinit var topImdbMoviesAdapter: TopImdbMoviesAdapter

    private lateinit var recyclerViewMovieSuggestions: RecyclerView
    private lateinit var recyclerViewImdbTopMovies: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // RecyclerView tanımları
        recyclerViewMovieSuggestions = view.findViewById(R.id.recyclerViewMovieSuggestions)
        recyclerViewImdbTopMovies = view.findViewById(R.id.recyclerViewImdbTopMovies)

        // GridLayoutManager ve Adapter ayarları
        recyclerViewMovieSuggestions.layoutManager = GridLayoutManager(context, 3)
        recyclerViewImdbTopMovies.layoutManager = GridLayoutManager(context, 3)

        // API'den veri yükleme fonksiyonları
        loadMovieSuggestions()
        loadTopImdbMovies()

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
                    movieSuggestionsAdapter = MovieSuggestionsAdapter(movieSuggestions)
                    recyclerViewMovieSuggestions.adapter = movieSuggestionsAdapter
                }
            }

            override fun onFailure(call: Call<MovieSuggestResponse>, t: Throwable) {
                // Hata durumu
            }
        })
    }

    private fun loadTopImdbMovies() {
        val call = ApiClient.movieApiService.moviesImdb(
            contentType = "application/json",
            authorization = "apikey 0zXunVCs4qN1zD5YpNZ88V:7kztaQJhg1xKov9tvLjaGZ"
        )

        call.enqueue(object : Callback<TopImdbMoviesResponse> {
            override fun onResponse(call: Call<TopImdbMoviesResponse>, response: Response<TopImdbMoviesResponse>) {
                if (response.isSuccessful) {
                    val topImdbMovies = response.body()?.result ?: emptyList()
                    topImdbMoviesAdapter = TopImdbMoviesAdapter(topImdbMovies)
                    recyclerViewImdbTopMovies.adapter = topImdbMoviesAdapter
                }
            }

            override fun onFailure(call: Call<TopImdbMoviesResponse>, t: Throwable) {
                // Hata durumu
            }
        })
    }
}
