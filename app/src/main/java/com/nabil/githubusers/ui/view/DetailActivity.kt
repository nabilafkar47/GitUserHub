package com.nabil.githubusers.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayoutMediator
import com.nabil.githubusers.R
import com.nabil.githubusers.data.database.Favorite
import com.nabil.githubusers.data.model.DetailResponse
import com.nabil.githubusers.databinding.ActivityDetailBinding
import com.nabil.githubusers.ui.adapter.SectionsPagerAdapter
import com.nabil.githubusers.ui.viewmodel.DetailViewModel
import com.nabil.githubusers.ui.viewmodel.ViewModelFavoriteFactory
import com.nabil.githubusers.utils.DateHelper

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var sectionsPagerAdapter: SectionsPagerAdapter
    private val viewModel by viewModels<DetailViewModel>(){
        ViewModelFavoriteFactory.getInstance(application)
    }
    private var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra("username") ?: ""

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = username
            setDisplayHomeAsUpEnabled(true)
        }

        viewModel.userDetail.observe(this) { userDetail ->
            setUserDetail(userDetail)
        }

        viewModel.isLoading.observe(this) { isLoading ->
            showLoading(isLoading)
        }

        if (savedInstanceState == null) {
            viewModel.getUserDetail(username)
        }

        sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = username

        binding.viewPager.adapter = sectionsPagerAdapter

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            if (position == 0) {
                tab.text = "Followers"
            } else {
                tab.text = "Following"
            }
        }.attach()

        viewModel.getFavoriteUserByUsername(username).observe(this) { favoriteUser ->
            isFavorite = favoriteUser != null
            updateFavoriteButton()
        }

        binding.fabFavorite.setOnClickListener{
            viewModel.userDetail.value?.let { userDetail ->
                if (isFavorite) {
                    showRemoveFavAlertDialog(userDetail.login)
                } else {
                    val favorite = Favorite(
                        userDetail.login,
                        userDetail.avatarUrl,
                        DateHelper.getCurrentDate()
                    )
                    viewModel.setFavorite(favorite)
                    Toast.makeText(this, "Successfully added $username to favorites", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun showRemoveFavAlertDialog(username: String) {
        MaterialAlertDialogBuilder(this, R.style.AlertDialogCustom)
            .setTitle("Delete Favorite Users")
            .setMessage("You have added $username to favorites. Do you want to delete it?")
            .setPositiveButton("Delete") { _, _ ->
                viewModel.deleteFavorite(username)
                Toast.makeText(this, "Successfully deleted $username from favorites", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun updateFavoriteButton() {
        binding.fabFavorite.setImageResource(if (isFavorite) R.drawable.ic_favorite_fill else R.drawable.ic_favorite)
    }

    private fun setUserDetail(userDetail: DetailResponse) {
        binding.apply {
            tvDetailName.text = userDetail.name ?: "No Name"
            tvDetailBio.text = userDetail.bio?.toString() ?: "This user hasn't written a bio yet"
            tvDetailLocation.text = userDetail.location ?: "No Location"
            tvDetailCompany.text = userDetail.company ?: "No Company"
            tvDetailFollowers.text = userDetail.followers.toString()
            tvDetailFollowing.text = userDetail.following.toString()
            tvDetailRepositories.text = userDetail.publicRepos.toString()

            Glide.with(this@DetailActivity)
                .load(userDetail.avatarUrl)
                .centerCrop()
                .into(ivDetailProfilePicture)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBarDetail.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun shareProfile() {
        viewModel.userDetail.value?.let { userDetail ->

            val username = userDetail.login
            val name = userDetail.name ?: "GitHub User"
            val followers = userDetail.followers
            val following = userDetail.following
            val publicRepos = userDetail.publicRepos

            val message = "Hey there, I'm $name, a passionate GitHub user with the username $username.\n\nLet's connect on GitHub! I have $followers followers and I'm currently following $following awesome individuals.\n\nCheck out my profile on GitHub and explore my $publicRepos repositories: https://github.com/$username"

            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, message)
            startActivity(Intent.createChooser(shareIntent, "Share via"))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_detail_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            R.id.menu_share -> {
                shareProfile()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}

