package org.deafsapps.shortlyapp.common.data.utils

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import org.deafsapps.shortlyapp.common.data.model.FailureDto
import org.deafsapps.shortlyapp.common.domain.model.ErrorMessage
import org.deafsapps.shortlyapp.common.domain.model.FailureBo
import org.deafsapps.shortlyapp.urlhistory.data.db.ShortenUrlOperationEntity
import org.deafsapps.shortlyapp.urlshortening.domain.model.ShortenUrlOpResultBo
import org.deafsapps.shortlyapp.urlshortening.domain.model.ShortenUrlOpStatusBo
import org.deafsapps.shortlyapp.urlshortening.domain.model.ShortenUrlOperationBo
import retrofit2.Response
import java.util.*

fun ShortenUrlOperationBo.toEntity(): ShortenUrlOperationEntity =
    ShortenUrlOperationEntity(
        uuidString = uuid.toString(),
        isSuccessful = status.isSuccessful,
        code = result.code,
        shortLink = result.shortLink,
        fullShortLink = result.fullShortLink,
        originalLink = result.originalLink
    )

fun List<ShortenUrlOperationEntity>.toBoList(): List<ShortenUrlOperationBo> =
    map { entity -> entity.toBo() }

fun ShortenUrlOperationEntity.toBo(): ShortenUrlOperationBo =
    ShortenUrlOperationBo(
        uuid = UUID.fromString(uuidString),
        status = ShortenUrlOpStatusBo(isSuccessful = isSuccessful),
        result = ShortenUrlOpResultBo(
            code = code,
            shortLink = shortLink,
            fullShortLink = fullShortLink,
            originalLink = originalLink
        )
    )

/**
 * This extension function provides a proceeding to handle with 'Retrofit' [Response] objects, so that
 * a parametrized 'Either' type is returned
 */
fun <T, R> Response<T>.retrofitSafeCall(
    transform: (T) -> R,
    errorHandler: (Response<T>).() -> Either<FailureBo, Nothing> = { handleDataSourceError() }
): Either<FailureBo, R> =
    try {
        run {
            if (isSuccessful) {
                val body = body()
                if (body != null) {
                    transform(body).right()
                } else {
                    errorHandler()
                }
            } else {
                errorHandler()
            }
        }
    } catch (exception: Exception) {
        System.err.println("Error: ${exception.message}")
        errorHandler()
    }

/**
 * Provides a mechanism to handle HTTP errors coming from a Retrofit [Response]. It returns an
 * [Either] which models the [FailureBo] to be transmitted beyond the domain layer.
 */
fun <T> Response<T>?.handleDataSourceError(): Either<FailureBo, Nothing> =
    when (this?.code()) {
        in 400..403 -> FailureDto.RequestError(code = 400, msg = ErrorMessage.ERROR_BAD_REQUEST)
        404 -> FailureDto.NoData
        in 405..499 -> FailureDto.RequestError(code = 400, msg = ErrorMessage.ERROR_BAD_REQUEST)
        in 500..599 -> FailureDto.RequestError(code = 500, msg = ErrorMessage.ERROR_SERVER)
        else -> FailureDto.Unknown
    }.dtoToBoFailure().left()

/**
 * Maps a [FailureDto] into a [FailureBo]
 */
fun FailureDto.dtoToBoFailure(): FailureBo = when (this) {
    is FailureDto.RequestError -> FailureBo.RequestError(msg = msg)
    FailureDto.NoData -> FailureBo.NoData
    is FailureDto.Error -> FailureBo.ServerError(msg = msg)
    FailureDto.Unknown -> FailureBo.Unknown
}
