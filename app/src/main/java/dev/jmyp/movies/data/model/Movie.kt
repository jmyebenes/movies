package dev.jmyp.movies.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val popularity: Double,
    @SerialName("poster_path")
    val posterPath: String? = null,
    @SerialName("genre_ids")
    val genreIds: List<Int>,
    val genreNames: List<String> = emptyList(),
    @SerialName("vote_average")
    val voteAverage: Double,
    @SerialName("release_date")
    val releaseDate: String,
    @SerialName("backdrop_path")
    val backdropPath: String? = null,
)
