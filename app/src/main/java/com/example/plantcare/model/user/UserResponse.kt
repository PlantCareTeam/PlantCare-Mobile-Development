package com.example.plantcare.model.user

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @field:SerializedName("user_id")
    val user_id : String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("email")
    val email : String,

    @field:SerializedName("password")
    val password : String,

    @field:SerializedName("role")
    val role : String,
)
