package org.deafsapps.shortlyapp.urlshortening.domain.model

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonClass
import com.squareup.moshi.ToJson
import java.util.*

@JsonClass(generateAdapter = true)
data class ShortenUrlOperationBo(
    val uuid: UUID = UUID.randomUUID(),
    val status: ShortenUrlOpStatusBo,
    val result: ShortenUrlOpResultBo
)

@JsonClass(generateAdapter = true)
@JvmInline
value class ShortenUrlOpStatusBo(val isSuccessful: Boolean)

@JsonClass(generateAdapter = true)
data class ShortenUrlOpResultBo(
    val code: String,
    val shortLink: String,
    val fullShortLink: String,
    val originalLink: String
)

object UuidAdapter {
    @FromJson
    fun fromJson(uuid: String): UUID = UUID.fromString(uuid)

    @ToJson
    fun toJson(value: UUID): String = value.toString()
}
