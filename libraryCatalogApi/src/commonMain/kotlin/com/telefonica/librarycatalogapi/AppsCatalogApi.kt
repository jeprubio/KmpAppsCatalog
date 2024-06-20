package com.telefonica.librarycatalogapi

import com.telefonica.librarycatalogapi.models.Product
import com.telefonica.librarycatalogapi.models.ProductsResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.call.receive
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class AppsCatalogApi(
    private val client: HttpClient
) {
    companion object {
        fun createDefault(): AppsCatalogApi {
            val defaultClient = HttpClient {
                install(ContentNegotiation) {
                    json(
                        Json {
                            ignoreUnknownKeys = true
                        }
                    )
                }
            }
            return AppsCatalogApi(defaultClient)
        }
    }

    suspend fun getAppsAsString(): Result<String> {
        val response = client.get("https://novum-launcher.firebaseio.com/apps.json")
        return response.asKotlinResult()
    }

    suspend fun getApps(): Result<ProductsResponse> {
        try {
            val response = client.get("https://novum-launcher.firebaseio.com/apps.json")
            val result = Json.decodeFromString(
                ProductsResponseSerializer,
                response.body()
            )
            return Result.success(result)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}

private suspend inline fun <reified T> HttpResponse.asKotlinResult(): Result<T> {
    return if (status.isSuccess()) {
        Result.success(body<T>())
    } else {
        Result.failure(Exception("Request failed with status: $status"))
    }
}
