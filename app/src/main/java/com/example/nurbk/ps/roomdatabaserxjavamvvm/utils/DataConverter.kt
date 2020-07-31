package com.example.nurbk.ps.roomdatabaserxjavamvvm.Utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream
import android.graphics.Matrix

class DataConverter {

    companion object {
        fun convertImage2ByteArray(bitmap: Bitmap): ByteArray? {
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, stream)


            return stream.toByteArray()
        }

        fun convertByteArray2Image(array: ByteArray): Bitmap? {
            return BitmapFactory.decodeByteArray(array, 0, array.size)
        }


    }



}