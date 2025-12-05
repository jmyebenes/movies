package dev.jmyp.movies.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReviewResult(
    val id: String,
    val author: String,
    val content: String,
    @SerialName("created_at")
    val createdAt: String,
)
