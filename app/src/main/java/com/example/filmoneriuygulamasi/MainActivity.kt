package com.example.filmoneriuygulamasi

import com.example.filmoneriuygulamasi.fragment.ProfileFragment
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.filmoneriuygulamasi.databinding.ActivityMainBinding
import com.example.filmoneriuygulamasi.fragment.HomeFragment
import com.example.filmoneriuygulamasi.fragment.LibraryFragment

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
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_layout, fragment)
            .commit()
    }

}
