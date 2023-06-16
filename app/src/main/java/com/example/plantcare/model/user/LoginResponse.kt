package com.example.plantcare.model.user

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoginResponse(
    @field:SerializedName("loginResult")
    val loginResult: LoginResult,

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
): Parcelable


@Parcelize
data class LoginResult(
    @field:SerializedName("token")
    val token: String? = null,

    @field:SerializedName("isLoading")
    val isLoading: Boolean? = null
): Parcelable

@Parcelize
data class LoginRequest(
    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("password")
    val password: String? = null
): Parcelable

