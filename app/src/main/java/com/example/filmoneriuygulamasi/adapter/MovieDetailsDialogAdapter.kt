package com.example.filmoneriuygulamasi.adapter

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import com.bumptech.glide.Glide
import com.example.filmoneriuygulamasi.databinding.DialogAddReviewBinding
import com.example.filmoneriuygulamasi.databinding.DialogMovieDetailsBinding
import com.example.filmoneriuygulamasi.network.MovieLine

/*
*
* film detaylarını gösterr
* izledime basarsa : AddReviewDialogAdapter çağır
* Daha sonra izleye basarsa : ??? şuanlık boş
* */


class MovieDetailsDialogAdapter
   {

    companion object {
        fun showMovieDetailsDialog(context: Context, movieLine: MovieLine)
        {
            val dialogBinding = DialogMovieDetailsBinding.inflate(LayoutInflater.from(context))

            // Dialog alanlarını doldurma
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

            //dialog oluştur
            val dialog = AlertDialog.Builder(context)
                .setView(dialogBinding.root)
                .setCancelable(true)
                .create()

            dialog.show()

            dialogBinding.buttonWatchedDetailsDialog.setOnClickListener {
                dialog.dismiss()
                AddReviewDialogAdapter.showReviewDialog(context, movieLine)
            }

            dialogBinding.buttonWatchLaterDetailsDialog.setOnClickListener {
                dialog.dismiss()
            }

            dialogBinding.imageViewCloseDetailsDialog.setOnClickListener {
                dialog.dismiss()
            }

        }
    }

}
