package dev.jmyp.movies.data.model

import kotlinx.serialization.Serializable

@Serializable
data class TrendingMovies(val results: List<Movie>)