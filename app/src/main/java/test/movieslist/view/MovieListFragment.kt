package test.movieslist.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import test.movieslist.R
import test.movieslist.adapters.MoviesListAdapter
import test.movieslist.utilities.Resource
import test.movieslist.viewmodels.MoviesVewModel
import test.movieslist.viewmodels.MoviesViewModelFactory


class MovieListFragment : Fragment() {

    lateinit var viewModel: MoviesVewModel
    lateinit var recyclerView : RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_movie_list, container, false)
        recyclerView = view.findViewById(R.id.recyclerview_moviesList)
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(LinearLayoutManager(getContext()))

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModelFactory = MoviesViewModelFactory()
        viewModel = ViewModelProvider(
            this,
            viewModelFactory
        ).get(MoviesVewModel::class.java)



        getMoviesList()
    }

    fun getMoviesList(){
        viewModel.getMovies()
        viewModel.moviesListResponse.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    response.data?.let { responsePost ->
                        Log.i("response : ", "\n ${responsePost.results}")
                        recyclerView.adapter = MoviesListAdapter(responsePost.results!!)
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