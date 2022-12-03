package test.movieslist.data.repository

import test.movieslist.api.MovieServicies

class MoviesRepository {

    suspend fun getAllMovies() = MovieServicies.api.getAllMovies()
    suspend fun getMovieById(movieId: Int) = MovieServicies.api.getMovieId(movieId)
}