package com.pop.moviemanagement.model.remoteData

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RemoteDataSource {
    private val okHttpClient = HttpLoggingInterceptor().run {
        level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder().addInterceptor(object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val request: Request = chain.request()
                    .newBuilder().header("accept", "application/json")
                    .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxNjQ0MzYwZWRjMTVlZjQ2YTAyY2JhODU5YTBjYjkyMiIsIm5iZiI6MTcyMjAzNjgyNS43Mjc0ODQsInN1YiI6IjYzODU1NDJmMGZiMTdmMDA4OTMyZGI0MCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.b_m0C2irzRdYdbRjycDIg3uX-J2fZnt0g9fp2BbMB5I")
                    .build()
                return chain.proceed(request)
            }
        }).build()
    }

    private val builder = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service: RemoteService = builder.create()
}