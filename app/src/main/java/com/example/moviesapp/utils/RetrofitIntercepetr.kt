package com.example.moviesapp.utils

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class RetrofitIntercepetr:Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val aRequest: Request = chain.request()
        println(aRequest.url)


        val aResponse = chain.proceed(aRequest)
        when (aResponse.code) {
            400 -> {
                println("------------400-----------")
            }
            401 -> {
                println("------------401-----------")
            }

            403 -> {
                println("------------403-----------")
            }

            404 -> {
                println("------------404-----------")
            }


        }
        return aResponse
    }
}