package com.nabil.githubusers.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.nabil.githubusers.data.database.Favorite
import com.nabil.githubusers.data.database.FavoriteDao
import com.nabil.githubusers.data.database.FavoriteRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {
    private val mFavoriteDao: FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteRoomDatabase.getDatabase(application)
        mFavoriteDao = db.favoriteDao()
    }

    fun setFavorite(favorite: Favorite) {
        executorService.execute { mFavoriteDao.setFavorite(favorite) }
    }

    fun deleteFavorite(username:String) {
        executorService.execute { mFavoriteDao.deleteFavorite(username) }
    }

    fun deleteAllFavorites() {
        executorService.execute { mFavoriteDao.deleteAllFavorites() }
    }

    fun getFavoriteUserByUsername(username: String): LiveData<Favorite> =
        mFavoriteDao.getFavoriteUserByUsername(username)

    fun getAllFavorites(): LiveData<List<Favorite>> = mFavoriteDao.getAllFavorites()
}