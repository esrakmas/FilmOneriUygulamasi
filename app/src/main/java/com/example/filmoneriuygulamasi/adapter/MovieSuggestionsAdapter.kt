package com.example.filmoneriuygulamasi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.filmoneriuygulamasi.R
import com.example.filmoneriuygulamasi.databinding.ItemMovieSuggestionBinding
import com.example.filmoneriuygulamasi.network.MovieSuggestion

/*

Film önerilerini göster üstüne tıklanırsa MovieDetailsDialogAdapter çağır
*/

class MovieSuggestionsAdapter(
    private val movieSuggestions: List<MovieSuggestion>
) : RecyclerView.Adapter<MovieSuggestionsAdapter.MovieSuggestionViewHolder>() {

    class MovieSuggestionViewHolder(val binding: ItemMovieSuggestionBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieSuggestionViewHolder {
        val binding = ItemMovieSuggestionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieSuggestionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieSuggestionViewHolder, position: Int) {
        val movieSuggestion = movieSuggestions[position]
        val movieLine = movieSuggestion.data.lines[0]

        holder.binding.textViewTitleSuggestion.text = movieLine.name
        holder.binding.textViewDurationSuggestion.text = movieLine.times
        holder.binding.textViewGenreSuggestion.text = movieLine.sty

        // URL'yi HTTPS ile güncelle
        val secureUrl = movieLine.img.replace("http://", "https://")

        // Poster görselini Glide ile yükle
        Glide.with(holder.binding.imageViewPosterSuggestion.context)
            .load(secureUrl)
            .apply(RequestOptions()
                .placeholder(R.drawable.ic_launcher_background) // Placeholder resmi
                .error(R.drawable.baseline_no) // Hata resmi
            )
            .into(holder.binding.imageViewPosterSuggestion)


        //önerilere basınca detay dialog açılsın
        holder.itemView.setOnClickListener {
            MovieDetailsDialogAdapter.showMovieDetailsDialog(
                holder.itemView.context,
                movieLine)
        }

    }


    override fun getItemCount(): Int = movieSuggestions.size
}
