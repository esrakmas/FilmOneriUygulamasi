package com.example.filmoneriuygulamasi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.filmoneriuygulamasi.databinding.ItemMovieLibraryBinding
import com.example.filmoneriuygulamasi.network.MovieLine

class MovieLibraryAdapter(
    private val movieList: List<MovieLine>
) : RecyclerView.Adapter<MovieLibraryAdapter.MovieLibraryViewHolder>() {

    class MovieLibraryViewHolder(val binding: ItemMovieLibraryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieLibraryViewHolder {
        val binding = ItemMovieLibraryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieLibraryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieLibraryViewHolder, position: Int) {
        val movieLine = movieList[position]

        holder.binding.textViewTitle.text = movieLine.name
        holder.binding.textViewDetails.text = "${movieLine.sty} - ${movieLine.times}"

        // URL'yi HTTPS ile güncelle
        val secureUrl = movieLine.img.replace("http://", "https://")
        Glide.with(holder.binding.imageViewPoster.context)
            .load(secureUrl)
            .into(holder.binding.imageViewPoster)
    }

    override fun getItemCount(): Int = movieList.size
}
