package org.deafsapps.shortlyapp.urlhistory.domain.usecase

import arrow.core.Either
import arrow.core.left
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.deafsapps.shortlyapp.common.domain.DomainLayerContract
import org.deafsapps.shortlyapp.common.domain.model.FailureBo
import org.deafsapps.shortlyapp.urlhistory.domain.UrlHistoryDomainLayerContract
import java.util.*

class RemoveShortenedUrlUc(
    private val urlHistoryRepository: UrlHistoryDomainLayerContract.DataLayer.Repository
) : DomainLayerContract.PresentationLayer.UseCase<UUID, Int> {

    override suspend fun invoke(
        params: UUID?,
        dispatcherWorker: CoroutineDispatcher
    ): Either<FailureBo, Int> =
        params?.let { uuid ->
            withContext(dispatcherWorker) {
                urlHistoryRepository.removeShortenedUrl(urlUuid = uuid)
            }
        } ?: run { FailureBo.Error("Invalid input params").left() }

}
