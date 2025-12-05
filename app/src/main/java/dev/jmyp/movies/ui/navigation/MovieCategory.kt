package dev.jmyp.movies.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
enum class MovieCategory {
    POPULAR,
    UPCOMING,
    NOW_PLAYING,
    SIMILAR,
    PERSON_CREDITS
}