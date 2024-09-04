package com.example.filmoneriuygulamasi

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.filmoneriuygulamasi.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var movieApiService: MovieApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrofit ve API servislerini başlat
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.collectapi.com/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()

        movieApiService = retrofit.create(MovieApiService::class.java)

        // API çağrılarını başlat
        fetchImdbSearchByName("inception")
        fetchMoviesImdb()
        fetchMovieSuggest()

        //home ile açılış yap
        replaceFragment(HomeFragment())
        binding.bottomNavigationView.setOnItemSelectedListener {menuItem ->
            Log.d("bottomChek", "bottom: ${binding.bottomNavigationView}")

            when (menuItem.itemId) {
                R.id.home -> replaceFragment(HomeFragment())
                R.id.library -> replaceFragment(LibraryFragment())
                R.id.profile -> replaceFragment(ProfileFragment())
                else->{
                }
            }
            true
        }


    }


    // Fragment'leri değiştiren yardımcı fonksiyon
    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_layout,fragment)
        fragmentTransaction.commit()
    }



    private fun fetchImdbSearchByName(query: String) {
        val apiKey = "apikey 0zXunVCs4qN1zD5YpNZ88V:7kztaQJhg1xKov9tvLjaGZ"
        val contentType = "application/json"

        val call = movieApiService.imdbSearchByName(contentType, apiKey, query)
        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    Log.d("MovieTest", "ImdbSearchByName Yanıt: ${response.body()}")
                } else {
                    Log.e("MovieTest", "ImdbSearchByName Başarısız yanıt: ${response.code()} - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("MovieTest", "ImdbSearchByName API çağrısı başarısız: ${t.message}")
            }
        })
    }

    private fun fetchMoviesImdb() {
        val apiKey = "apikey 0zXunVCs4qN1zD5YpNZ88V:7kztaQJhg1xKov9tvLjaGZ"
        val contentType = "application/json"

        val call = movieApiService.moviesImdb(contentType, apiKey)
        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    Log.d("MovieTest", "MoviesImdb Yanıt: ${response.body()}")
                } else {
                    Log.e("MovieTest", "MoviesImdb Başarısız yanıt: ${response.code()} - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("MovieTest", "MoviesImdb API çağrısı başarısız: ${t.message}")
            }
        })
    }

    private fun fetchMovieSuggest() {
        val apiKey = "apikey 0zXunVCs4qN1zD5YpNZ88V:7kztaQJhg1xKov9tvLjaGZ"
        val contentType = "application/json"

        val call = movieApiService.movieSuggest(contentType, apiKey)
        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    Log.d("MovieTest", "MovieSuggest Yanıt: ${response.body()}")
                } else {
                    Log.e("MovieTest", "MovieSuggest Başarısız yanıt: ${response.code()} - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("MovieTest", "MovieSuggest API çağrısı başarısız: ${t.message}")
            }
        })
    }

}



