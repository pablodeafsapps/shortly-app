package org.deafsapps.shortlyapp.utils

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import org.deafsapps.shortlyapp.common.domain.model.FailureBo
import org.deafsapps.shortlyapp.urlshortening.domain.model.ShortenUrlOpResultBo
import org.deafsapps.shortlyapp.urlshortening.domain.model.ShortenUrlOpStatusBo
import org.deafsapps.shortlyapp.urlshortening.domain.model.ShortenUrlOperationBo
import java.util.*

private const val DEFAULT_STRING_VALUE = "string"
private const val DEFAULT_BOOLEAN_VALUE = true
private const val DEFAULT_INT_VALUE = 1

fun getDummyShortenUrlOperationBo(): ShortenUrlOperationBo =
    ShortenUrlOperationBo(
        uuid = UUID.randomUUID(),
        status = getDummyShortenUrlOpStatusBo(),
        result = getDummyShortenUrlOpResultBo()
    )

fun getDummyShortenUrlOperationBoByUuidRight(uuid: UUID): Either<Nothing, ShortenUrlOperationBo> =
    getDummyShortenUrlOperationBoByUuid(uuid = uuid).right()

fun getDummyShortenUrlOperationBoByUuid(uuid: UUID): ShortenUrlOperationBo =
    ShortenUrlOperationBo(
        uuid = uuid,
        status = getDummyShortenUrlOpStatusBo(),
        result = getDummyShortenUrlOpResultBo()
    )

fun getDummyShortenUrlOpResultBo(): ShortenUrlOpResultBo =
    ShortenUrlOpResultBo(
        code = DEFAULT_STRING_VALUE,
        shortLink = DEFAULT_STRING_VALUE,
        fullShortLink = DEFAULT_STRING_VALUE,
        originalLink = DEFAULT_STRING_VALUE
    )

fun getDummyShortenUrlOpStatusBo(): ShortenUrlOpStatusBo =
    ShortenUrlOpStatusBo(
        isSuccessful = DEFAULT_BOOLEAN_VALUE
    )

fun getDummyIntRight(): Either<Nothing, Int> =
    DEFAULT_INT_VALUE.right()

fun getDummyFailureBoLeft(): Either<FailureBo, Nothing> =
    getDummyFailureBo().left()

fun getDummyFailureBo(): FailureBo =
    FailureBo.Unknown
