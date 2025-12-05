package dev.jmyp.movies.data.network

import android.util.Log
import dev.jmyp.movies.utils.Constants
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import java.util.Locale

fun createHttpClient(): HttpClient {
    return HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
        install(Logging) {
            level = LogLevel.BODY
            logger = object : Logger {
                override fun log(message: String) {
                    Log.d("Ktor", message)
                }
            }
        }
        defaultRequest {
            url {
                protocol = URLProtocol.HTTPS
                host = Constants.Api.BASE_URL
                parameters.append("language", Locale.getDefault().toLanguageTag())
                parameters.append("include_adult", "false")
            }
            header(
                "Authorization",
                "Bearer ${Constants.Api.TMDB_API_KEY}"
            )
            header("Accept", "application/json")
        }
        // timeouts, interceptors, etc.
    }
}