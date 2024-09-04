package com.example.filmoneriuygulamasi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class TopImdbMoviesAdapter(private val movies: List<TopImdbMovie>) : RecyclerView.Adapter<TopImdbMoviesAdapter.TopImdbMovieViewHolder>() {

    inner class TopImdbMovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.movieTitle)
        val year: TextView = itemView.findViewById(R.id.movieYear)
        val poster: ImageView = itemView.findViewById(R.id.moviePoster)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopImdbMovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.top_imdb_movie_item, parent, false)
        return TopImdbMovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: TopImdbMovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.title.text = movie.name
        holder.year.text = movie.year
        Glide.with(holder.itemView.context).load(movie.img).into(holder.poster)
    }

    override fun getItemCount() = movies.size
}
