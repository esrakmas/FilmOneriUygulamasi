package com.example.filmoneriuygulamasi

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmoneriuygulamasi.adapter.WatchLaterAdapter
import com.example.filmoneriuygulamasi.adapter.WatchedAdapter
import com.example.filmoneriuygulamasi.databinding.FragmentWatchLaterBinding
import com.example.filmoneriuygulamasi.databinding.FragmentWatchedBinding
import com.example.filmoneriuygulamasi.repository.FirebaseRepository

class WatchedFragment : Fragment() {

    private var _binding: FragmentWatchedBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: WatchedAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWatchedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = WatchedAdapter()
        binding.recyclerViewWatchedMovies.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewWatchedMovies.adapter = adapter

        val firebaseRepository = FirebaseRepository()
        firebaseRepository.fetchWatchedMovies { movies ->
            Log.d("WatchedFragment", "Movies fetched: $movies")
            // Verileri adapter'a iletin
            adapter.submitList(movies)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}