package com.example.filmoneriuygulamasi.adapter

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.filmoneriuygulamasi.R
import com.example.filmoneriuygulamasi.databinding.DialogSummaryCommentBinding
import com.example.filmoneriuygulamasi.network.MovieLine
import com.example.filmoneriuygulamasi.databinding.ItemWatchedBinding
import com.example.filmoneriuygulamasi.repository.FirebaseRepository

class WatchedAdapter(
    private var movieList: List<MovieLine> = listOf()
) : RecyclerView.Adapter<WatchedAdapter.WatchedViewHolder>() {

    inner class WatchedViewHolder(val binding: ItemWatchedBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.buttonUpdate.setOnClickListener {
                val movieLine = movieList[adapterPosition]
                // itemView.context ile context'i alıyoruz
                AddReviewDialogAdapter.showReviewDialog(itemView.context, movieLine)
            }

            binding.buttonDelete.setOnClickListener {
                val movieLine = movieList[adapterPosition]

                // Silme onay diyalogunu oluştur
                AlertDialog.Builder(itemView.context)
                    .setTitle("Silme Onayı")
                    .setMessage("Silmek istediğinize emin misiniz?")
                    .setPositiveButton("Evet") { _, _ ->

                        val firebaseRepository = FirebaseRepository()
                        firebaseRepository.deleteMovieFromWatched(itemView.context, movieLine.name)

                        val updatedList = movieList.toMutableList()
                        updatedList.removeAt(adapterPosition)
                        submitList(updatedList)
                    }
                    .setNegativeButton("Hayır", null) // Hayır'a tıklandığında hiçbir şey yapma
                    .show()
            }

            binding.buttonSumeryAndComment.setOnClickListener {
                val movieLine = movieList[adapterPosition]
                showSummaryAndCommentsDialog(binding.root.context, movieLine)
            }
        }
    }

    private fun showSummaryAndCommentsDialog(context: Context, movieLine: MovieLine) {
        // Dialog'un görünümünü oluştur
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_summary_comment, null)
        val binding = DialogSummaryCommentBinding.bind(dialogView)

        // Firestore'dan veri al
        FirebaseRepository().fetchMovieSummaryAndComments(movieLine.name) { summary, comments ->
            binding.textViewSummaryContent.text =
                if (summary.isNullOrBlank()) "Özet bulunamadı" else summary
            binding.textViewCommentsContent.text =
                if (comments.isNullOrBlank()) "Yorum bulunamadı" else comments

            // Diğer diyalog özelliklerini ayarla
            AlertDialog.Builder(context)
                .setView(dialogView)
                .setPositiveButton("Kapat", null) // Kapat butonu ile diyalog kapanacak
                .show()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WatchedViewHolder {
        val binding = ItemWatchedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WatchedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WatchedViewHolder, position: Int) {
        val movieLine = movieList[position]

        holder.binding.textViewTitle.text = movieLine.name
        holder.binding.textViewYear.text = movieLine.year

        val secureUrl = movieLine.img.replace("http://", "https://")
        Log.d("WatchedAdapter", "Loading image from URL: $secureUrl")

        Glide.with(holder.binding.imageViewPoster.context)
            .load(secureUrl)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.baseline_no)
            )
            .into(holder.binding.imageViewPoster)
    }

    override fun getItemCount(): Int = movieList.size

    fun submitList(movies: List<MovieLine>) {
        movieList = movies
        notifyDataSetChanged()
    }
}
