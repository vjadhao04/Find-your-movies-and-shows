package com.example.moviesapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.moviesapp.models.UserTable
import java.time.Instant


@Database(entities = [UserTable::class,FavoriteMovie::class], version = 1)
abstract class MovieDB: RoomDatabase() {


    abstract fun userDao() : UserDao

    companion object{

        @Volatile
        var Instance:MovieDB?=null

        fun getIntance(context: Context) :MovieDB{
            if(Instance==null){
                synchronized(this){
                    return Room.databaseBuilder(
                        context,
                        MovieDB::class.java,
                        "UserDB"
                    )
                        .allowMainThreadQueries()
                        .build()
                }
            }
            else
                return Instance!!


        }

        fun destroyInstance() {
            Instance = null
        }
    }



}