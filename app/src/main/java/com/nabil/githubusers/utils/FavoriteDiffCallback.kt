package com.nabil.githubusers.utils

import androidx.recyclerview.widget.DiffUtil
import com.nabil.githubusers.data.database.Favorite

class FavoriteDiffCallback(
    private val oldFavoriteLists: List<Favorite>,
    private val newFavoriteLists: List<Favorite>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldFavoriteLists.size

    override fun getNewListSize(): Int = newFavoriteLists.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldFavoriteLists[oldItemPosition].username == newFavoriteLists[newItemPosition].username
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldFavoriteUser = oldFavoriteLists[oldItemPosition]
        val newFavoriteUser = newFavoriteLists[newItemPosition]
        return oldFavoriteUser.username == newFavoriteUser.username
    }
}