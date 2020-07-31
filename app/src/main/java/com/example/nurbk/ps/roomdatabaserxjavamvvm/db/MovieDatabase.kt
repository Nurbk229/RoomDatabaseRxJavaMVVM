package com.example.nurbk.ps.roomdatabaserxjavamvvm.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.nurbk.ps.roomdatabaserxjavamvvm.model.Genre
import com.example.nurbk.ps.roomdatabaserxjavamvvm.model.Movie

@Database(entities = [Genre::class, Movie::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
    abstract fun genreDao(): GenreDao


    companion object {
        @Volatile
        private var instance: MovieDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) =
            instance
                ?: synchronized(LOCK) {
                    instance
                        ?: createDatabase(
                            context
                        ).also {
                            instance = it
                        }
                }
        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                    context.applicationContext,
                    MovieDatabase::class.java,
                    "article_db.db"
                ).fallbackToDestructiveMigration()
                .build()

    }

}