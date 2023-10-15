package com.example.moviesapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserTable(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val userName:String,
    val password:String

)