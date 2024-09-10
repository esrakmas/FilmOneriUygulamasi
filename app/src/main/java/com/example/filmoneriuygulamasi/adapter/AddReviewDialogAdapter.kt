package com.example.filmoneriuygulamasi.adapter


import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import com.example.filmoneriuygulamasi.databinding.DialogAddReviewBinding
import com.example.filmoneriuygulamasi.network.MovieLine
import com.example.filmoneriuygulamasi.repository.FirebaseRepository


/**
 *
 * dialog_add_review yönetecek ama henüz butonlara işlev kazxandırmadım !!!!!
 *
 */

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

            // Yorum ya da özet ile birlikte gönder
            reviewDialogBinding.buttonSubmitReview.setOnClickListener {
                val userSummary = reviewDialogBinding.editTextSummary.text.toString()
                val userComment = reviewDialogBinding.editTextComment.text.toString()

                // Eğer özet ya da yorum boşsa, boş string olarak kaydedelim
                val summaryToSave = if (userSummary.isNotEmpty()) userSummary else ""
                val commentToSave = if (userComment.isNotEmpty()) userComment else ""

                firebaseRepository.saveMovieToWatched(context, movie, userSummary, userComment)
                Log.d("MovieReview", "Özet: $userSummary, Yorum: $userComment")
                reviewDialog.dismiss()
                // Özet ve yorumu işleme koyabilirsiniz
            }

        }
    }
}

