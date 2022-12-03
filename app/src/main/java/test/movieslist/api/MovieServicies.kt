package test.movieslist.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import test.movieslist.data.models.ResultList
import test.movieslist.data.models.Result

interface MovieServicies {

    @GET("discover/movie?api_key=c9856d0cb57c3f14bf75bdc6c063b8f3")
    suspend fun getAllMovies() : Response<ResultList>

    @GET("movie/{movieId}?api_key=c9856d0cb57c3f14bf75bdc6c063b8f3")
    suspend fun getMovieId(
        @Path("movieId") movieId: Int
    ) : Response<Result>

    companion object {
        private const val BASE_URL = "https://api.themoviedb.org/3/"

        fun create(): MovieServicies {
            val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MovieServicies::class.java)
        }

        val api = create()
    }
}