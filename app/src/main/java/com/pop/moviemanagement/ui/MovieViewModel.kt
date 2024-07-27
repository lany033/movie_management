package com.pop.moviemanagement.ui

import com.pop.moviemanagement.data.MovieResult
import com.pop.moviemanagement.model.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pop.moviemanagement.data.Movie
import kotlinx.coroutines.launch

class MovieViewModel(private val movieRepository: MovieRepository): ViewModel() {

    private val _state = MutableStateFlow(MovieState())
    val state: StateFlow<MovieState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.value = MovieState(movies = movieRepository.getMovies().results)
        }
    }
    data class MovieState(
        val movies: List<Movie> = emptyList(),
    )
}