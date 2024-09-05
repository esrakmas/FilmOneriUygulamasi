import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.filmoneriuygulamasi.R
import com.example.filmoneriuygulamasi.network.TopImdbMovie

class TopImdbMoviesAdapter(
    private val imdbMovies: List<TopImdbMovie>
) : RecyclerView.Adapter<TopImdbMoviesAdapter.TopImdbMovieViewHolder>() {

    class TopImdbMovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val posterImageView: ImageView = view.findViewById(R.id.imageViewImdbMoviePoster)
        val titleTextView: TextView = view.findViewById(R.id.textViewImdbMovieTitle)
        val ratingTextView: TextView = view.findViewById(R.id.textViewImdbMovieRating)
        val yearTextView: TextView = view.findViewById(R.id.textViewImdbMovieYear)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopImdbMovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_top_imdb_movie, parent, false)
        return TopImdbMovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: TopImdbMovieViewHolder, position: Int) {
        val imdbMovie = imdbMovies[position]

        holder.titleTextView.text = imdbMovie.name
        holder.ratingTextView.text = imdbMovie.rate
        holder.yearTextView.text = imdbMovie.year

        // Poster görselini yüklemek için Glide kütüphanesini kullanıyoruz
        Glide.with(holder.posterImageView.context)
            .load(imdbMovie.img)
            .into(holder.posterImageView)
    }

    override fun getItemCount(): Int {
        return imdbMovies.size
    }
}
