package org.deafsapps.shortlyapp.urlshortening.data.datasource

import arrow.core.Either
import org.deafsapps.shortlyapp.common.data.utils.retrofitSafeCall
import org.deafsapps.shortlyapp.common.domain.model.FailureBo
import org.deafsapps.shortlyapp.urlshortening.data.service.ShrtcodeApiService
import org.deafsapps.shortlyapp.urlshortening.data.utils.toBo
import org.deafsapps.shortlyapp.urlshortening.domain.model.ShortenUrlOperationBo
import retrofit2.Retrofit
import javax.inject.Inject

interface ShortenUrlDatasource {

    /**
     * Gets a shortened version of a Url String
     *
     * @param urlString to be shortened
     * @return The [ShortenUrlOperationBo] obtained if it is successful or a [FailureBo] otherwise
     */
    suspend fun getShortenedUrl(urlString: String): Either<FailureBo, ShortenUrlOperationBo>

}

class ShrtcodeDatasource @Inject constructor(val retrofit: Retrofit) : ShortenUrlDatasource {

    override suspend fun getShortenedUrl(urlString: String): Either<FailureBo, ShortenUrlOperationBo> =
        retrofit.create(ShrtcodeApiService::class.java).getShortenedUrl(urlString = urlString)
            .retrofitSafeCall(transform = { it.toBo() })

}
