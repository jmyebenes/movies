package dev.jmyp.movies.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Reviews(
    val id: String,
    val results: List<ReviewResult>
)
