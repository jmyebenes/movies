package dev.jmyp.movies.data.repository

import dev.jmyp.movies.data.network.MoviesApiService
import dev.jmyp.movies.data.network.safeApiCall

class MoviesRepository(private val apiService: MoviesApiService) {
    suspend fun getTrendingMovies() = safeApiCall { apiService.getTrendingMovies() }
    suspend fun getPopularMovies() = safeApiCall { apiService.getPopularMovies() }
    suspend fun getNowPlayingMovies() = safeApiCall { apiService.getNowPlayingMovies() }
    suspend fun getUpcomingMovies() = safeApiCall { apiService.getUpcomingMovies() }
    suspend fun getMovieDetail(id: Int) = safeApiCall { apiService.getMovieDetail(id) }
    suspend fun getCredits(id: Int) = safeApiCall { apiService.getCredits(id) }
    suspend fun getSimilarMovies(id: Int) = safeApiCall { apiService.getSimilarMovies(id) }
    suspend fun getFavoriteMovies(ids: List<Int>) = safeApiCall { apiService.getFavoriteMovies(ids) }
}