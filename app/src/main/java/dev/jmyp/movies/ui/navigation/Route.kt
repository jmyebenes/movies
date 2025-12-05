package dev.jmyp.movies.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Route {
    @Serializable
    object Home : Route()

    @Serializable
    data class MovieDetail(val id: Int) : Route()

    @Serializable
    data class PersonDetail(val id: Int) : Route()

    @Serializable
    data class AllMovies(val referenceId: Int? = null, val movieCategory: MovieCategory) : Route()

    @Serializable
    data class AllCredits(val personId: Int? = null, val personCategory: PersonCategory) : Route()
}