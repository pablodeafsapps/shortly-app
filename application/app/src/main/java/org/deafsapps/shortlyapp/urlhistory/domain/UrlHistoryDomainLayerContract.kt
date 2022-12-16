package org.deafsapps.shortlyapp.urlhistory.domain

import arrow.core.Either
import kotlinx.coroutines.flow.Flow
import org.deafsapps.shortlyapp.common.domain.DomainLayerContract
import org.deafsapps.shortlyapp.common.domain.model.FailureBo
import org.deafsapps.shortlyapp.urlhistory.data.db.ShortenUrlOperationEntity
import org.deafsapps.shortlyapp.urlshortening.domain.model.ShortenUrlOpResultBo
import org.deafsapps.shortlyapp.urlshortening.domain.model.ShortenUrlOperationBo

/**
 * Gathers all protocols to interact with the 'url history' domain layer.
 *
 * Includes several interfaces which define the contracts to comply, both on the 'presentation' and
 * the 'data' side.
 */
interface UrlHistoryDomainLayerContract : DomainLayerContract {

    /**
     * Defines the requirements for an entity to become the 'data-layer' of this feature
     */
    interface DataLayer {

        /**
         * Defines the requirements that an entity must fulfill to become the 'url shortening'
         * data layer
         */
        interface Repository {
            /**
             * Subscribes to a [Flow] bringing all available [ShortenUrlOperationEntity]
             *
             * @return A [Flow] to all available [ShortenUrlOperationBo] persisted so far or a
             * [FailureBo] if none
             */
            suspend fun fetchAllShortenedUrlsAsync(): Flow<Either<FailureBo, List<ShortenUrlOperationBo>>>

            /**
             * Persists a shortened link
             *
             * @param url to be persisted
             * @return The [ShortenUrlOperationBo] persisted if it is successful or a [FailureBo]
             * otherwise
             */
            suspend fun saveShortenedUrl(url: ShortenUrlOperationBo): Either<FailureBo, ShortenUrlOperationBo>

            /**
             * Deletes a shortened link
             *
             * @param url to be deleted
             * @return The [ShortenUrlOperationBo] deleted if it is successful or a [FailureBo]
             * otherwise
             */
            suspend fun deleteShortenedUrl(url: ShortenUrlOperationBo): Either<FailureBo, ShortenUrlOperationBo>

        }

    }

}
