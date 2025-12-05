package dev.jmyp.movies.data.model

import kotlinx.serialization.Serializable

@Serializable
data class NowPlayingMovies(val results: List<Movie>)