package com.nabil.githubusers.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nabil.githubusers.data.model.User
import com.nabil.githubusers.databinding.ItemUserBinding

class FollowAdapter(private val userList: List<User>) :
    RecyclerView.Adapter<FollowAdapter.UserViewHolder>() {

    inner class UserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {
            binding.apply {
                tvItemName.text = user.login
                tvItemLink.text = user.htmlUrl

                Glide.with(itemView)
                    .load(user.avatarUrl)
                    .centerCrop()
                    .into(ivProfilePicture)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]
        holder.bind(user)
    }
}
