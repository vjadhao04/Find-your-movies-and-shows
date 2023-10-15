package com.example.moviesapp.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.moviesapp.api.RetrofitService
import com.example.moviesapp.paging.MoviesPagingSource
import com.example.moviesapp.paging.ShowPagingDataSource

class ShowRepo(val retrofitService: RetrofitService) {



    fun getshow()= Pager(
        config = PagingConfig(20,100),
        pagingSourceFactory = { ShowPagingDataSource(retrofitService) }
    ).liveData
}