package com.example.nurbk.ps.roomdatabaserxjavamvvm.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.nurbk.ps.roomdatabaserxjavamvvm.R
import com.example.nurbk.ps.roomdatabaserxjavamvvm.Utils.DataConverter
import com.example.nurbk.ps.roomdatabaserxjavamvvm.model.Movie
import kotlinx.android.synthetic.main.single_movie_layout.view.*

class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {


    inner class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MoviesViewHolder {

        return MoviesViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.single_movie_layout, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val movie = getMovieAt(position)

        holder.itemView.apply {
            year.text = movie.release_data
            title.text = movie.title
//            image_view.setImageURI(Uri.parse(movie.image))

            image_view.setImageBitmap(DataConverter.convertByteArray2Image(movie.image))
        }

    }


    private val differCallback =
        object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.uid == newItem.uid
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.genre_id == newItem.genre_id
                        && oldItem.rating == oldItem.rating
                        && oldItem.release_data == oldItem.release_data
            }
        }


    var differ = AsyncListDiffer(this, differCallback)
        .also {
            notifyDataSetChanged()
        }

    private fun getMovieAt(position: Int): Movie {
        return differ.currentList[position]
    }

}