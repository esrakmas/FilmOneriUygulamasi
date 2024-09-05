package com.example.filmoneriuygulamasi

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.filmoneriuygulamasi.databinding.ActivityMainBinding
import com.example.filmoneriuygulamasi.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // İlk olarak HomeFragment'i aç
        replaceFragment(HomeFragment())

        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> replaceFragment(HomeFragment())
                R.id.library -> replaceFragment(LibraryFragment())
                R.id.profile -> replaceFragment(ProfileFragment())
            }
            true
        }

        // API çağrısı
        fetchImdbSearchByName("inception")
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_layout, fragment)
            .commit()
    }

    private fun fetchImdbSearchByName(query: String) {
        val apiKey = "apikey 0zXunVCs4qN1zD5YpNZ88V:7kztaQJhg1xKov9tvLjaGZ"
        val contentType = "application/json"

        val call = ApiClient.movieApiService.imdbSearchByName(contentType, apiKey, query)
        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    Log.d("MovieTest", "Yanıt: ${response.body()}")
                } else {
                    Log.e("MovieTest", "Başarısız yanıt: ${response.code()} - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("MovieTest", "API başarısız: ${t.message}")
            }
        })
    }
}
