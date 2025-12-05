package dev.jmyp.movies.data.network

import dev.jmyp.movies.data.model.GenresResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse

class GenreApiService(private val client: HttpClient) {
    suspend fun getGenres(): GenresResult {
        val response: HttpResponse = client.get("/genre/movie/list")
        return response.body()
    }
}