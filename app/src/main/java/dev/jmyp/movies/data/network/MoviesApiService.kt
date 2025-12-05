package dev.jmyp.movies.data.network

import dev.jmyp.movies.data.mapper.toMovie
import dev.jmyp.movies.data.model.Credits
import dev.jmyp.movies.data.model.Movie
import dev.jmyp.movies.data.model.MovieDetail
import dev.jmyp.movies.data.model.MovieResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class MoviesApiService(private val client: HttpClient) {
    suspend fun getTrendingMovies(): MovieResult {
        val response: HttpResponse = client.get("/trending/movie/week")
        return response.body()
    }

    suspend fun getPopularMovies(): MovieResult {
        val response: HttpResponse = client.get("/movie/popular")
        return response.body()
    }

    suspend fun getNowPlayingMovies(): MovieResult {
        val response: HttpResponse = client.get("/movie/now_playing")
        return response.body()
    }

    suspend fun getUpcomingMovies(): MovieResult {
        val response: HttpResponse = client.get("/movie/upcoming")
        return response.body()
    }

    suspend fun getMovieDetail(id: Int): MovieDetail {
        val response: HttpResponse = client.get("/movie/$id")
        return response.body()
    }

    suspend fun getCredits(id: Int): Credits {
        val response: HttpResponse = client.get("/movie/$id/credits")
        return response.body()
    }

    suspend fun getSimilarMovies(id: Int): MovieResult {
        val response: HttpResponse = client.get("/movie/$id/similar")
        return response.body()
    }

    suspend fun getFavoriteMovies(ids: List<Int>): List<Movie> {
        return coroutineScope {
            ids.map { id ->
                async {
                    try {
                        getMovieDetail(id).toMovie()
                    } catch (e: Exception) {
                        null
                    }
                }
            }.awaitAll().filterNotNull()
        }
    }
}

