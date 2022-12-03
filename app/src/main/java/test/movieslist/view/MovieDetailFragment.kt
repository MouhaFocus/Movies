package test.movieslist.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import test.movieslist.R
import test.movieslist.adapters.MoviesListAdapter
import test.movieslist.utilities.Resource
import test.movieslist.viewmodels.MoviesVewModel
import test.movieslist.viewmodels.MoviesViewModelFactory


class MovieDetailFragment : Fragment() {

    lateinit var viewModel: MoviesVewModel
    lateinit var title: TextView
    lateinit var year: TextView
    lateinit var overview: TextView
    lateinit var imageView: ImageView
    val urlImage = "https://image.tmdb.org/t/p/w200"
    val args: MovieDetailFragmentArgs by navArgs()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModelFactory = MoviesViewModelFactory()
        viewModel = ViewModelProvider(
            this,
            viewModelFactory
        ).get(MoviesVewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movie_detail, container, false)
        // Inflate the layout for this fragment
        title = view.findViewById(R.id.title)
        year = view.findViewById(R.id.year)
        overview = view.findViewById(R.id.overview)
        imageView = view.findViewById(R.id.imageDetailMovie)
        getMovieDetail(args.movieId)


        return view
    }

    fun getMovieDetail(movieId: Int){
        viewModel.getSelectedMovie(movieId)
        viewModel.movieSelected.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    response.data?.let { responsePost ->
                        val url = urlImage+responsePost.poster_path
                        activity?.let {
                            Glide
                                .with(it)
                                .load(url)
                                .centerCrop()
                                .into(imageView)
                        }
                        title.text = responsePost.original_title
                        year.text = responsePost.release_date!!.subSequence(0,4)
                        overview.text = responsePost.overview
                    }

                }
                is Resource.Error -> {
                    response.message?.let { message ->
                        Log.e("response", "An error occured: $message")
                    }
                }
            }
        })
    }

}