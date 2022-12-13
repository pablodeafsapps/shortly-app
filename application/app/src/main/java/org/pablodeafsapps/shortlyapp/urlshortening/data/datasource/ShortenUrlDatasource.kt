package org.pablodeafsapps.shortlyapp.urlshortening.data.datasource

import arrow.core.Either
import org.pablodeafsapps.shortlyapp.common.data.model.FailureDto
import org.pablodeafsapps.shortlyapp.common.data.utils.retrofitSafeCall
import org.pablodeafsapps.shortlyapp.common.domain.model.FailureBo
import org.pablodeafsapps.shortlyapp.urlshortening.data.model.ShortenUrlOperationDto
import org.pablodeafsapps.shortlyapp.urlshortening.data.service.ShrtcodeApiService
import org.pablodeafsapps.shortlyapp.urlshortening.data.utils.toBo
import org.pablodeafsapps.shortlyapp.urlshortening.domain.model.ShortenUrlOperationBo
import retrofit2.Retrofit

interface ShortenUrlDatasource {

    /**
     *
     */
    suspend fun getShortenedUrl(urlString: String): Either<FailureBo, ShortenUrlOperationBo>

}

class ShrtcodeDatasource(val retrofit: Retrofit) : ShortenUrlDatasource {

    override suspend fun getShortenedUrl(urlString: String): Either<FailureBo, ShortenUrlOperationBo> =
        retrofit.create(ShrtcodeApiService::class.java).getShortenedUrl(urlString = urlString)
            .retrofitSafeCall(transform = { it.toBo() })

}
