package com.pop.moviemanagement.model.theater

data class Theater(
    var id: String? = null,
    var userId: String = "",
    val name: String = "",
    val address: String = "",
    var favorite: Boolean = false,
    val city: String = ""
)
