package org.deafsapps.shortlyapp.urlshortening.domain.usecase

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.left
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.deafsapps.shortlyapp.common.domain.DomainLayerContract
import org.deafsapps.shortlyapp.common.domain.model.FailureBo
import org.deafsapps.shortlyapp.common.domain.model.Url
import org.deafsapps.shortlyapp.common.utils.isValid
import org.deafsapps.shortlyapp.urlhistory.domain.UrlHistoryDomainLayerContract
import org.deafsapps.shortlyapp.urlshortening.domain.UrlShorteningDomainLayerContract
import org.deafsapps.shortlyapp.urlshortening.domain.model.ShortenUrlOperationBo

class ShortenAndPersistUrlUc(
    private val shortenUrlRepository: UrlShorteningDomainLayerContract.DataLayer.Repository,
    private val urlHistoryRepository: UrlHistoryDomainLayerContract.DataLayer.Repository
) : DomainLayerContract.PresentationLayer.UseCase<Url, ShortenUrlOperationBo> {

    override suspend fun invoke(
        params: Url?,
        dispatcherWorker: CoroutineDispatcher
    ): Either<FailureBo, ShortenUrlOperationBo> =
        params?.takeIf { url -> url.isValid() }?.let { url ->
            withContext(dispatcherWorker) {
                shortenUrlRepository.shortenUrl(url = url).flatMap { r ->
                    urlHistoryRepository.saveShortenedUrl(url = r)
                }
            }
        } ?: run { FailureBo.Error("Invalid input params").left() }

}
