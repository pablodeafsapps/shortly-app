package org.deafsapps.shortlyapp.common.utils

import retrofit2.Converter
import retrofit2.Retrofit

private const val DEFAULT_BASE_URL: String = "https://api.shrtco.de/v2/"

fun getRetrofitInstance(
    baseUrl: String = DEFAULT_BASE_URL, converterFactory: Converter.Factory
): Retrofit = Retrofit.Builder()
    .baseUrl(baseUrl)
    .addConverterFactory(converterFactory)
    .build()
