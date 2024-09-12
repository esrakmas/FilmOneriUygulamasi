package com.example.filmoneriuygulamasi.fragment

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.filmoneriuygulamasi.R
import com.example.filmoneriuygulamasi.SharedViewModel
import com.example.filmoneriuygulamasi.databinding.FragmentProfileBinding
import com.example.filmoneriuygulamasi.databinding.DialogEditProfileBinding
import com.example.filmoneriuygulamasi.databinding.DialogEditUsernameBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var sharedViewModel: SharedViewModel

    private val PREFS_NAME = "UserPreferences"
    private val KEY_USER_NAME = "userName"
    private val KEY_PROFILE = "profile"
    private val KEY_USER_RANK = "userRank"
    private val KEY_MOVIES_WATCHED = "moviesWatched"
    private val KEY_MOVIES_TO_WATCH = "moviesToWatch"


    private val targets = listOf(50, 150, 300, 600, Int.MAX_VALUE) // Hedef aşamaları

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // SharedPreferences'ı başlat
        sharedPreferences = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        // Kullanıcı adını SharedPreferences'tan oku ve göster
        val savedUserName = sharedPreferences.getString(KEY_USER_NAME, "Kullanıcı Adı")
        binding.userName.text = savedUserName

        val savedProfile = sharedPreferences.getString(KEY_PROFILE, "default")
        updateProfileImage(savedProfile ?: "default")

        // Kullanıcı adını düzenleme ikonuna tıklanma olayı
        binding.editUserNameIcon.setOnClickListener {
            showEditUserNameDialog()
        }

        binding.editprofileImageIcon.setOnClickListener {
            showEditProfileDialog()
        }

        // sharedViewModel baslat
        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]

        // Film istatistiklerini güncelle
        updateMovieStats()

        // Başlangıçta SharedPreferences'tan değerleri oku ve güncelle
        loadSavedMovieStats()

    }

    private fun updateMovieStats() {
        // ViewModel'den izlenen film sayısını al ve hedef aşamalarını belirle
        sharedViewModel.watchedMoviesCount.observe(viewLifecycleOwner, Observer { moviesWatched ->
            binding.moviesWatched.text = "İzlenen Filmler: $moviesWatched"

            // Hedef aşamalarından izlenecek film sayısını belirle
            val moviesToWatch = targets.firstOrNull { it >= moviesWatched } ?: targets.last()

            // İzlenecek filmler sayısını göster
            binding.moviesToWatch.text = "İzlenecek Filmler: $moviesToWatch"

            // Progress bar hesaplaması
            val progress = if (moviesToWatch == 0) 0 else (moviesWatched * 100) / moviesToWatch
            binding.progressBar.progress = progress

            // Kullanıcı seviyesini güncelle
            val userRank = when (moviesWatched) {
                in 0 until 50 -> "Yeni Başlayan"
                in 50 until 150 -> "Araştırmacı"
                in 150 until 300 -> "Tutkulu İzleyici"
                in 300 until 600 -> "Film Kaşifi"
                in 600 until Int.MAX_VALUE -> "Film Gurmesi"
                else -> "Bilinmeyen Seviye"
            }
            binding.userRank.text = "Seviye: $userRank"

            // SharedPreferences'a kaydet
            with(sharedPreferences.edit()) {
                putInt(KEY_MOVIES_WATCHED, moviesWatched)
                putInt(KEY_MOVIES_TO_WATCH, moviesToWatch)
                putString(KEY_USER_RANK, userRank)
                apply()
            }
        })
    }

    private fun loadSavedMovieStats() {
        // SharedPreferences'tan film istatistiklerini oku
        val savedMoviesWatched = sharedPreferences.getInt(KEY_MOVIES_WATCHED, 0)
        val savedMoviesToWatch = sharedPreferences.getInt(KEY_MOVIES_TO_WATCH, 50)
        val savedUserRank =
            sharedPreferences.getString(KEY_USER_RANK, "Bilinmeyen Seviye") ?: "Bilinmeyen Seviye"

        binding.moviesWatched.text = "İzlenen Filmler: $savedMoviesWatched"
        binding.moviesToWatch.text = "İzlenecek Filmler: $savedMoviesToWatch"
        binding.userRank.text = "Seviye: $savedUserRank"

        // Progress bar hesaplaması
        val progress =
            if (savedMoviesToWatch == 0) 0 else (savedMoviesWatched * 100) / savedMoviesToWatch
        binding.progressBar.progress = progress
    }

    private fun showEditProfileDialog() {
        val dialogBinding = DialogEditProfileBinding.inflate(LayoutInflater.from(requireContext()))
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Profil Seç")
            .setView(dialogBinding.root)
            .setPositiveButton("Kaydet") { _, _ ->
                // "Kaydet" butonuna tıklanınca mevcut profil tercihini kaydet
                val currentProfile = sharedPreferences.getString(KEY_PROFILE, "default")
                updateProfileImage(currentProfile ?: "default")
            }
            .setNegativeButton("Sıfırla") { dialog, _ ->
                updateProfileImage("default")
                saveProfilePreference("default")
                dialog.dismiss()
            }
            .create()

        dialogBinding.imageViewCloseEditProfileDialog.setOnClickListener {
            dialog.dismiss()
        }

        dialogBinding.imageMale.setOnClickListener {
            updateProfileImage("male")
            saveProfilePreference("male")
            dialog.dismiss()
        }

        dialogBinding.imageFemale.setOnClickListener {
            updateProfileImage("female")
            saveProfilePreference("female")
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun updateProfileImage(profile: String) {
        val profileImageRes = when (profile) {
            "male" -> R.drawable.man
            "female" -> R.drawable.female
            else -> R.drawable.baseline_person_24
        }
        binding.profileImage.setImageResource(profileImageRes)
    }

    private fun saveProfilePreference(profile: String) {
        // foto tercihini SharedPreferences'ta kaydet
        with(sharedPreferences.edit()) {
            putString(KEY_PROFILE, profile)
            apply()
        }
    }

    private fun showEditUserNameDialog() {
        val dialogBinding = DialogEditUsernameBinding.inflate(LayoutInflater.from(requireContext()))
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Kullanıcı Adı Düzenle")
            .setView(dialogBinding.root)
            .setNegativeButton("İptal", null)
            .setPositiveButton("Kaydet") { _, _ ->
                val newUserName = dialogBinding.userNameEditText.text.toString()
                if (newUserName.isNotBlank()) {
                    updateUserName(newUserName)
                }
            }
            .create()

        dialogBinding.imageViewCloseUserNameDialog.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun updateUserName(newUserName: String) {
        binding.userName.text = newUserName
        // Kullanıcı adını SharedPreferences'ta kaydet
        with(sharedPreferences.edit()) {
            putString(KEY_USER_NAME, newUserName)
            apply()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
