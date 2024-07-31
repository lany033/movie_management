package com.pop.moviemanagement.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pop.moviemanagement.model.theater.Theater
import com.pop.moviemanagement.utils.FirestoreManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TheaterViewModel(private val firestoreManager: FirestoreManager): ViewModel(){

    private val _theaterState = MutableStateFlow(TheaterState())
    val theaterState: StateFlow<TheaterState> = _theaterState.asStateFlow()

    init {
        viewModelScope.launch {
            firestoreManager.getTheatersFlow().collect{ theaterList ->
                _theaterState.update {
                    it.copy(theaters = theaterList)
                }
            }
        }
    }

    fun onFavoriteClicked(theaterId: String, isFavorite: Boolean){
        viewModelScope.launch {
            firestoreManager.setFavorite(theaterId, isFavorite)
            // Update the list immediately after changing the favorite status
            _theaterState.value.theaters = _theaterState.value.theaters.map {theater ->
                if (theater.id == theaterId){
                    theater.copy(favorite = isFavorite)
                } else {
                    theater
                }
            }

        }
    }

    data class TheaterState(
        var theaters: List<Theater> = emptyList(),
    )
}