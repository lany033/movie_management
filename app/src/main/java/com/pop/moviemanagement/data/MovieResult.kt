package com.pop.moviemanagement.data

data class MovieResult(
    val dates: Dates,
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)