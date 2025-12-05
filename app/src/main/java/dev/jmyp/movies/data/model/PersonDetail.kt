package dev.jmyp.movies.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PersonDetail(
    val id: Int,
    val name: String,
    val biography: String,
    val birthday: String?,
    @SerialName("known_for_department")
    val knownForDepartment: String,
    @SerialName("profile_path")
    val profilePath: String
)