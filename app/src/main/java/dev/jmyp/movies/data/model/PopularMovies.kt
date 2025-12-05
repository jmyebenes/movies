package dev.jmyp.movies.data.model

import kotlinx.serialization.Serializable

@Serializable
data class PopularMovies(val results: List<Movie>)