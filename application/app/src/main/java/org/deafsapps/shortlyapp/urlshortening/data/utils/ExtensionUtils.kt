package org.deafsapps.shortlyapp.urlshortening.data.utils

import org.deafsapps.shortlyapp.urlshortening.data.model.ShortenUrlOpResultDto
import org.deafsapps.shortlyapp.urlshortening.data.model.ShortenUrlOpStatusDto
import org.deafsapps.shortlyapp.urlshortening.data.model.ShortenUrlOperationDto
import org.deafsapps.shortlyapp.urlshortening.domain.model.ShortenUrlOpResultBo
import org.deafsapps.shortlyapp.urlshortening.domain.model.ShortenUrlOpStatusBo
import org.deafsapps.shortlyapp.urlshortening.domain.model.ShortenUrlOperationBo

fun ShortenUrlOperationDto.toBo(): ShortenUrlOperationBo =
    ShortenUrlOperationBo(
        status = status.toBo(),
        result = result.toBo()
    )

fun ShortenUrlOpStatusDto.toBo(): ShortenUrlOpStatusBo =
    ShortenUrlOpStatusBo(
        isSuccessful = isSuccessful
    )

fun ShortenUrlOpResultDto.toBo(): ShortenUrlOpResultBo =
    ShortenUrlOpResultBo(
        code = code,
        shortLink = shortLink,
        fullShortLink = fullShortLink
    )
