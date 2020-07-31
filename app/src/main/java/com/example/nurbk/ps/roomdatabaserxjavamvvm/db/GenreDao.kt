package com.example.nurbk.ps.roomdatabaserxjavamvvm.db

import androidx.room.*
import com.example.nurbk.ps.roomdatabaserxjavamvvm.model.Genre
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface GenreDao {

    @Insert
    fun insert(genre: Genre)

    @Update
    fun update(genre: Genre)

    @Delete
    fun delete(genre: Genre)

    @Query("DELETE FROM genre_table")
    fun deleteAllGenre()

    @Query("SELECT * FROM genre_table")
    fun getALLGenre(): Single<List<Genre>>

}