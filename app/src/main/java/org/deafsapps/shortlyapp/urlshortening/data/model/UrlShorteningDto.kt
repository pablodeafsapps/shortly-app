package org.deafsapps.shortlyapp.urlshortening.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ShortenUrlOperationDto(
    @Json(name = "ok") val status: Boolean,
    @Json(name = "result") val result: ShortenUrlOpResultDto
)

@JsonClass(generateAdapter = true)
data class ShortenUrlOpResultDto(
    @Json(name = "code") val code: String,
    @Json(name = "short_link") val shortLink: String,
    @Json(name = "full_short_link") val fullShortLink: String,
    @Json(name = "short_link2") val shortLink2: String,
    @Json(name = "full_short_link2") val fullShortLink2: String,
    @Json(name = "short_link3") val shortLink3: String,
    @Json(name = "full_short_link3") val fullShortLink3: String,
    @Json(name = "share_link") val shareLink: String,
    @Json(name = "full_share_link") val fullShareLink: String,
    @Json(name = "original_link") val originalLink: String
)
