package com.example.nurbk.ps.roomdatabaserxjavamvvm.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.nurbk.ps.roomdatabaserxjavamvvm.model.Genre
import com.example.nurbk.ps.roomdatabaserxjavamvvm.model.Movie
import com.example.nurbk.ps.roomdatabaserxjavamvvm.repository.MovieRepository

class GenreActivityViewModel(application: Application) : AndroidViewModel(application) {

    val movieRepository by lazy { MovieRepository(application) }


    fun getAllGenre() =
        movieRepository.getAllGenre()

    fun getIsLoading() =
        movieRepository.getIsLoading()

    fun insertGenre(genre: Genre) =
        movieRepository.insertGenre(genre)

    fun updateGenre(genre: Genre) =
        movieRepository.updateGenre(genre)

    fun deleteGenre(genre: Genre) =
        movieRepository.deleteGenre(genre)

    fun deleteAllGenre() =
        movieRepository.deleteAllGenre()


}