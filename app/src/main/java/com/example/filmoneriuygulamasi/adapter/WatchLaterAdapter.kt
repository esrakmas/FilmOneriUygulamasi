package com.example.filmoneriuygulamasi.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.filmoneriuygulamasi.R
import com.example.filmoneriuygulamasi.databinding.ItemWatchLaterBinding
import com.example.filmoneriuygulamasi.network.MovieLine

class WatchLaterAdapter(
    private var movieList: List<MovieLine> = listOf()
) : RecyclerView.Adapter<WatchLaterAdapter.WatchLaterAdapterViewHolder>() {

    inner class WatchLaterAdapterViewHolder(val binding: ItemWatchLaterBinding) : RecyclerView.ViewHolder(binding.root)
    {
        init {
            binding.buttonWatched.setOnClickListener {
                val movieLine = movieList[adapterPosition]
                // itemView.context ile context'i alıyoruz
                AddReviewDialogAdapter.showReviewDialog(itemView.context, movieLine)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WatchLaterAdapterViewHolder {
        val binding =ItemWatchLaterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WatchLaterAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WatchLaterAdapterViewHolder, position: Int) {
        val movieLine = movieList[position]

        holder.binding.textViewTitle.text = movieLine.name
        holder.binding.textViewYear.text = movieLine.year

        val secureUrl = movieLine.img.replace("http://", "https://")
        Log.d("WatchLaterAdapter", "Loading image from URL: $secureUrl")

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
