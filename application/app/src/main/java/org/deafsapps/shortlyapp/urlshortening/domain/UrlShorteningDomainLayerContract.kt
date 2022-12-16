package org.deafsapps.shortlyapp.urlshortening.domain

import arrow.core.Either
import org.deafsapps.shortlyapp.common.domain.DomainLayerContract
import org.deafsapps.shortlyapp.common.domain.model.FailureBo
import org.deafsapps.shortlyapp.common.domain.model.Url
import org.deafsapps.shortlyapp.urlshortening.domain.model.ShortenUrlOperationBo

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
         * Defines the requirements for an entity to become the 'data-layer' of this feature
         */
        interface Repository {
            /**
             * Shortens a [Url]
             *
             * @param url to be shortened
             * @return A [ShortenUrlOperationBo] data if it is successful or a [FailureBo] otherwise
             */
            suspend fun shortenUrl(url: Url): Either<FailureBo, ShortenUrlOperationBo>

        }

    }

}
