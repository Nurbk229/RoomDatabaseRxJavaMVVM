package com.example.nurbk.ps.roomdatabaserxjavamvvm.adapter

import android.app.Activity
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.nurbk.ps.roomdatabaserxjavamvvm.R
import com.example.nurbk.ps.roomdatabaserxjavamvvm.Utils.DataConverter
import com.example.nurbk.ps.roomdatabaserxjavamvvm.model.Genre
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.genre_item_layout.view.*


class GenreAdapter(val itemClick: OnGenreClickListener) :
    RecyclerView.Adapter<GenreAdapter.AdapterViewHolder>() {


    inner class AdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterViewHolder {

        return AdapterViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.genre_item_layout, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) {
        val genre = getGenreAt(position)

        holder.itemView.apply {
            txtGenre.text = genre.genre
//            val image = MediaStore
//            .Images.Media.getBitmap(
//                activity.contentResolver,
//                Uri.parse(genre.image)
//            )

//            val image = genre.image.substring(genre.image.lastIndexOf('/')+1)
//            ivGenre.setImageURI(Uri.parse(genre.image))


//   Log.e("tttte",image)
//
            ivGenre.setImageBitmap(
                DataConverter
                    .convertByteArray2Image(genre.image)
            )

            cardViewGenre.setOnClickListener {
                itemClick.onGenreClick(genre)
            }

        }

    }


    private val differCallback =
        object : DiffUtil.ItemCallback<Genre>() {
            override fun areItemsTheSame(oldItem: Genre, newItem: Genre): Boolean {
                return oldItem.uid == newItem.uid
            }

            override fun areContentsTheSame(oldItem: Genre, newItem: Genre): Boolean {
                return oldItem.genre == newItem.genre

            }
        }


    var differ = AsyncListDiffer(this, differCallback)
        .also {
            notifyDataSetChanged()

        }

    fun getGenreAt(position: Int): Genre {
        return differ.currentList[position]
    }

    interface OnGenreClickListener {
        fun onGenreClick(genre: Genre)
    }

}