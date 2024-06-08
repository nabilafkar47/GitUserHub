package com.nabil.githubusers.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.nabil.githubusers.R
import com.nabil.githubusers.databinding.ActivityMainBinding
import com.nabil.githubusers.ui.adapter.MainAdapter
import com.nabil.githubusers.ui.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var userAdapter: MainAdapter
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setTitle(R.string.app_name)
            setLogo(R.drawable.logo_github)
        }

        userAdapter = MainAdapter()

        binding.rvUserMain.layoutManager = LinearLayoutManager(this)
        binding.rvUserMain.adapter = userAdapter

        viewModel.listUsers.observe(this) { users ->
            userAdapter.setData(users)
        }

        viewModel.isEmpty.observe(this) { isEmpty ->
            showEmptyText(isEmpty)
        }

        viewModel.isLoading.observe(this) { isLoading ->
            showLoading(isLoading)
        }

        binding.searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrBlank()) {
                    viewModel.searchUsers(query)
                    binding.searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBarSearch.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showEmptyText(isEmpty: Boolean) {
        binding.tvEmptyData.visibility = if (isEmpty) View.VISIBLE else View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_settings -> {
                startActivity(Intent(this, SettingActivity::class.java))
                true
            }
            R.id.menu_favorite -> {
                startActivity(Intent(this, FavoriteActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}