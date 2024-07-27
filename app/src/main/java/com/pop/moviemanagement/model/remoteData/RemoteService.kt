package com.pop.moviemanagement.model.remoteData

import com.pop.moviemanagement.data.MovieResult
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface RemoteService {
    @GET("movie/upcoming?language=en-US&page=1")
    suspend fun listPopularMovies(): MovieResult

}