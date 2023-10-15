package com.example.moviesapp.models

data class ShowsData(
    val page: Int,
    val results: List<ResultX>,
    val total_pages: Int,
    val total_results: Int
)