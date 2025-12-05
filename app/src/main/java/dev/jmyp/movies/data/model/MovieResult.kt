package dev.jmyp.movies.data.model

import kotlinx.serialization.Serializable

@Serializable
data class MovieResult(val results: List<Movie>)