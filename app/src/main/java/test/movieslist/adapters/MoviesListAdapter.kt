package test.movieslist.adapters


import test.movieslist.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import test.movieslist.data.models.Result
import test.movieslist.view.MovieListFragmentDirections

class MoviesListAdapter(private val mList: List<Result>) :
    RecyclerView.Adapter<MoviesListAdapter.ViewHolder>() {

    val urlImage = "https://image.tmdb.org/t/p/w200"


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)


        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val items = mList[position]
        holder.result = items
        holder.textViewTitle.text = items.title
        holder.textViewYear.text = items.release_date!!.subSequence(0,4)
        val url = urlImage+items.poster_path
        Glide
            .with(holder.itemView.context)
            .load(url)
            .centerCrop()
            .into(holder.imageViewPoster)

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val textViewTitle: TextView = itemView.findViewById(R.id.title)
        val textViewYear: TextView = itemView.findViewById(R.id.year)
        val imageViewPoster: ImageView = itemView.findViewById(R.id.imageFilm)
        lateinit var result: Result

        init {

            ItemView.setOnClickListener {
            val direction = MovieListFragmentDirections.actionMovieListFragmentToMovieDetailFragment(
                result.id!!
            )
            itemView.findNavController().navigate(direction)
            }
        }

    }


}

