package com.example.filmoneriuygulamasi.adapter

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import com.example.filmoneriuygulamasi.databinding.DialogAddReviewBinding
import com.example.filmoneriuygulamasi.network.MovieLine
import com.example.filmoneriuygulamasi.repository.FirebaseRepository


class AddReviewDialogAdapter {
    companion object {
        fun showReviewDialog(context: Context, movie: MovieLine) {
            val reviewDialogBinding = DialogAddReviewBinding.inflate(LayoutInflater.from(context))

            val reviewDialog = AlertDialog.Builder(context)
                .setView(reviewDialogBinding.root)
                .setCancelable(true)
                .create()

            reviewDialog.show()

            val firebaseRepository = FirebaseRepository()

            reviewDialogBinding.imageViewAddReviewDialog.setOnClickListener {
                reviewDialog.dismiss()
            }

            reviewDialogBinding.buttonSubmitReview.setOnClickListener {
                val userSummary = reviewDialogBinding.editTextSummary.text.toString()
                val userComment = reviewDialogBinding.editTextComment.text.toString()

                firebaseRepository.saveMovieToWatched(context, movie, userSummary, userComment)
                reviewDialog.dismiss()
            }

        }
    }
}

