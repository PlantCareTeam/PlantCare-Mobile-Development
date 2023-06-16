package com.example.plantcare.model.predict

data class PredictTomatoResponse(
	val imageHex: String? = null,
	val deskripsi: Deskripsi? = null,
	val predictedLabel: String? = null
)

data class Deskripsi(
	val pengobatan: String? = null,
	val penyebab: String? = null,
	val description: String? = null,
	val rekomendasiObat: String? = null
)

