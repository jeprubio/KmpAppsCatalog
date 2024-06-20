package com.telefonica.librarycatalogapi.models

import kotlinx.serialization.Serializable


@Serializable
data class ProductsResponse(val products: Map<String, Product>)

@Serializable
data class Product(
    val android: AndroidAppData,
    val description: String,
    val icon: String,
    val ios: IosAppData,
    val name: String,
    val project: String,
)

@Serializable
data class AndroidAppData(
    val installUrl: String,
    val packageName: String,
    val repositoryUrl: String,
)

@Serializable
data class IosAppData(
    val installUrl: String,
    val packageName: String,
    val repositoryUrl: String,
    val scheme: String,
)

