package dev.jmyp.movies.data.model

import kotlinx.serialization.Serializable

@Serializable
data class PersonCreditsResult(val cast: List<Movie>)