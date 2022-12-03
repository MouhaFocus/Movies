package test.movieslist.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import test.movieslist.data.repository.MoviesRepository

class MoviesViewModelFactory():ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(MoviesVewModel::class.java) ->
                MoviesVewModel(MoviesRepository())
            else -> throw IllegalArgumentException("Unexpected model class $modelClass")
        } as T
    }
}