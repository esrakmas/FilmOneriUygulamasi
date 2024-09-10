package com.example.filmoneriuygulamasi.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.filmoneriuygulamasi.R
import com.example.filmoneriuygulamasi.databinding.ItemMovieLibraryBinding
import com.example.filmoneriuygulamasi.network.MovieLine

class MovieLibraryAdapter(
    private var movieList: List<MovieLine> = listOf()
) : RecyclerView.Adapter<MovieLibraryAdapter.MovieLibraryViewHolder>() {

    inner class MovieLibraryViewHolder(val binding: ItemMovieLibraryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieLibraryViewHolder {
        val binding = ItemMovieLibraryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieLibraryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieLibraryViewHolder, position: Int) {
        val movieLine = movieList[position]

        holder.binding.textViewTitle.text = movieLine.name
        holder.binding.textViewDetails.text = "${movieLine.sty} | ${movieLine.times}"

        val secureUrl = movieLine.img.replace("http://", "https://")
        Log.d("MovieLibraryAdapter", "Loading image from URL: $secureUrl")

        Glide.with(holder.binding.imageViewPoster.context)
            .load(secureUrl)
            .apply(RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.baseline_no))
            .into(holder.binding.imageViewPoster)


    }

    override fun getItemCount(): Int = movieList.size

    fun submitList(movies: List<MovieLine>) {
        movieList = movies
        notifyDataSetChanged()
    }
}
