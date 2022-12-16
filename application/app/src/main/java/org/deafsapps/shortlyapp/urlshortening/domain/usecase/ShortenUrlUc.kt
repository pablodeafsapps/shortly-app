package org.deafsapps.shortlyapp.urlshortening.domain.usecase

import arrow.core.Either
import arrow.core.left
import kotlinx.coroutines.CoroutineDispatcher
import org.deafsapps.shortlyapp.common.domain.DomainLayerContract
import org.deafsapps.shortlyapp.common.domain.model.FailureBo
import org.deafsapps.shortlyapp.urlshortening.domain.UrlShorteningDomainLayerContract
import org.deafsapps.shortlyapp.urlshortening.domain.model.ShortenUrlOperationBo
import org.deafsapps.shortlyapp.urlshortening.domain.model.Url
import org.deafsapps.shortlyapp.urlshortening.domain.utils.isValid

class ShortenUrlUc(
    private val repository: UrlShorteningDomainLayerContract.DataLayer.Repository
) : DomainLayerContract.PresentationLayer.UseCase<Url, ShortenUrlOperationBo> {

    override suspend fun invoke(
        params: Url?,
        dispatcherWorker: CoroutineDispatcher
    ): Either<FailureBo, ShortenUrlOperationBo> =
        params?.takeIf { url -> url.isValid() }?.let { url ->
            repository.shortenUrl(url = url)
        } ?: run { FailureBo.Error("Invalid input params").left() }

}
