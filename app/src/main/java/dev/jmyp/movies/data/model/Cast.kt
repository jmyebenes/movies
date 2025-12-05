package dev.jmyp.movies.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Cast(
    val id: Int,
    val name: String,
    @SerialName("profile_path")
    val profilePath: String?,
    val character: String
)