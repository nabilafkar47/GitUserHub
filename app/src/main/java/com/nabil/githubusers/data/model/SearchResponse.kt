package com.nabil.githubusers.data.model

import com.google.gson.annotations.SerializedName

data class SearchResponse(

	@field:SerializedName("items")
	val items: List<User>
)

data class User(

	@field:SerializedName("login")
	val login: String,

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("html_url")
	val htmlUrl: String,
)
