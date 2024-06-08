package com.nabil.githubusers.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nabil.githubusers.data.model.User
import com.nabil.githubusers.databinding.ItemUserBinding
import com.nabil.githubusers.ui.view.DetailActivity

class MainAdapter :
    RecyclerView.Adapter<MainAdapter.UserViewHolder>() {

    private var list: List<User> = emptyList()

    fun setData(users: List<User>) {
        list = users
        notifyDataSetChanged()
    }

    class UserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {
            binding.tvItemName.text = user.login
            binding.tvItemLink.text = user.htmlUrl
            Glide.with(itemView.context)
                .load(user.avatarUrl)
                .centerCrop()
                .into(binding.ivProfilePicture)

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra("username", user.login)
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = list[position]
        holder.bind(user)
    }
}