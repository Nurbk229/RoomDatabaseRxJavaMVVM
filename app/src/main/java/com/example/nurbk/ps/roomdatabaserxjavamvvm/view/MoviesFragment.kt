package com.example.nurbk.ps.roomdatabaserxjavamvvm.view

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nurbk.ps.roomdatabaserxjavamvvm.R
import com.example.nurbk.ps.roomdatabaserxjavamvvm.adapter.MoviesAdapter
import com.example.nurbk.ps.roomdatabaserxjavamvvm.model.Movie
import com.example.nurbk.ps.roomdatabaserxjavamvvm.viewModel.MovieActivityViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_movies.*


class MoviesFragment : Fragment(R.layout.fragment_movies),
    CreateMovieDialog.CreateMovieListener {


    private val compositeDisposable by lazy { CompositeDisposable() }
    private var movieActivityViewModel: MovieActivityViewModel? = null
    private val adapter by lazy {
        MoviesAdapter()
    }

    private var mGenre = ""
    private var genre_id = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar!!
            .setDisplayHomeAsUpEnabled(true)
        requireActivity().toolbarG.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
        setHasOptionsMenu(true)

        requireActivity().toolbarG.title = " "


        movieActivityViewModel = ViewModelProvider(this).get(
            MovieActivityViewModel::class.java
        )


        val extras = arguments

        if (extras != null) {
            mGenre = extras.getString("genre", "")
            genre_id = extras.getInt("uid", 0)

            requireActivity().toolbar_title.text = mGenre
        }
        val disposable = movieActivityViewModel!!.getAllMovies(genre_id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                adapter.differ.submitList(it)
            }, {

            })
        rvMovie.layoutManager = GridLayoutManager(requireActivity(), 3)
        rvMovie.setHasFixedSize(true)
        rvMovie.adapter = adapter
        compositeDisposable.add(disposable)


        fab.setOnClickListener {
            openAddMovieDialog()
        }

    }

    private fun openAddMovieDialog() {
        val createDialog = CreateMovieDialog(this)
        createDialog.show(requireActivity().supportFragmentManager, "")
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        requireActivity().menuInflater.inflate(R.menu.menu_main, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_delete -> {
                movieActivityViewModel!!.deleteAllMovies(genre_id)

                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }

    }

    override fun saveNewMovie(movie: Movie) {
        movie.genre_id = genre_id
        movieActivityViewModel!!.insertMovie(movie)
    }
}