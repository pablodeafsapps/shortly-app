package org.pablodeafsapps.shortlyapp.urlshortening.domain.model

import org.pablodeafsapps.shortlyapp.common.domain.model.FailureBo

@JvmInline
value class Url(val value: String)

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
)
