package com.example.nurbk.ps.roomdatabaserxjavamvvm.view

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nurbk.ps.roomdatabaserxjavamvvm.R
import com.example.nurbk.ps.roomdatabaserxjavamvvm.adapter.GenreAdapter
import com.example.nurbk.ps.roomdatabaserxjavamvvm.factory.GenreViewModelProviderFactory
import com.example.nurbk.ps.roomdatabaserxjavamvvm.model.Genre
import com.example.nurbk.ps.roomdatabaserxjavamvvm.viewModel.GenreActivityViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_genre.*


class GenreFragment : Fragment(R.layout.fragment_genre),
    GenreAdapter.OnGenreClickListener,
    CreateGenreDialog.CreateGenreListener {

    private val TAG = "GenreActivity"


    private var genreActivityViewModel: GenreActivityViewModel? = null
    private var genreAdapter: GenreAdapter? = null

    private val compositeDisposable by lazy { CompositeDisposable() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        requireActivity().toolbar_title.text = "Genre Movie"

        (activity as AppCompatActivity).supportActionBar!!
            .setDisplayHomeAsUpEnabled(false)


        val viewModelProviderFactory =
            GenreViewModelProviderFactory(
                requireActivity().application
            )
        genreActivityViewModel =
            ViewModelProvider(this, viewModelProviderFactory)[GenreActivityViewModel::class.java]

        recycler_view.layoutManager = LinearLayoutManager(requireActivity())
        recycler_view.setHasFixedSize(true)

        val disposable = genreActivityViewModel!!.getAllGenre()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ list ->
                Log.d(TAG, "accept: Called");
                setDataToRecyclerView(list).also {
                    progress.visibility = View.GONE
                    genreAdapter!!.differ.submitList(list)
                }

            }, {

            })
        compositeDisposable.add(disposable)

        genreActivityViewModel!!.getIsLoading().observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "onChanged: $it")
            if (it != null) {
                if (it) {
                    progress.visibility = View.VISIBLE
                } else {
                    progress.visibility = View.GONE
                }
            }
        })

        fabG.setOnClickListener {
            Toast.makeText(requireActivity(), "asdfasd", Toast.LENGTH_LONG).show()
            openCreateGenreDialog()

        }

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(
                viewHolder: RecyclerView.ViewHolder,
                direction: Int
            ) {
                genreActivityViewModel!!.deleteGenre(genreAdapter!!.getGenreAt(viewHolder.adapterPosition))
            }
        }).attachToRecyclerView(recycler_view)


    }

    private fun setDataToRecyclerView(genres: List<Genre>) {
        genreAdapter = GenreAdapter(this)
        genreAdapter!!.differ.submitList(genres)
        recycler_view.adapter = genreAdapter
    }


    override fun onGenreClick(genre: Genre) {
        Log.d(TAG, "onGenreClick: onItemClick")
        moveToMoviesActivity(genre)
    }

    private fun moveToMoviesActivity(genre: Genre) {
        Bundle().apply {
            putString("genre", genre.genre)
            putInt("uid", genre.uid)
        }.also {
            findNavController()
                .navigate(R.id.action_genreFragment_to_moviesFragment, it)
        }
    }

    override fun saveNewGenre(genre: Genre?) {
        Log.d(TAG, "saveNewGenre: ${genre!!.genre}")
        genreActivityViewModel!!.insertGenre(genre)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    fun openCreateGenreDialog() {
        val createDialog = CreateGenreDialog(this)
        createDialog.show(requireActivity().supportFragmentManager, "")
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        requireActivity().menuInflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_delete -> {
                genreActivityViewModel!!.deleteAllGenre()
                    .also {
                        genreAdapter!!.notifyDataSetChanged()
                    }

                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }


    }

}