package dev.jmyp.movies.data.network

import dev.jmyp.movies.data.model.PersonCreditsResult
import dev.jmyp.movies.data.model.PersonDetail
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse

class PeopleApiService(private val client: HttpClient) {
    suspend fun getPersonDetail(id: Int): PersonDetail {
        val response: HttpResponse = client.get("/person/$id")
        return response.body()
    }

    suspend fun getPersonCredits(id: Int): PersonCreditsResult {
        val response: HttpResponse = client.get("/person/$id/movie_credits")
        return response.body()
    }
}