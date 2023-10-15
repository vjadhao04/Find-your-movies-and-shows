package com.example.moviesapp.api

import com.example.moviesapp.utils.RetrofitIntercepetr
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val POSTER_BASE_URL: String = "https://image.tmdb.org/t/p/w342"

object Retrofit {
    private const val  BASE_URL="https://api.themoviedb.org/3/"

    fun getInstance(): Retrofit {

        val logging = HttpLoggingInterceptor()
        logging.level=HttpLoggingInterceptor.Level.BODY
        val okhttpClient = OkHttpClient()
            .newBuilder()
            .connectTimeout(400, TimeUnit.MILLISECONDS)
            .readTimeout(400, TimeUnit.MILLISECONDS)
            .addInterceptor(logging)
            .build()


        return  Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okhttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()



    }


}
