package dev.jmyp.movies.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import dev.jmyp.movies.utils.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class FavoriteRepository(
    private val dataStore: DataStore<Preferences>
) {

    fun getFavoriteIds(): Flow<Set<String>> {
        return dataStore.data.map { prefs ->
            prefs[Constants.Preferences.FAVORITE_IDS] ?: emptySet()
        }
    }

    suspend fun addFavorite(movieId: Int) {
        dataStore.edit { prefs ->
            val current = prefs[Constants.Preferences.FAVORITE_IDS] ?: emptySet()
            prefs[Constants.Preferences.FAVORITE_IDS] = current + movieId.toString()
        }
    }

    suspend fun removeFavorite(movieId: Int) {
        dataStore.edit { prefs ->
            val current = prefs[Constants.Preferences.FAVORITE_IDS] ?: emptySet()
            prefs[Constants.Preferences.FAVORITE_IDS] = current - movieId.toString()
        }
    }

    suspend fun isFavorite(movieId: Int): Boolean {
        val current = dataStore.data.first()[Constants.Preferences.FAVORITE_IDS] ?: emptySet()
        return movieId.toString() in current
    }
}