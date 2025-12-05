package dev.jmyp.movies.data.repository

import dev.jmyp.movies.data.network.PeopleApiService
import dev.jmyp.movies.data.network.safeApiCall

class PeopleRepository(private val apiService: PeopleApiService) {
    suspend fun getPersonDetail(id: Int) = safeApiCall { apiService.getPersonDetail(id) }
    suspend fun getPersonCredits(id: Int) = safeApiCall { apiService.getPersonCredits(id) }
}