package com.example.moviesapp.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.moviesapp.api.RetrofitService
import com.example.moviesapp.models.ResultX

class ShowPagingDataSource(val retrofitService: RetrofitService):PagingSource<Int, ResultX>() {


    override fun getRefreshKey(state: PagingState<Int, ResultX>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }

    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultX> {
        return try {

            val position = params.key ?: 1
            println("-----------retrofit show--------------")
            val response = retrofitService.getShows("323351aac3b04130cfe5c7929b04eec4",position)
            println("-----------after retrofit on source in show---------------")

            LoadResult.Page(
                data = response.body()!!.results,
                prevKey = if(position==1) null else position-1,
                nextKey = if(response.body()!!.total_pages==position) null else position+1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }

    }
}