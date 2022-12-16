package org.deafsapps.shortlyapp.common.data.repository

import arrow.core.Either
import org.deafsapps.shortlyapp.common.data.utils.toBo
import org.deafsapps.shortlyapp.common.data.utils.toEntity
import org.deafsapps.shortlyapp.common.domain.model.FailureBo
import org.deafsapps.shortlyapp.common.domain.model.Url
import org.deafsapps.shortlyapp.urlhistory.domain.UrlHistoryDomainLayerContract
import org.deafsapps.shortlyapp.urlshortening.data.datasource.ShortenUrlDatasource
import org.deafsapps.shortlyapp.urlhistory.data.datasource.UrlHistoryDatasource
import org.deafsapps.shortlyapp.urlshortening.domain.UrlShorteningDomainLayerContract
import org.deafsapps.shortlyapp.urlshortening.domain.model.ShortenUrlOperationBo

object UrlRepository : UrlShorteningDomainLayerContract.DataLayer.Repository,
    UrlHistoryDomainLayerContract.DataLayer.Repository {

    lateinit var shortenUrlDatasource: ShortenUrlDatasource
    lateinit var urlHistoryDatasource: UrlHistoryDatasource

    override suspend fun shortenUrl(url: Url): Either<FailureBo, ShortenUrlOperationBo> =
        shortenUrlDatasource.getShortenedUrl(urlString = url.value)

    override suspend fun saveShortenedUrl(url: ShortenUrlOperationBo): Either<FailureBo, ShortenUrlOperationBo> =
        urlHistoryDatasource.saveUrl(urlEntity = url.toEntity()).map { it.toBo() }

    override suspend fun deleteShortenedUrl(url: ShortenUrlOperationBo): Either<FailureBo, ShortenUrlOperationBo> =
        urlHistoryDatasource.deleteUrl(urlEntity = url.toEntity()).map { it.toBo() }

}
