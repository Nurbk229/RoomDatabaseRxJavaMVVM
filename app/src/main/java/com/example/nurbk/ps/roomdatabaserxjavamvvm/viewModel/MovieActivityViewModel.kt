package com.example.nurbk.ps.roomdatabaserxjavamvvm.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.nurbk.ps.roomdatabaserxjavamvvm.model.Genre
import com.example.nurbk.ps.roomdatabaserxjavamvvm.model.Movie
import com.example.nurbk.ps.roomdatabaserxjavamvvm.repository.MovieRepository
import io.reactivex.Flowable

class MovieActivityViewModel(application: Application) :
    AndroidViewModel(application) {

    val movieRepository by lazy { MovieRepository(application) }

    fun getAllMovies(genre_id: Int) =
        movieRepository.getAllMovies(genre_id)

    fun getIsLoading() =
        movieRepository.getIsLoading()

    fun insertMovie(movie: Movie) =
        movieRepository.insertMovie(movie)

    fun updateMovie(movie: Movie) =
        movieRepository.updateMovie(movie)

    fun deleteMovie(movie: Movie) =
        movieRepository.deleteMovie(movie)

    fun deleteAllMovies(genre_id: Int) =
        movieRepository.deleteAllMoviesByGenre(genre_id)


}