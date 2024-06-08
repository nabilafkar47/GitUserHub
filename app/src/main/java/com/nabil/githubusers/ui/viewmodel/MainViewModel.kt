package com.nabil.githubusers.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nabil.githubusers.data.api.ApiConfig
import com.nabil.githubusers.data.model.SearchResponse
import com.nabil.githubusers.data.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    companion object {
        private val TAG = MainViewModel::class.simpleName
    }

    private val _listUsers = MutableLiveData<List<User>>()
    val listUsers: LiveData<List<User>> = _listUsers

    private val _isEmpty = MutableLiveData<Boolean>()
    val isEmpty: LiveData<Boolean> = _isEmpty

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun searchUsers(query: String) {
        _isLoading.value = true
        _isEmpty.value = false
        val client = ApiConfig.getApiService().getUsers(query)

        client.enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val users = response.body()?.items ?: emptyList()
                    _listUsers.value = users
                    _isEmpty.value = users.isEmpty()
                } else {
                    _isEmpty.value = true
                    Log.e(TAG, "onResponse: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                _isLoading.value = false
                _isEmpty.value = true
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}
