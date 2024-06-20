package com.telefonica.librarycatalogapi

import com.telefonica.librarycatalogapi.models.Product
import com.telefonica.librarycatalogapi.models.ProductsResponse
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonObject

object ProductsResponseSerializer : KSerializer<ProductsResponse> {
    override val descriptor: SerialDescriptor = JsonObject.serializer().descriptor

    override fun deserialize(decoder: Decoder): ProductsResponse {
        check(decoder is JsonDecoder) {
            "This serializer can only be used with JSON format"
        }

        val jsonObject = decoder.decodeJsonElement().jsonObject
        val products = jsonObject.mapValues { (_, jsonElement) ->
            decoder.json.decodeFromJsonElement<Product>(jsonElement)
        }
        return ProductsResponse(products)
    }

    override fun serialize(encoder: Encoder, value: ProductsResponse) {
        TODO("No need for serialization in this project")
    }
}