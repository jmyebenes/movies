package dev.jmyp.movies.utils

import androidx.datastore.preferences.core.stringSetPreferencesKey
import dev.jmyp.movies.BuildConfig

object Constants {
    object Api {
        const val BASE_URL = "api.themoviedb.org/3"
        const val BASE_IMG_URL = "https://image.tmdb.org/t/p/w500"
        const val TMDB_API_KEY = BuildConfig.TMDB_API_KEY
    }

    object Preferences {
        val FAVORITE_IDS = stringSetPreferencesKey("favorite_movie_ids")
    }
}