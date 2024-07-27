package com.pop.moviemanagement.model.repository

import com.pop.moviemanagement.data.MovieResult
import com.pop.moviemanagement.model.remoteData.RemoteDataSource

class MovieRepository {
    suspend fun getMovies(): MovieResult {
        return RemoteDataSource.service.listPopularMovies()
    }
}
