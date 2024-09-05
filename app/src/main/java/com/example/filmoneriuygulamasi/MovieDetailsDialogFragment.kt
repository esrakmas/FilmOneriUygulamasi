package com.example.filmoneriuygulamasi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.example.filmoneriuygulamasi.databinding.DialogMovieDetailsBinding

class MovieDetailsDialogFragment : DialogFragment() {

    companion object {
        private const val ARG_NAME = "name"
        private const val ARG_YEAR = "year"
        private const val ARG_GENRE = "genre"
        private const val ARG_DURATION = "duration"
        private const val ARG_DIRECTOR = "director"
        private const val ARG_SCREENWRITER = "screenwriter"
        private const val ARG_PRODUCER = "producer"
        private const val ARG_COUNTRY = "country"
        private const val ARG_POSTER = "poster"
        private const val ARG_TEASER = "teaser"

        fun newInstance(
            name: String,
            year: String,
            genre: String,
            duration: String,
            director: String,
            screenwriter: String,
            producer: String,
            country: String,
            poster: String,
            teaser: String
        ): MovieDetailsDialogFragment {
            val fragment = MovieDetailsDialogFragment()
            val args = Bundle().apply {
                putString(ARG_NAME, name)
                putString(ARG_YEAR, year)
                putString(ARG_GENRE, genre)
                putString(ARG_DURATION, duration)
                putString(ARG_DIRECTOR, director)
                putString(ARG_SCREENWRITER, screenwriter)
                putString(ARG_PRODUCER, producer)
                putString(ARG_COUNTRY, country)
                putString(ARG_POSTER, poster)
                putString(ARG_TEASER, teaser)
            }
            fragment.arguments = args
            return fragment
        }
    }

    private var _binding: DialogMovieDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            binding.textViewName.text = it.getString(ARG_NAME)
            binding.textViewYear.text = "Yıl: ${it.getString(ARG_YEAR)}"
            binding.textViewGenre.text = "Tür: ${it.getString(ARG_GENRE)}"
            binding.textViewDuration.text = "Süre: ${it.getString(ARG_DURATION)}"
            binding.textViewDirector.text = "Yönetmen: ${it.getString(ARG_DIRECTOR)}"
            binding.textViewScreenwriter.text = "Senarist: ${it.getString(ARG_SCREENWRITER)}"
            binding.textViewProducer.text = "Yapımcı: ${it.getString(ARG_PRODUCER)}"
            binding.textViewCountry.text = "Ülke: ${it.getString(ARG_COUNTRY)}"
            binding.textViewTeaser.text = "Teaser: ${it.getString(ARG_TEASER)}"

            Glide.with(this)
                .load(it.getString(ARG_POSTER))
                .into(binding.imageViewPoster)

            binding.buttonWatched.setOnClickListener {
                // İzledim butonuna tıklama işlemi
                dismiss() // Dialog'ı kapat
            }

            binding.buttonWatchLater.setOnClickListener {
                // Daha Sonra İzle butonuna tıklama işlemi
                dismiss() // Dialog'ı kapat
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
