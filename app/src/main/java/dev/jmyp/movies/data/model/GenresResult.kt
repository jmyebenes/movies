package dev.jmyp.movies.data.model

import kotlinx.serialization.Serializable

@Serializable
data class GenresResult(val genres: List<Genre>)
