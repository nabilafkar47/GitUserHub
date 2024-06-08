package com.nabil.githubusers.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun setFavorite(favorite: Favorite)

    @Query("DELETE FROM Favorite WHERE username = :username")
    fun deleteFavorite(username: String)

    @Query("DELETE FROM Favorite")
    fun deleteAllFavorites()

    @Query("SELECT * FROM Favorite WHERE username = :username")
    fun getFavoriteUserByUsername(username: String): LiveData<Favorite>

    @Query("SELECT * from Favorite ORDER BY created_at DESC")
    fun getAllFavorites(): LiveData<List<Favorite>>
}