package org.pablodeafsapps.shortlyapp.urlshortening.data.repository

import arrow.core.Either
import org.pablodeafsapps.shortlyapp.common.domain.model.FailureBo
import org.pablodeafsapps.shortlyapp.urlshortening.data.datasource.ShortenUrlDatasource
import org.pablodeafsapps.shortlyapp.urlshortening.domain.UrlShorteningDomainLayerContract
import org.pablodeafsapps.shortlyapp.urlshortening.domain.model.ShortenUrlOperationBo
import org.pablodeafsapps.shortlyapp.urlshortening.domain.model.Url

object ShortenUrlRepository : UrlShorteningDomainLayerContract.DataLayer.Repository {

    lateinit var shortenUrlDatasource: ShortenUrlDatasource

    override suspend fun shortenUrl(url: Url): Either<FailureBo, ShortenUrlOperationBo> =
        shortenUrlDatasource.getShortenedUrl(urlString = url.value)

}
