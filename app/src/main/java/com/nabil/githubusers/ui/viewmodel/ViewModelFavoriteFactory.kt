package com.nabil.githubusers.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFavoriteFactory private constructor(private val mApplication: Application) : ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var INSTANCE: ViewModelFavoriteFactory? = null
        @JvmStatic
        fun getInstance(application: Application): ViewModelFavoriteFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFavoriteFactory::class.java) {
                    INSTANCE = ViewModelFavoriteFactory(application)
                }
            }
            return INSTANCE as ViewModelFavoriteFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(mApplication) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}