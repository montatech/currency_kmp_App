package org.example.project.network.remote

import androidx.compose.ui.graphics.ImageBitmap
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.io.IOException
import org.example.project.network.model.CurrencyDtoItem

class ApiRepository(private val httpClient: HttpClient) : CurrencyProvider {
    override suspend fun getCurrency(): Result<List<CurrencyDtoItem>> {
        return try {
            val response = httpClient.get(
                urlString = "https://api.coingecko.com/api/v3/coins/markets"
            ) {
                parameter("vs_currency", "usd")
                parameter("sparkline", "true")

            }
            Result.success(response.body())
        } catch (e: IOException) {
            Result.failure(Exception("Check your internet connection: ${e.message}"))
        } catch (e: ClientRequestException) {
            Result.failure(Exception("Client error (4xx): ${e.message}"))
        } catch (e: ServerResponseException) {
            Result.failure(Exception("Server error (5xx): ${e.message}"))
        } catch (e: Exception) {
            Result.failure(Exception("Unknown error: ${e.message}"))
        }


    }

    override suspend fun getCurrency(query: String): Result<List<CurrencyDtoItem>> {
        return try {
            val response = httpClient.get("https://api.coingecko.com/api/v3/search") {
                parameter("vs_currency", "usd")
                parameter("sparkline", "true")
                parameter("ids", query)
            }


            Result.success(response.body())
        } catch (e: IOException) {
            Result.failure(Exception("Check your internet connection: ${e.message}"))
        } catch (e: ClientRequestException) {
            Result.failure(Exception("Client error (4xx): ${e.message}"))
        } catch (e: ServerResponseException) {
            Result.failure(Exception("Server error (5xx): ${e.message}"))
        } catch (e: Exception) {
            Result.failure(Exception("Unknown error: ${e.message}"))
        }
    }




}