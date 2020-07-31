package com.example.nurbk.ps.roomdatabaserxjavamvvm.view

import android.Manifest
import android.app.Activity.RESULT_OK
import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaScannerConnection
import android.media.MediaScannerConnection.OnScanCompletedListener
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialogFragment
import com.example.nurbk.ps.roomdatabaserxjavamvvm.R
import com.example.nurbk.ps.roomdatabaserxjavamvvm.Utils.DataConverter
import com.example.nurbk.ps.roomdatabaserxjavamvvm.model.Genre
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.vanniktech.rxpermission.RealRxPermission
import id.zelory.compressor.Compressor
import kotlinx.android.synthetic.main.genre_layout_dialog.view.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import kotlin.random.Random


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class CreateGenreDialog(var mListener: CreateGenreListener) : AppCompatDialogFragment() {


    val REQUEST_CODE_IMAGE = 111
    private val TAG = "CreateGenreDialog"

    private var mBitmap: Bitmap? = null

    var selectedImage: Uri? = null
    var result: String? = null
    private lateinit var root: View
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = MaterialAlertDialogBuilder(requireActivity())
        root = requireActivity().layoutInflater.inflate(R.layout.genre_layout_dialog, null)

        builder.setView(root)
        builder.setCancelable(true)
        builder.setTitle(null)

        root.ivAddGenre.setOnClickListener {
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

        root.btn_save.setOnClickListener {
            val name = root.et_genre.text.toString()
            if (name.trim().isEmpty() || selectedImage == null) {
                Toast.makeText(requireActivity(), "Name and photo are required", Toast.LENGTH_LONG)
                    .show()
                return@setOnClickListener
            }

            val genre = Genre(
                0, name,
                DataConverter.convertImage2ByteArray(mBitmap!!)!!

            )
//            DataConverter.convertImage2ByteArray(mBitmap!!)!!
            mListener.saveNewGenre(genre)
            dismiss()
        }


        return builder.create()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_IMAGE) {
            if (resultCode == RESULT_OK) {
                selectedImage = data!!.data
                try {
                    val selectedImage = data.data

                    root.ivAddGenre.setImageURI(selectedImage)
                    mBitmap = MediaStore
                        .Images.Media.getBitmap(
                            requireActivity().contentResolver,
                            selectedImage
                        )

                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == 1) {
//            if (resultCode == RESULT_OK) {
//                val selectedImage = data!!.data
//                val imageFile = File(getRealPathFromURI(selectedImage!!))
//                try {
//                    mBitmap = Compressor(activity).compressToBitmap(imageFile)
//                    root.ivAddGenre.setImageBitmap(mBitmap)
//                    Log.d("CreateGenreDialog", "onActivityResult: ")
//                } catch (e: IOException) {
//                    e.printStackTrace()
//                }
//            }
//        }
//    }

    interface CreateGenreListener {
        fun saveNewGenre(genre: Genre?)
    }

//    private fun getRealPathFromURI(contentURI: Uri): String? {
//        val result: String?
//        val cursor =
//            requireActivity().contentResolver.query(contentURI, null, null, null, null)
//        if (cursor == null) { // Source is Dropbox or other similar local file path
//            result = contentURI.path
//        } else {
//            cursor.moveToFirst()
//            val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
//            result = cursor.getString(idx)
//            cursor.close()
//        }
//        return result
//    }




}