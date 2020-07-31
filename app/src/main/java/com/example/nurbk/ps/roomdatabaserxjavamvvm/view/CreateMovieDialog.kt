package com.example.nurbk.ps.roomdatabaserxjavamvvm.view

import android.Manifest
import android.app.Activity.RESULT_OK
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatDialogFragment
import com.example.nurbk.ps.roomdatabaserxjavamvvm.R
import com.example.nurbk.ps.roomdatabaserxjavamvvm.Utils.DataConverter
import com.example.nurbk.ps.roomdatabaserxjavamvvm.model.Genre
import com.example.nurbk.ps.roomdatabaserxjavamvvm.model.Movie
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.vanniktech.rxpermission.RealRxPermission
import kotlinx.android.synthetic.main.movie_layout_dialog.*
import kotlinx.android.synthetic.main.movie_layout_dialog.view.*
import java.io.IOException


class CreateMovieDialog(var mListener: CreateMovieListener) : AppCompatDialogFragment() {


    val REQUEST_CODE_IMAGE = 111
    private val TAG = "CreateGenreDialog"

    private var mBitmap: Bitmap? = null

    var selectedImage: Uri? = null

    private lateinit var root: View
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = MaterialAlertDialogBuilder(requireActivity())
        root = requireActivity().layoutInflater.inflate(R.layout.movie_layout_dialog, null)

        builder.setView(root)
        builder.setCancelable(true)
        builder.setTitle(null)

        root.ivMovie.setOnClickListener {
            RealRxPermission.getInstance(requireActivity())
                .request(Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe()

            Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
                .also {
                    startActivityForResult(it, REQUEST_CODE_IMAGE)
                }


        }

        root.btnSaveMovie.setOnClickListener {
            val title = root.titleMovie.text.toString()
            val year = root.yearMovie.text.toString()
            val rating = (root.ratingMovie.rating * 2.00).toString()

            if (title.isNotEmpty() && year.isNotEmpty() && rating.isNotEmpty() && selectedImage != null) {
                val movie = Movie(
                    0,
                    0,
                    title,
                    year,
                    rating,
                    DataConverter.convertImage2ByteArray(mBitmap!!)!!
                )
                mListener!!.saveNewMovie(movie)
                dismiss()
            }

        }


        return builder.create()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_IMAGE) {
            if (resultCode == RESULT_OK) {
                selectedImage = data!!.data
                try {
//                    root.ivMovie.setImageURI(selectedImage)
                    mBitmap = MediaStore
                        .Images.Media.getBitmap(
                            requireActivity().contentResolver,
                            selectedImage
                        )
//
//
//
                    root.ivMovie.setImageBitmap(mBitmap)
//                    Log.d(TAG, "onActivityResult: ")
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    interface CreateMovieListener {
        fun saveNewMovie(movie: Movie)
    }


}