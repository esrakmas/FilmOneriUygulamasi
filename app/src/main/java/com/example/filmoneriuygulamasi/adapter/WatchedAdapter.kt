package com.example.filmoneriuygulamasi.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.filmoneriuygulamasi.R
import com.example.filmoneriuygulamasi.network.MovieLine
import com.example.filmoneriuygulamasi.databinding.ItemWatchedBinding

class WatchedAdapter(
    private var movieList: List<MovieLine> = listOf()
) : RecyclerView.Adapter<WatchedAdapter.WatchedViewHolder>() {

    inner class WatchedViewHolder(val binding: ItemWatchedBinding) : RecyclerView.ViewHolder(binding.root)
    {
        init {
            binding.buttonUpdate.setOnClickListener {
                val movieLine = movieList[adapterPosition]
                // itemView.context ile context'i alıyoruz
                AddReviewDialogAdapter.showReviewDialog(itemView.context, movieLine)
            }
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
                .error(R.drawable.baseline_no))
            .into(holder.binding.imageViewPoster)
    }

    override fun getItemCount(): Int = movieList.size


    fun submitList(movies: List<MovieLine>) {
        movieList = movies
        notifyDataSetChanged()
    }
}
