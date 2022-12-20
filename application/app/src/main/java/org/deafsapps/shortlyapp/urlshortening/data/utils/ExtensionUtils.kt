package org.deafsapps.shortlyapp.urlshortening.data.utils

import org.deafsapps.shortlyapp.urlshortening.data.model.ShortenUrlOpResultDto
import org.deafsapps.shortlyapp.urlshortening.data.model.ShortenUrlOperationDto
import org.deafsapps.shortlyapp.urlshortening.domain.model.ShortenUrlOpResultBo
import org.deafsapps.shortlyapp.urlshortening.domain.model.ShortenUrlOpStatusBo
import org.deafsapps.shortlyapp.urlshortening.domain.model.ShortenUrlOperationBo

fun ShortenUrlOperationDto.toBo(): ShortenUrlOperationBo =
    ShortenUrlOperationBo(
        status = ShortenUrlOpStatusBo(status),
        result = result.toBo()
    )

private fun ShortenUrlOpResultDto.toBo(): ShortenUrlOpResultBo =
    ShortenUrlOpResultBo(
        code = code,
        shortLink = shortLink,
        fullShortLink = fullShortLink,
        originalLink = originalLink
    )
