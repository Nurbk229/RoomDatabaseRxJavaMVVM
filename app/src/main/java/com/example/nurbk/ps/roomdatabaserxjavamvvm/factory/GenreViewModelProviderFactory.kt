package com.example.nurbk.ps.roomdatabaserxjavamvvm.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.nurbk.ps.roomdatabaserxjavamvvm.viewModel.GenreActivityViewModel

class GenreViewModelProviderFactory(
    val application: Application,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return GenreActivityViewModel(
            application
        ) as T
    }
}