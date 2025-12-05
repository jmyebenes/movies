package dev.jmyp.movies.data.model

import kotlinx.serialization.Serializable

@Serializable
data class UpcomingMovies(val results: List<Movie>)