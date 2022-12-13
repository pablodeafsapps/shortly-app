package org.deafsapps.shortlyapp.urlshortening.data.repository

import arrow.core.Either
import org.deafsapps.shortlyapp.common.domain.model.FailureBo
import org.deafsapps.shortlyapp.urlshortening.data.datasource.ShortenUrlDatasource
import org.deafsapps.shortlyapp.urlshortening.domain.UrlShorteningDomainLayerContract
import org.deafsapps.shortlyapp.urlshortening.domain.model.ShortenUrlOperationBo
import org.deafsapps.shortlyapp.urlshortening.domain.model.Url

object ShortenUrlRepository : UrlShorteningDomainLayerContract.DataLayer.Repository {

    lateinit var shortenUrlDatasource: ShortenUrlDatasource

    override suspend fun shortenUrl(url: Url): Either<FailureBo, ShortenUrlOperationBo> =
        shortenUrlDatasource.getShortenedUrl(urlString = url.value)

}
