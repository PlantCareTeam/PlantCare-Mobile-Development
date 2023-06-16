package com.example.plantcare.model.histories

import com.google.gson.annotations.SerializedName
import java.util.Date

data class HistoryResponse(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("listStory")
    val listHistory: List<HistoryList>
){
    data class HistoryList(
        val user_id: String? = null,
        val predict_id: String? =null,
        val plant: String? =null,
        val nama_ilmiah: String? =null,
        val hasil_predict: String? = null,
        val date_predict: Date? =null,
    )
}
