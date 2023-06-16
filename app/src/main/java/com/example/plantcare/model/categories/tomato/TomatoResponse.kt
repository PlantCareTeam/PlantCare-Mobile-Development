package com.example.plantcare.model.categories.tomato

import java.util.Date

data class TomatoResponse(
    val user_id : String,
    val predict_id : String,
    val image : String,
    val hasil_predict : String,
    val date_predict : Date,
    val diagnosis : String,
    val pengobatan : String
)

