package test.movieslist.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import test.movieslist.data.models.ResultList
import test.movieslist.data.models.Result
import test.movieslist.data.repository.MoviesRepository
import test.movieslist.utilities.Resource
import test.movieslist.utilities.HandleApiResponse.Companion.handleApiResponse

class MoviesVewModel(private val moviesRepository: MoviesRepository) : ViewModel() {

    val moviesListResponse : MutableLiveData<Resource<ResultList>> = MutableLiveData()
    val movieSelected : MutableLiveData<Resource<Result>> = MutableLiveData()

    fun getMovies() = viewModelScope.launch {
        moviesListResponse.postValue(Resource.Loading())
        val response = moviesRepository.getAllMovies()
        moviesListResponse.postValue(handleApiResponse(response))
    }

    fun getSelectedMovie(movieId: Int) = viewModelScope.launch {
        movieSelected.postValue(Resource.Loading())
        val response = moviesRepository.getMovieById(movieId)
        movieSelected.postValue(handleApiResponse(response))
    }

}