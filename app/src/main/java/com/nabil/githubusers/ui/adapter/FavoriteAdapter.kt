package com.nabil.githubusers.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nabil.githubusers.data.database.Favorite
import com.nabil.githubusers.databinding.ItemUserBinding
import com.nabil.githubusers.ui.view.DetailActivity
import com.nabil.githubusers.utils.FavoriteDiffCallback

class FavoriteAdapter :
    RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private val listFavorites = ArrayList<Favorite>()

    fun setData(listFavorites: List<Favorite>) {
        val diffCallback = FavoriteDiffCallback(this.listFavorites, listFavorites)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listFavorites.clear()
        this.listFavorites.addAll(listFavorites)
        diffResult.dispatchUpdatesTo(this)
    }

    class FavoriteViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(favorite: Favorite) {
            binding.apply {
                tvItemName.text = favorite.username
                tvItemLink.text = StringBuilder("Added at ").append(favorite.createdAt)
                Glide.with(itemView)
                    .load(favorite.avatarUrl)
                    .centerCrop()
                    .into(ivProfilePicture)

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra("username", favorite.username)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun getItemCount(): Int = listFavorites.size

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(listFavorites[position])
    }
}
