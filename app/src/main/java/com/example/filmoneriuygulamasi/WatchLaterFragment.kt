package com.example.filmoneriuygulamasi

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmoneriuygulamasi.adapter.MovieLibraryAdapter
import com.example.filmoneriuygulamasi.databinding.FragmentWatchLaterBinding
import com.example.filmoneriuygulamasi.network.MovieLine
import com.example.filmoneriuygulamasi.repository.FirebaseRepository

class WatchLaterFragment : Fragment() {

    private var _binding: FragmentWatchLaterBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: MovieLibraryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWatchLaterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = MovieLibraryAdapter()
        binding.recyclerViewWatchLater.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewWatchLater.adapter = adapter

        val firebaseRepository = FirebaseRepository()
        firebaseRepository.fetchWatchLaterMovies { movies ->
            Log.d("WatchLaterFragment", "Movies fetched: $movies")
            // Verileri adapter'a iletin
            adapter.submitList(movies)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
