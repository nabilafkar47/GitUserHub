package com.nabil.githubusers.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nabil.githubusers.data.api.ApiConfig
import com.nabil.githubusers.data.database.Favorite
import com.nabil.githubusers.data.model.DetailResponse
import com.nabil.githubusers.data.repository.FavoriteRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : ViewModel() {

    companion object {
        private val TAG = DetailViewModel::class.simpleName
    }

    private val _userDetail = MutableLiveData<DetailResponse>()
    val userDetail: LiveData<DetailResponse> = _userDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val mFavoriteRepository = FavoriteRepository(application)

    fun setFavorite(favorite: Favorite) {
        mFavoriteRepository.setFavorite(favorite)
    }

    fun deleteFavorite(username: String) {
        mFavoriteRepository.deleteFavorite(username)
    }

    fun getFavoriteUserByUsername(username: String): LiveData<Favorite> =
        mFavoriteRepository.getFavoriteUserByUsername(username)

    fun getUserDetail(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetail(username)

        client.enqueue(object : Callback<DetailResponse> {
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _userDetail.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}

