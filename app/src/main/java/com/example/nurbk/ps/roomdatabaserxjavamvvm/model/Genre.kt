package com.example.nurbk.ps.roomdatabaserxjavamvvm.model

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "genre_table")
class Genre(
    @PrimaryKey(autoGenerate = true)
    val uid: Int,
    val genre: String,
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    val image: ByteArray
) {
}