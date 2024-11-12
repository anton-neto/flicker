package com.example.myapplication

data class SearchApiResponse(
    val Search: List<FilmeApiResponse>,
    val totalResults: String,
    val Response: String
)
