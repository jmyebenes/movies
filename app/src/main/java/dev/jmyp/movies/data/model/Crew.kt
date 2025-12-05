package dev.jmyp.movies.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Crew(
    val id: Int,
    val name: String,
    @SerialName("profile_path")
    val profilePath: String?,
    val department: String,
    val job: String
)