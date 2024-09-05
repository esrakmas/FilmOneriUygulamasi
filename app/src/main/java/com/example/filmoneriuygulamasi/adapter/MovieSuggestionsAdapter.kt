import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.filmoneriuygulamasi.R
import com.example.filmoneriuygulamasi.network.MovieSuggestion
import android.util.Log
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException

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

        // URL'yi HTTPS ile güncelle
        val secureUrl = movieLine.img.replace("http://", "https://")

        // Poster görselini Glide ile yükle
        Glide.with(holder.posterImageView.context)
            .load(secureUrl)
            .apply(RequestOptions()
                .placeholder(R.drawable.ic_launcher_background) // Placeholder resmi
                .error(R.drawable.baseline_no) // Hata resmi
            )
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                    Log.e("Glide", "Load failed: ${e?.message}")
                    return false
                }

                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    Log.d("Glide", "Resource loaded successfully")
                    return false
                }
            })
            .into(holder.posterImageView)
    }

    override fun getItemCount(): Int {
        return movieSuggestions.size
    }
}
