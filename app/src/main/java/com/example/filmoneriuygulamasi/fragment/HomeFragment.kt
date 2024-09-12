package com.example.filmoneriuygulamasi.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.filmoneriuygulamasi.adapter.MovieSearchAdapter
import com.example.filmoneriuygulamasi.adapter.MovieSuggestionsAdapter
import com.example.filmoneriuygulamasi.databinding.FragmentHomeBinding
import com.example.filmoneriuygulamasi.network.ApiClient
import com.example.filmoneriuygulamasi.network.MovieSearchResponse
import com.example.filmoneriuygulamasi.network.MovieSuggestResponse
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
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
        // Binding baslat
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        //  GridLayoutManager 3lu sutun olsun
        binding.recyclerViewMovieSuggestions.layoutManager = GridLayoutManager(context, 3)
        binding.recyclerViewMovieSearch.layoutManager =
            GridLayoutManager(context, 3) // ya da LinearLayoutManager

        binding.searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query.isNullOrEmpty()) {

                    // Arama metni temizlendiğinde veya boş olduğunda öneri listesini göster
                    binding.recyclerViewMovieSearch.visibility = View.GONE
                    binding.recyclerViewMovieSuggestions.visibility = View.VISIBLE
                    binding.textViewMovieSuggestions.visibility = View.VISIBLE

                    // Sayfayı güncelleme
                    refreshPage()

                    // Klavyeyi kapatma işlemi
                    hideKeyboard()
                } else {
                    // Arama metni mevcutsa öneri listesini gizle
                    binding.recyclerViewMovieSuggestions.visibility = View.GONE
                    binding.textViewMovieSuggestions.visibility = View.GONE
                }
                query?.let { searchMovie(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { searchMovie(it) }
                return false
            }
        })
        // Film önerilerini API'den yükleme fonksiyonu çağrılır
        loadMovieSuggestions()

        return view
    }

    private fun refreshPage() {
        // Öneri listesini tekrar yükle
        loadMovieSuggestions()
    }

    // Klavyeyi kapatma fonksiyonu
    private fun hideKeyboard() {
        val imm =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
        binding.searchView.clearFocus() // SearchView'in odaktan çıkmasını sağlar
    }

    private fun loadMovieSuggestions() {
        val call = ApiClient.movieApiService.movieSuggest(
            contentType = "application/json",
            authorization = "apikey 0a6w7cPEYljiAxvpU9vOTp:5VXrydZS4PYA6y5hvRruwI"
        )
        //yanıt
        call.enqueue(object : Callback<MovieSuggestResponse> {
            override fun onResponse(
                call: Call<MovieSuggestResponse>,
                response: Response<MovieSuggestResponse>
            ) {
                if (response.isSuccessful) {
                    val movieSuggestions = response.body()?.result ?: emptyList()
                    Log.d("HomeFragment", "Movie Suggestions: $movieSuggestions")
                    movieSuggestionsAdapter = MovieSuggestionsAdapter(movieSuggestions)
                    binding.recyclerViewMovieSuggestions.adapter = movieSuggestionsAdapter
                } else {
                    Log.e(
                        "HomeFragment",
                        "Movie Suggestions Error: ${response.code()} - ${response.message()}"
                    )
                }
            }

            override fun onFailure(call: Call<MovieSuggestResponse>, t: Throwable) {
                Log.e("HomeFragment", "Movie Suggestions Failure: ${t.message}")
            }
        })
    }

    private fun searchMovie(query: String) {

        // Arama başladığında öneri listesini gizle
        binding.recyclerViewMovieSuggestions.visibility = View.GONE
        binding.textViewMovieSuggestions.visibility = View.GONE
        binding.recyclerViewMovieSearch.visibility = View.VISIBLE  // Arama sonuçları görünür


        val apiService = ApiClient.movieApiService
        val call = apiService.imdbSearchByName(
            contentType = "application/json",
            authorization = "apikey 0a6w7cPEYljiAxvpU9vOTp:5VXrydZS4PYA6y5hvRruwI",  // Buraya kendi API anahtarınızı ekleyin
            query = query
        )

        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    val responseBody = response.body() ?: ""
                    val gson = Gson()

                    try {
                        // JSON string'i MovieSearchResponse nesnesine dönüştür
                        val movieSearchResponse =
                            gson.fromJson(responseBody, MovieSearchResponse::class.java)
                        val movieSearch = movieSearchResponse.result ?: emptyList()
                        Log.d("HomeFragment", "Movie Search: $movieSearch")

                        if (movieSearch.isNotEmpty()) {
                            binding.recyclerViewMovieSearch.visibility =
                                View.VISIBLE  // Sonuçlar varsa RecyclerView görünür
                            binding.recyclerViewMovieSearch.adapter =
                                MovieSearchAdapter(movieSearch)

                            Log.d("HomeFragment", "Sonuç bulundu")
                        } else {
                            binding.recyclerViewMovieSearch.visibility =
                                View.GONE  // Sonuç yoksa RecyclerView gizlenir
                            Log.d(" ", "Sonuç bulunamadı")
                        }
                    } catch (e: JsonSyntaxException) {
                        binding.recyclerViewMovieSearch.visibility = View.GONE
                        Log.e("HomeFragment", "JSON ayrıştırma hatası: ${e.message}")
                    }
                } else {
                    binding.recyclerViewMovieSearch.visibility = View.GONE
                    Log.d("HomeFragment", "Arama başarısız: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {

                binding.recyclerViewMovieSearch.visibility = View.GONE
                binding.recyclerViewMovieSuggestions.visibility = View.VISIBLE
                binding.textViewMovieSuggestions.visibility = View.VISIBLE
                Log.d("HomeFragment", "Hata: ${t.localizedMessage}")
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Binding'i serbest bırak
    }
}
