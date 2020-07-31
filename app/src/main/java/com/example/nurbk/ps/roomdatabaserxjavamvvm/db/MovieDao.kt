package com.example.nurbk.ps.roomdatabaserxjavamvvm.db

import androidx.room.*
import com.example.nurbk.ps.roomdatabaserxjavamvvm.model.Movie

import io.reactivex.Flowable

@Dao
interface MovieDao {

    @Insert
    fun insert(movie: Movie)

    @Update
    fun update(movie: Movie)

    @Delete
    fun delete(movie: Movie)

    @Query("DELETE FROM movie_table")
    fun deleteAllMovie()

    @Query("SELECT * FROM movie_table WHERE genre_id LIKE :genre_id")
    fun getAllMoviesByGenreId(genre_id: Int): Flowable<List<Movie>>

    @Query("DELETE FROM movie_table WHERE genre_id==:genre_id")
    fun deleteAllMovieUnderGenre(genre_id: Int)
}