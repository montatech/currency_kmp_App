package org.example.project.network.remote

import androidx.compose.ui.graphics.ImageBitmap
import io.ktor.client.HttpClient
import org.example.project.network.model.CurrencyDtoItem

interface CurrencyProvider {
    suspend fun getCurrency(): Result<List<CurrencyDtoItem>>
    suspend fun getCurrency(query: String): Result<List<CurrencyDtoItem>>
}