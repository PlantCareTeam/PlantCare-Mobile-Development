package com.example.plantcare.data

import android.content.Context
import android.util.Log
import com.example.plantcare.api.ApiService
import com.example.plantcare.model.predict.Deskripsi
import com.example.plantcare.model.user.LoginApiResponse
import com.example.plantcare.model.user.LoginRequest
import com.example.plantcare.model.user.RegisterRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import okhttp3.MultipartBody
import java.io.File


class MainRepository(
    private val pref : UserPreferences,
    private val apiService: ApiService,
    val context : Context
) {
    private var imageFile : MultipartBody.Part? = null
    fun setImage(image: MultipartBody.Part?){
        imageFile = image
    }
    fun getImage() : Flow<MultipartBody.Part> = flowOf(imageFile!!)

    suspend fun loginUser(login: LoginRequest): Flow<LoginApiResponse> {
        try{
            val response = apiService.userLogin(login)
            if(response.success == true) {
                setToken("token")
                return flowOf(response)
            }
            throw Exception(response.message)
        } catch (e: Exception) { throw e }
    }

    suspend fun registerUser(register: RegisterRequest): Flow<Boolean> {
        try {
            val response = apiService.userRegister(register)
            if(!response.error) return flowOf(true)
            throw Exception(response.message)
        } catch (e: Exception) {
            Log.d("TAG", "registerUser error: ${e.message} ")
            throw e
        }
    }

    fun getUserData(): Flow<Flow<String>> = flowOf(pref.getUser())

    suspend fun getToken() :String = pref.getToken().first()

    suspend fun setToken(token : String) = pref.setToken(token)

    suspend fun saveUserData(user: String) {
        pref.saveUserData(user)
    }

    suspend fun logout() {
        pref.logout()
    }

    suspend fun predictTomato(image: MultipartBody.Part?) : Flow<Deskripsi>{
        try{
            val response = apiService.predictTomato(image)
            if (!response.predictedLabel.isNullOrEmpty()){
                return flowOf(Deskripsi())
            }
            throw Exception(response.predictedLabel)
        } catch (e: Exception) {
            Log.d("TAG", "registerUser error: ${e.message} ")
            throw e
        }
    }

    

    companion object {
        private val TAG = MainRepository::class.java

        @Volatile
        private var instance: MainRepository? = null
        fun getInstance(
            preferences: UserPreferences,
            apiService: ApiService,
            context: Context
        ): MainRepository =
            instance ?: synchronized(this) {
                instance ?: MainRepository(preferences, apiService, context)
            }.also { instance = it }
    }
}