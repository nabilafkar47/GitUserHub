package com.nabil.githubusers.ui.view

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.nabil.githubusers.R
import com.nabil.githubusers.databinding.ActivitySettingBinding
import com.nabil.githubusers.ui.viewmodel.SettingViewModel
import com.nabil.githubusers.ui.viewmodel.ViewModelSettingFactory
import com.nabil.githubusers.utils.SettingPreferences
import com.nabil.githubusers.utils.dataStore

class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setTitle(R.string.settings)
            setDisplayHomeAsUpEnabled(true)
        }

        val pref = SettingPreferences.getInstance(application.dataStore)
        val settingViewModel = ViewModelProvider(this, ViewModelSettingFactory(pref))[SettingViewModel::class.java]

        settingViewModel.getThemeSettings().observe(this) { theme: Int ->
            when (theme) {
                0 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    binding.radioAuto.isChecked = true
                }
                1 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    binding.radioLight.isChecked = true
                }
                2 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    binding.radioDark.isChecked = true
                }
            }
        }

        binding.radioGroup.setOnCheckedChangeListener { _, id ->
            when (id) {
                binding.radioAuto.id -> settingViewModel.saveThemeSetting(0)
                binding.radioLight.id -> settingViewModel.saveThemeSetting(1)
                binding.radioDark.id -> settingViewModel.saveThemeSetting(2)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}