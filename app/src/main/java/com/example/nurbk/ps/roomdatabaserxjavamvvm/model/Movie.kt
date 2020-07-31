package com.example.nurbk.ps.roomdatabaserxjavamvvm.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "movie_table")
class Movie(
    @PrimaryKey(autoGenerate = true)
    val uid: Int,
    var genre_id: Int,
    val title: String,
    val rating: String,
    val release_data: String,
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    val image: ByteArray
) {
}