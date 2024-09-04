package com.example.filmoneriuygulamasi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MovieSuggestionsAdapter(private val suggestions: List<MovieSuggestion>) : RecyclerView.Adapter<MovieSuggestionsAdapter.MovieSuggestionViewHolder>() {

    inner class MovieSuggestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.suggestionTitle)
        val poster: ImageView = itemView.findViewById(R.id.suggestionPoster)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieSuggestionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_suggestion_item, parent, false)
        return MovieSuggestionViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieSuggestionViewHolder, position: Int) {
        val suggestion = suggestions[position]
        holder.title.text = suggestion.data.lines[0].name // İlk satırdaki adı alıyoruz
        Glide.with(holder.itemView.context).load(suggestion.url).into(holder.poster)
    }

    override fun getItemCount() = suggestions.size
}
