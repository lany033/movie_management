package com.pop.moviemanagement.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pop.moviemanagement.model.theater.Theater
import com.pop.moviemanagement.utils.FirestoreManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TheaterViewModel(firestoreManager: FirestoreManager): ViewModel(){

    private val _state = MutableStateFlow(TheaterState())
    val state: StateFlow<TheaterState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            firestoreManager.getTheatersFlow().collect{ theaterList ->
                _state.update {
                    it.copy(theaters = theaterList)
                }
            }
        }
    }

    data class TheaterState(
        val theaters: List<Theater> = emptyList()
    )
}