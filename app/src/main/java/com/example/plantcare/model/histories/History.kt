package com.example.plantcare.model.histories

data class History(
    val id : Int,
    val category: String,
    val disease : String,
    val photo : Int,
    val caused : String,
    val date : String,
)
