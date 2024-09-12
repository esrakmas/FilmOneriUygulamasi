package com.example.filmoneriuygulamasi.adapter

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.filmoneriuygulamasi.databinding.DialogAddReviewBinding
import com.example.filmoneriuygulamasi.databinding.DialogMovieDetailsBinding
import com.example.filmoneriuygulamasi.network.MovieLine
import com.example.filmoneriuygulamasi.repository.FirebaseRepository
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MovieDetailsDialogAdapter
   {

    companion object {

        fun showMovieDetailsDialog(context: Context, movieLine: MovieLine)
        {
            val dialogBinding = DialogMovieDetailsBinding.inflate(LayoutInflater.from(context))

            // Dialog alanlarını api ile doldurma
            dialogBinding.textViewNameDetailsDialog.text = movieLine.name
            dialogBinding.textViewYearDetailsDialog.text =  "${movieLine.year}"
            dialogBinding.textViewGenreDetailsDialog.text = " ${movieLine.sty}"
            dialogBinding.textViewDurationDetailsDialog.text = "${movieLine.times}"
            dialogBinding.textViewDirectorDetailsDialog.text = "${movieLine.dir}"
            dialogBinding.textViewScreenwriterDetailsDialog.text = "${movieLine.sce}"
            dialogBinding.textViewProducerDetailsDialog.text = "${movieLine.pro}"
            dialogBinding.textViewCountryDetailsDialog.text = "${movieLine.pla}"
            dialogBinding.textViewTeaserDetailsDialog.text = "${movieLine.teaser}"

            val secureUrl = movieLine.img.replace("http://", "https://")
            Glide.with(dialogBinding.imageViewPosterDetailsDialog.context)
                .load(secureUrl)
                .into(dialogBinding.imageViewPosterDetailsDialog)

            val dialog = AlertDialog.Builder(context)
                .setView(dialogBinding.root)
                .setCancelable(true)
                .create()

            dialog.show()

            val firebaseRepository = FirebaseRepository()

            dialogBinding.buttonWatchedDetailsDialog.setOnClickListener {
                dialog.dismiss()
                AddReviewDialogAdapter.showReviewDialog(context, movieLine)
            }

            dialogBinding.buttonWatchLaterDetailsDialog.setOnClickListener {
                firebaseRepository.saveMovieToWatchLater(context, movieLine)
                dialog.dismiss()
            }

            dialogBinding.imageViewCloseDetailsDialog.setOnClickListener {
                dialog.dismiss()
            }

        }
    }
}
