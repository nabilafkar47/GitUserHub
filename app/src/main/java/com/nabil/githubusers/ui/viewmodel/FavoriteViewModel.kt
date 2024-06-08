package com.nabil.githubusers.ui.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.nabil.githubusers.data.database.Favorite
import com.nabil.githubusers.data.repository.FavoriteRepository

class FavoriteViewModel(application: Application) : ViewModel() {
    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    fun getAllFavorites(): LiveData<List<Favorite>> = mFavoriteRepository.getAllFavorites()

    fun deleteAllFavorites() {
        mFavoriteRepository.deleteAllFavorites()
    }
}