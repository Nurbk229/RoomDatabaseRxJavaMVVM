package com.example.nurbk.ps.roomdatabaserxjavamvvm.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.nurbk.ps.roomdatabaserxjavamvvm.db.GenreDao
import com.example.nurbk.ps.roomdatabaserxjavamvvm.db.MovieDao
import com.example.nurbk.ps.roomdatabaserxjavamvvm.db.MovieDatabase
import com.example.nurbk.ps.roomdatabaserxjavamvvm.model.Genre
import com.example.nurbk.ps.roomdatabaserxjavamvvm.model.Movie
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class MovieRepository(val application: Application) {

    private val TAG = "MovieRepository"

    private var genreDao: GenreDao? = null
    private var movieDao: MovieDao? = null
    private var isLoading = MutableLiveData<Boolean>()
    val compositeDisposable = CompositeDisposable()

    init {

        val movieDatabase = MovieDatabase.invoke(application)
        genreDao = movieDatabase.genreDao()
        movieDao = movieDatabase.movieDao()
    }


    fun getAllGenre(): Single<List<Genre>> {
        isLoading.value = true
        return genreDao!!.getALLGenre()

    }

    fun getIsLoading(): MutableLiveData<Boolean> {
        return isLoading
    }


    fun getAllMovies(genre_id: Int): Flowable<List<Movie>> {
        return movieDao!!.getAllMoviesByGenreId(genre_id)
    }

    fun insertGenre(genre: Genre) {
        isLoading.value = true
        Completable.fromAction {
                genreDao!!.insert(genre)
            }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                    Log.d(TAG, "onSubscribe: Called")
                }

                override fun onError(e: Throwable) {
                    Log.d(TAG, "onError: " + e.message)
                }

                override fun onComplete() {
                    isLoading.value = false
                }
            })
    }

    fun updateGenre(genre: Genre) {
        isLoading.value = true
        Completable.fromAction {
                genreDao!!.update(genre)
            }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                    Log.d(TAG, "onSubscribe: Called")
                }

                override fun onError(e: Throwable) {
                    Log.d(TAG, "onError: " + e.message)
                }

                override fun onComplete() {
                    isLoading.value = false
                }
            })

    }

    fun deleteGenre(genre: Genre) {
        isLoading.value = true
        Completable.fromAction {
                genreDao!!.delete(genre)
            }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                    Log.d(TAG, "onSubscribe: Called")
                }

                override fun onError(e: Throwable) {
                    Log.d(TAG, "onError: " + e.message)
                }

                override fun onComplete() {
                    Log.d(TAG, "onComplete: Called")
                    deleteAllMoviesByGenre(genre.uid)
                    isLoading.value = false
                }
            })
    }

    fun deleteAllGenre() {
        isLoading.value = true
        Completable.fromAction {
                genreDao!!.deleteAllGenre()
            }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                    Log.d(TAG, "onSubscribe: Called")
                }

                override fun onError(e: Throwable) {
                    Log.d(TAG, "onError: " + e.message)
                }

                override fun onComplete() {
                    Log.d(TAG, "onComplete: Called")
                    deleteAllMovies();
                    isLoading.value = false
                }
            })
    }


    fun insertMovie(movie: Movie) {
        isLoading.value = true
        Completable.fromAction {
                movieDao!!.insert(movie)
            }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                    Log.d(TAG, "onSubscribe: Called")
                }

                override fun onError(e: Throwable) {
                    Log.d(TAG, "onError: " + e.message)
                }

                override fun onComplete() {
                    Log.d(TAG, "onComplete: Called")
                    isLoading.value = false
                }
            })
    }

    fun updateMovie(movie: Movie) {
        Completable.fromAction {
                movieDao!!.update(movie)
            }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                    Log.d(TAG, "onSubscribe: Called")
                }

                override fun onError(e: Throwable) {
                    Log.d(TAG, "onError: " + e.message)
                }

                override fun onComplete() {
                    Log.d(TAG, "onComplete: Called")
                    isLoading.value = false
                }
            })
    }

    fun deleteMovie(movie: Movie) {
        isLoading.value = true
        Completable.fromAction {
                movieDao!!.delete(movie)
            }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                    Log.d(TAG, "onSubscribe: Called")
                }

                override fun onError(e: Throwable) {
                    Log.d(TAG, "onError: " + e.message)
                }

                override fun onComplete() {
                    Log.d(TAG, "onComplete: Called")
                    isLoading.value = false
                }
            })
    }

    fun deleteAllMovies() {
        isLoading.value = true
        Completable.fromAction {
                movieDao!!.deleteAllMovie()
            }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                    Log.d(TAG, "onSubscribe: Called")
                }

                override fun onError(e: Throwable) {
                    Log.d(TAG, "onError: " + e.message)
                }

                override fun onComplete() {
                    Log.d(TAG, "onComplete: Called")
                    isLoading.value = false
                }
            })
    }

    fun deleteAllMoviesByGenre(genre_id: Int) {
        isLoading.value = true
        Completable.fromAction {
                movieDao!!.deleteAllMovieUnderGenre(genre_id)
            }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                    Log.d(TAG, "onSubscribe: Called")
                }

                override fun onError(e: Throwable) {
                    Log.d(TAG, "onError: " + e.message)
                }

                override fun onComplete() {
                    Log.d(TAG, "onComplete: Called")
                    isLoading.value = false
                }
            })
    }


}