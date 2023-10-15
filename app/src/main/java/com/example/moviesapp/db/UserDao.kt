package com.example.moviesapp.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.moviesapp.models.UserTable

@Dao
interface UserDao{
    @Insert
    fun insertMovie(favoriteMovie: FavoriteMovie)

//    @Query("SELECT EXISTS(SELECT :username FROM UserTable)")
//    fun getUserName(username:String):Boolean
//
//    @Query("SELECT EXISTS(SELECT * FROM UserTable WHERE  userName=:username AND password=:pass )")
//    fun getLogin(username:String, pass:String):Boolean
    @Query("SELECT * FROM FavoriteMovie")
    fun selectAll():List<FavoriteMovie>

    @Delete
    fun deletMovie(favoriteMovie: FavoriteMovie)

    @Query("SELECT EXISTS(SELECT * FROM  FavoriteMovie WHERE id=:id)")
    fun getMFavoriteMovie(id:Int):Boolean

}