package com.example.filmoneriuygulamasi.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.filmoneriuygulamasi.network.MovieLine
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FirebaseRepository {

    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference

    fun saveMovieToWatchLater(context: Context, movieLine: MovieLine) {
        val movieData = hashMapOf(
            "name" to movieLine.name,
            "img" to movieLine.img,
            "year" to movieLine.year,
            "sty" to movieLine.sty,
            "times" to movieLine.times,
            "dir" to movieLine.dir,
            "sce" to movieLine.sce,
            "pro" to movieLine.pro,
            "pla" to movieLine.pla,
            "teaser" to movieLine.teaser
        )

        database.child("watchLater").child(movieLine.name).setValue(movieData)
            .addOnSuccessListener {
                Toast.makeText(
                    context,
                    "${movieLine.name} 'Daha Sonra İzle' listesine eklendi.",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .addOnFailureListener {
                Toast.makeText(context, "Film kaydedilirken bir hata oluştu.", Toast.LENGTH_SHORT)
                    .show()
            }
    }

    fun saveMovieToWatched(
        context: Context,
        movieLine: MovieLine,
        userSummary: String? = null,
        userComment: String? = null
    ) {
        val movieData = hashMapOf(
            "name" to movieLine.name,
            "img" to movieLine.img,
            "year" to movieLine.year,
            "sty" to movieLine.sty,
            "times" to movieLine.times,
            "dir" to movieLine.dir,
            "sce" to movieLine.sce,
            "pro" to movieLine.pro,
            "pla" to movieLine.pla,
            "teaser" to movieLine.teaser,
            "summary" to userSummary,
            "comment" to userComment
        )

        database.child("watched").child(movieLine.name).setValue(movieData)
            .addOnSuccessListener {
                Toast.makeText(
                    context,
                    "${movieLine.name} 'İzledim' listesine eklendi.",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .addOnFailureListener {
                Toast.makeText(context, "Film kaydedilirken bir hata oluştu.", Toast.LENGTH_SHORT)
                    .show()
            }
    }

    fun deleteMovieFromWatchLater(context: Context, movieName: String) {
        database.child("watchLater").child(movieName).removeValue()
            .addOnSuccessListener {
                Toast.makeText(
                    context,
                    "Film 'Daha Sonra İzle' listesinden kaldırıldı.",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .addOnFailureListener {
                Toast.makeText(context, "Film silinirken bir hata oluştu.", Toast.LENGTH_SHORT)
                    .show()
            }
    }

    fun deleteMovieFromWatched(context: Context, movieName: String) {
        database.child("watched").child(movieName).removeValue()
            .addOnSuccessListener {
                Toast.makeText(
                    context,
                    "Film 'İzledim' listesinden kaldırıldı.",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .addOnFailureListener {
                Toast.makeText(context, "Film silinirken bir hata oluştu.", Toast.LENGTH_SHORT)
                    .show()
            }
    }

    fun fetchWatchLaterMovies(onResult: (List<MovieLine>) -> Unit) {
        database.child("watchLater").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val movies = mutableListOf<MovieLine>()
                for (movieSnapshot in snapshot.children) {
                    val movieData = movieSnapshot.getValue(MovieLine::class.java)
                    if (movieData != null) {
                        movies.add(movieData)
                    } else {
                    }
                }
                onResult(movies)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("FirebaseRepository", "hata", error.toException())
            }
        })
    }

    fun fetchWatchedMovies(onResult: (List<MovieLine>) -> Unit) {
        database.child("watched").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val movies = mutableListOf<MovieLine>()
                for (movieSnapshot in snapshot.children) {
                    val movieData = movieSnapshot.getValue(MovieLine::class.java)
                    if (movieData != null) {
                        movies.add(movieData)
                    } else {
                        Log.e("FirebaseRepository", "hata film gelmiyor ${movieSnapshot.key}")
                    }
                }
                onResult(movies)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("FirebaseRepository", "hata", error.toException())
            }
        })
    }


    // Film özet ve yorumlarını almak için fonksiyon
    fun fetchMovieSummaryAndComments(movieName: String, onResult: (String?, String?) -> Unit) {
        database.child("watched").child(movieName).get()
            .addOnSuccessListener { snapshot ->
                if (snapshot.exists()) {
                    val summary = snapshot.child("summary").getValue(String::class.java)
                    val comments = snapshot.child("comment").getValue(String::class.java)
                    onResult(summary, comments)
                } else {
                    onResult(null, null) // Veriler bulunamadı
                }
            }
            .addOnFailureListener { exception ->
                Log.e("FirebaseRepository", "yorum özet gelmiyor", exception)
                onResult(null, null) // Hata durumunda null döndür
            }
    }


}
