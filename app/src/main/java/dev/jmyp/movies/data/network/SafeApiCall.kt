package dev.jmyp.movies.data.network

import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import kotlinx.io.IOException
import kotlinx.serialization.SerializationException


suspend inline fun <T> safeApiCall(
    crossinline apiCall: suspend () -> T
): Resource<T> {
    return try {
        Resource.Success(apiCall())
    } catch (e: RedirectResponseException) { // 3xx
        Resource.Error("Redirección inesperada (${e.response.status})", e)
    } catch (e: ClientRequestException) { // 4xx
        Resource.Error("Error del cliente (${e.response.status})", e)
    } catch (e: ServerResponseException) { // 5xx
        Resource.Error("Error del servidor (${e.response.status})", e)
    } catch (e: SerializationException) {
        Resource.Error("Error de serialización: ${e.message}", e)
    } catch (e: IOException) {
        Resource.Error("Error de conexión: ${e.message}", e)
    } catch (e: Exception) {
        Resource.Error("Error inesperado: ${e.message}", e)
    }
}