package org.deafsapps.shortlyapp.urlshortening.domain

import arrow.core.Either
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.deafsapps.shortlyapp.common.domain.DomainLayerContract
import org.deafsapps.shortlyapp.common.domain.model.FailureBo
import org.deafsapps.shortlyapp.urlshortening.domain.model.ShortenUrlOperationBo
import org.deafsapps.shortlyapp.urlshortening.domain.model.Url

/**
 * Gathers all protocols to interact with the 'url shortening' domain layer.
 *
 * Includes several interfaces which define the contracts to comply, both on the 'presentation' and
 * the 'data' side.
 */
interface UrlShorteningDomainLayerContract : DomainLayerContract {

    /**
     * Defines the requirements for an entity to become the 'data-layer' of the app
     */
    interface DataLayer {

        /**
         * Defines the requirements that an entity must fulfill to become the 'url shortening'
         * data layer
         */
        interface Repository {
            /**
             * Logs-in a user according to certain info parameters
             * @param url to be shortened
             * @return A [ShortenUrlOperationBo] data if it is successful or a [FailureBo] otherwise
             */
            suspend fun shortenUrl(url: Url): Either<FailureBo, ShortenUrlOperationBo>

        }

    }

}
