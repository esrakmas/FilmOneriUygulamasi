package com.example.filmoneriuygulamasi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.filmoneriuygulamasi.R
import com.example.filmoneriuygulamasi.databinding.ItemMovieSearchBinding
import com.example.filmoneriuygulamasi.network.MovieSearchResult

class MovieSearchAdapter(private val movieList: List<MovieSearchResult>) : RecyclerView.Adapter<MovieSearchAdapter.MovieViewHolder>() {

    class MovieViewHolder(private val binding: ItemMovieSearchBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieSearchResult) {
            binding.textViewTitleSearch.text = movie.Title
            binding.textViewYearSearch.text = movie.Year
            Glide.with(binding.root.context)
                .load(movie.Poster)
                .placeholder(R.drawable.ic_launcher_background) // Yer tutucu resim
                .into(binding.imageViewPosterSearch)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movieList[position])
    }

    override fun getItemCount(): Int {
        return movieList.size
    }
}
