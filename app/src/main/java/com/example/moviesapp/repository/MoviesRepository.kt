package com.example.moviesapp.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.example.moviesapp.api.RetrofitService
import com.example.moviesapp.db.FavoriteMovie
import com.example.moviesapp.db.MovieDB
import com.example.moviesapp.models.MoviesList
import com.example.moviesapp.models.Result

import com.example.moviesapp.models.SingleMovie
import com.example.moviesapp.paging.MoviesPagingSource
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Response
import retrofit2.http.Query

class MoviesRepository (private val retrofitService: RetrofitService) {

    private val mutableLiveData= MutableLiveData<SingleMovie>()
    val movies: LiveData<SingleMovie>
    get() = mutableLiveData


    suspend fun searchMovies(query: String) :List<Result>{
        val response=retrofitService.searchMovies(
            api_key = "323351aac3b04130cfe5c7929b04eec4",
            query = query
        )
        return response.body()!!.results
    }

    //to get a single movie by providing ID
    suspend fun getMovie( id :Int){
        val result=retrofitService.getMovie(id,"323351aac3b04130cfe5c7929b04eec4")

        if (result.body() != null){
            mutableLiveData.postValue(result.body())
        }

    }


    //paging for get multiple movies
    fun getMovie()=Pager(
        config = PagingConfig(20,100),
        pagingSourceFactory = {MoviesPagingSource(retrofitService)}
    ).liveData

    fun saveFavoriteMovie(context: Context,favoriteMovie: FavoriteMovie){
        GlobalScope.launch {
            val db= MovieDB.getIntance(context).userDao()
            db.insertMovie(favoriteMovie)
        }

    }
    fun findSavedFavoriteMovie(context: Context,id:Int):Boolean{
        var result=false
        val job=GlobalScope.launch {
            val db= MovieDB.getIntance(context).userDao()
            result=db.getMFavoriteMovie(id)
        }
        job.onJoin
        return result

    }


}