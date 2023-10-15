package com.example.moviesapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.api.Retrofit
import com.example.moviesapp.api.RetrofitService
import com.example.moviesapp.models.Result
import com.example.moviesapp.repository.MoviesRepository
import kotlinx.coroutines.launch
import retrofit2.http.Query

class SearchVM :ViewModel(){
    private val retrofitService=Retrofit.getInstance().create(RetrofitService::class.java)
    private val moviesRepository=MoviesRepository(retrofitService)

    private val moviesdata:MutableLiveData<List<Result>> = MutableLiveData()
    val movies:LiveData<List<Result>>
        get() = moviesdata

    fun searchMovie(query: String) {
        viewModelScope.launch {
            val responce=moviesRepository.searchMovies(query)
            moviesdata.postValue(responce)
        }

    }



}