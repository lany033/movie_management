package com.pop.moviemanagement.model.theater

data class Theater(
    var id: String? = null,
    var userId: String = "",
    val name: String = "",
    val address: String = "",
    val favorite: Boolean = false,
    val city: String = ""
)
