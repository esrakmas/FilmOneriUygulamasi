package com.example.filmoneriuygulamasi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {

    // İzlenen filmler sayısını tutan LiveData
    private val _watchedMoviesCount = MutableLiveData<Int>()

    val watchedMoviesCount: LiveData<Int> get() = _watchedMoviesCount

    // Sayıyı güncellemek için fonksiyon
    fun setWatchedMoviesCount(count: Int) {
        _watchedMoviesCount.value = count
    }
}
