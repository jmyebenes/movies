package dev.jmyp.movies.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Credits(
    val id: Int,
    val cast: List<Cast>,
    val crew: List<Crew>
)
