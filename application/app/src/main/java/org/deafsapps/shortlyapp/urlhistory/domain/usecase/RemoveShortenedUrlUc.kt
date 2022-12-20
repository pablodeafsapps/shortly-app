package org.deafsapps.shortlyapp.urlhistory.domain.usecase

import arrow.core.Either
import arrow.core.left
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.deafsapps.shortlyapp.common.domain.DomainLayerContract
import org.deafsapps.shortlyapp.common.domain.model.FailureBo
import org.deafsapps.shortlyapp.urlhistory.domain.UrlHistoryDomainLayerContract
import org.deafsapps.shortlyapp.urlshortening.domain.model.ShortenUrlOperationBo
import javax.inject.Inject

class RemoveShortenedUrlUc @Inject constructor(
    private val urlHistoryRepository: UrlHistoryDomainLayerContract.DataLayer.Repository
) : DomainLayerContract.PresentationLayer.UseCase<ShortenUrlOperationBo, Int> {

    override suspend fun invoke(
        params: ShortenUrlOperationBo?,
        dispatcherWorker: CoroutineDispatcher
    ): Either<FailureBo, Int> =
        params?.let { url ->
            withContext(dispatcherWorker) {
                urlHistoryRepository.removeShortenedUrl(url = url)
            }
        } ?: run { FailureBo.Error("Invalid input params").left() }

}
