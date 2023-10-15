package com.example.moviesapp.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteMovie(
    val budget: Int,
    val homepage: String,
    @PrimaryKey
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val revenue: Int,
    val runtime: Double,
    val tagline: String,
    val title: String,

)