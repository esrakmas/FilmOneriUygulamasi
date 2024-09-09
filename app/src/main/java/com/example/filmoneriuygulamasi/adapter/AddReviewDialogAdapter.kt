package com.example.filmoneriuygulamasi.adapter


import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import com.example.filmoneriuygulamasi.databinding.DialogAddReviewBinding
import com.example.filmoneriuygulamasi.network.MovieLine


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

            reviewDialogBinding.imageViewAddReviewDialog.setOnClickListener {
                reviewDialog.dismiss()
            }

            // Yorum ya da özet ile birlikte gönder
            reviewDialogBinding.buttonSubmitReview.setOnClickListener {
                val userSummary = reviewDialogBinding.editTextSummary.text.toString()
                val userReview = reviewDialogBinding.editTextComment.text.toString()

                Log.d("MovieReview", "Özet: $userSummary, Yorum: $userReview")
                reviewDialog.dismiss()
                // Özet ve yorumu işleme koyabilirsiniz
            }

            // Yorum ya da özet eklemeden kaydet
            reviewDialogBinding.buttonSaveWithoutReview.setOnClickListener {
                Log.d("MovieReview", "${movie.name} için yorum ya da özet eklemeden kaydedildi.")
                reviewDialog.dismiss()
                // Kaydetme işlemi yapılabilir
            }
        }
    }
}

