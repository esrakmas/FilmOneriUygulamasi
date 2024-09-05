import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.filmoneriuygulamasi.R
import com.example.filmoneriuygulamasi.network.MovieSuggestion

class MovieSuggestionsAdapter(
    private val movieSuggestions: List<MovieSuggestion>
) : RecyclerView.Adapter<MovieSuggestionsAdapter.MovieSuggestionViewHolder>() {

    class MovieSuggestionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val posterImageView: ImageView = view.findViewById(R.id.imageViewPoster)
        val titleTextView: TextView = view.findViewById(R.id.textViewTitle)
        val durationTextView: TextView = view.findViewById(R.id.textViewDuration)
        val genreTextView: TextView = view.findViewById(R.id.textViewGenre)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieSuggestionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie_suggestion, parent, false)
        return MovieSuggestionViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieSuggestionViewHolder, position: Int) {
        val movieSuggestion = movieSuggestions[position]
        val movieLine = movieSuggestion.data.lines[0]

        holder.titleTextView.text = movieLine.name
        holder.durationTextView.text = movieLine.times
        holder.genreTextView.text = movieLine.sty

        // Poster görselini yüklemek için Glide kütüphanesini kullanıyoruz
        Glide.with(holder.posterImageView.context)
            .load(movieLine.img)
            .into(holder.posterImageView)
    }

    override fun getItemCount(): Int {
        return movieSuggestions.size
    }
}
