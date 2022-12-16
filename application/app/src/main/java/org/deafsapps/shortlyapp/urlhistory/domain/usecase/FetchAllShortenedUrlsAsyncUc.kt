package org.deafsapps.shortlyapp.urlhistory.domain.usecase

import arrow.core.Either
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import org.deafsapps.shortlyapp.common.domain.DomainLayerContract
import org.deafsapps.shortlyapp.common.domain.model.FailureBo
import org.deafsapps.shortlyapp.urlhistory.domain.UrlHistoryDomainLayerContract
import org.deafsapps.shortlyapp.urlshortening.domain.model.ShortenUrlOperationBo

class FetchAllShortenedUrlsAsyncUc(
    private val urlHistoryRepository: UrlHistoryDomainLayerContract.DataLayer.Repository
) : DomainLayerContract.PresentationLayer.FlowUseCase<Unit, List<ShortenUrlOperationBo>> {

    override suspend fun invoke(
        params: Unit?,
        dispatcherWorker: CoroutineDispatcher
    ): Flow<Either<FailureBo, List<ShortenUrlOperationBo>>> =
        withContext(dispatcherWorker) {
            urlHistoryRepository.fetchAllShortenedUrlsAsync()
        }

}
