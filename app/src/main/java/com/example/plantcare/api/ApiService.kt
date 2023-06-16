package com.example.plantcare.api

import com.example.plantcare.model.histories.HistoryResponse
import com.example.plantcare.model.predict.PredictResponse
import com.example.plantcare.model.predict.PredictTomatoResponse
import com.example.plantcare.model.user.LoginApiResponse
import com.example.plantcare.model.user.LoginRequest
import com.example.plantcare.model.user.RegisterRequest
import com.example.plantcare.model.user.RegisterResponse
import com.example.plantcare.model.user.UserResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query
import java.io.File

interface ApiService {

    @POST("auth/register")
    suspend fun userRegister(
        @Body request: RegisterRequest
    ): RegisterResponse

    @POST("auth/login")
    suspend fun userLogin(
        @Body request: LoginRequest
    ): LoginApiResponse

    @GET("profile")
    suspend fun getUser(
        @Query("userId") userId : Int
    ): UserResponse

    @GET("profile/history")
    suspend fun getAllHistory(
        @Header ("Authorization") token : String,
        @Query("userId") userId : String
    ): HistoryResponse

    @Multipart
    @POST("predictapple")
    suspend fun predictApple(
        @Part file: MultipartBody.Part?,
    ): PredictResponse

    @Multipart
    @POST("predict-tomato")
    suspend fun predictTomato(
        @Part file: MultipartBody.Part?,
    ): PredictTomatoResponse


}