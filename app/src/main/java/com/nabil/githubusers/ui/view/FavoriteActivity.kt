package com.nabil.githubusers.ui.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.nabil.githubusers.R
import com.nabil.githubusers.databinding.ActivityFavoriteBinding
import com.nabil.githubusers.ui.adapter.FavoriteAdapter
import com.nabil.githubusers.ui.viewmodel.FavoriteViewModel
import com.nabil.githubusers.ui.viewmodel.ViewModelFavoriteFactory

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var favoriteAdapter: FavoriteAdapter
    private val viewModel by viewModels<FavoriteViewModel>(){
        ViewModelFavoriteFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setTitle(R.string.favorite)
            setDisplayHomeAsUpEnabled(true)
        }

        favoriteAdapter = FavoriteAdapter()

        binding.rvUserFavorite.layoutManager = LinearLayoutManager(this)
        binding.rvUserFavorite.adapter = favoriteAdapter

        viewModel.getAllFavorites().observe(this) { favorites ->
            if (favorites.isEmpty()) {
                binding.tvEmptyFavorite.visibility = View.VISIBLE
                favoriteAdapter.setData(emptyList())
            } else {
                binding.tvEmptyFavorite.visibility = View.GONE
                favoriteAdapter.setData(favorites)
            }
        }
    }

    private fun showRemoveAllFavAlertDialog() {
        MaterialAlertDialogBuilder(this, R.style.AlertDialogCustom)
            .setTitle("Delete All Favorite Users")
            .setMessage("Do you want to delete all your favorite users?")
            .setPositiveButton("Delete") { _, _ ->
                viewModel.deleteAllFavorites()
                Toast.makeText(this, "Successfully deleted all users from favorites", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_favorite_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            R.id.menu_remove -> {
                if (favoriteAdapter.itemCount > 0) {
                    showRemoveAllFavAlertDialog()
                } else {
                    Toast.makeText(this, "You don't have any favorite users", Toast.LENGTH_SHORT).show()
                }
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}