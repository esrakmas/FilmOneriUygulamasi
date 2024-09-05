package com.example.filmoneriuygulamasi.adapter

import android.app.AlertDialog
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.filmoneriuygulamasi.R
import com.example.filmoneriuygulamasi.databinding.DialogMovieDetailsBinding
import com.example.filmoneriuygulamasi.databinding.ItemMovieSuggestionBinding
import com.example.filmoneriuygulamasi.network.MovieLine
import com.example.filmoneriuygulamasi.network.MovieSuggestion

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

        holder.binding.textViewTitle.text = movieLine.name
        holder.binding.textViewDuration.text = movieLine.times
        holder.binding.textViewGenre.text = movieLine.sty

        // URL'yi HTTPS ile güncelle
        val secureUrl = movieLine.img.replace("http://", "https://")

        // Poster görselini Glide ile yükle
        Glide.with(holder.binding.imageViewPoster.context)
            .load(secureUrl)
            .apply(RequestOptions()
                .placeholder(R.drawable.ic_launcher_background) // Placeholder resmi
                .error(R.drawable.baseline_no) // Hata resmi
            )
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                    Log.e("Glide", "Load failed: ${e?.message}")
                    return false
                }

                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    Log.d("Glide", "Resource loaded successfully")
                    return false
                }
            })
            .into(holder.binding.imageViewPoster)

        // Item tıklandığında dialog açma
        holder.itemView.setOnClickListener {
            showMovieDetailsDialog(holder, movieLine)
        }
    }

    private fun showMovieDetailsDialog(holder: MovieSuggestionViewHolder, movieLine: MovieLine) {
        // Dialog oluşturma
        val dialogBinding = DialogMovieDetailsBinding.inflate(LayoutInflater.from(holder.itemView.context))

        // Dialog alanlarını doldurma
        dialogBinding.textViewName.text = movieLine.name
        dialogBinding.textViewYear.text = "${movieLine.year}"
        dialogBinding.textViewGenre.text = "${movieLine.sty}"
        dialogBinding.textViewDuration.text = "${movieLine.times}"
        dialogBinding.textViewDirector.text = "${movieLine.dir}"
        dialogBinding.textViewScreenwriter.text = "${movieLine.sce}"
        dialogBinding.textViewProducer.text = "${movieLine.pro}"
        dialogBinding.textViewCountry.text = "${movieLine.pla}"
        dialogBinding.textViewTeaser.text = "${movieLine.teaser}"

        // Poster yüklemesi
        val secureUrl = movieLine.img.replace("http://", "https://")
        Glide.with(dialogBinding.imageViewPoster.context)
            .load(secureUrl)
            .into(dialogBinding.imageViewPoster)

        // Dialog'u göster
        val dialog = AlertDialog.Builder(holder.itemView.context)
            .setView(dialogBinding.root)
            .setCancelable(true)
            .create()

        dialog.show()

        // İzledim ve Daha Sonra İzle butonları için örnek olaylar
        dialogBinding.buttonWatched.setOnClickListener {
            Log.d("Dialog", "${movieLine.name} izlendi.")
            dialog.dismiss()
        }

        dialogBinding.buttonWatchLater.setOnClickListener {
            Log.d("Dialog", "${movieLine.name} daha sonra izlenecek.")
            dialog.dismiss()
        }
    }

    override fun getItemCount(): Int = movieSuggestions.size
}
