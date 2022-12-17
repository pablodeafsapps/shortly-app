package org.deafsapps.shortlyapp.urlshortening.domain.model

data class ShortenUrlOperationBo(
    val status: ShortenUrlOpStatusBo,
    val result: ShortenUrlOpResultBo
)

@JvmInline
value class ShortenUrlOpStatusBo(val isSuccessful: Boolean)

data class ShortenUrlOpResultBo(
    val code: String,
    val shortLink: String,
    val fullShortLink: String,
    val originalLink: String
)
