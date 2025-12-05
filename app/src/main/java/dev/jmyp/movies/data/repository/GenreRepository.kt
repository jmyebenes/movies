package dev.jmyp.movies.data.repository

import dev.jmyp.movies.data.network.GenreApiService
import dev.jmyp.movies.data.network.safeApiCall

class GenreRepository(private val apiService: GenreApiService) {
    suspend fun getGenres() = safeApiCall { apiService.getGenres() }
}