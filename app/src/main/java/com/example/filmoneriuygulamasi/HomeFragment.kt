package com.example.filmoneriuygulamasi

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.filmoneriuygulamasi.adapter.MovieSuggestionsAdapter
import com.example.filmoneriuygulamasi.databinding.FragmentHomeBinding
import com.example.filmoneriuygulamasi.network.ApiClient
import com.example.filmoneriuygulamasi.network.MovieSuggestResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var movieSuggestionsAdapter: MovieSuggestionsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Binding başlatılır
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        // RecyclerView için GridLayoutManager ayarlanır
        binding.recyclerViewMovieSuggestions.layoutManager = GridLayoutManager(context, 3)

        // Film önerilerini API'den yükleme fonksiyonu çağrılır
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
                    binding.recyclerViewMovieSuggestions.adapter = movieSuggestionsAdapter
                } else {
                    Log.e("HomeFragment", "Movie Suggestions Error: ${response.code()} - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<MovieSuggestResponse>, t: Throwable) {
                Log.e("HomeFragment", "Movie Suggestions Failure: ${t.message}")
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Binding'i serbest bırak
    }
}
