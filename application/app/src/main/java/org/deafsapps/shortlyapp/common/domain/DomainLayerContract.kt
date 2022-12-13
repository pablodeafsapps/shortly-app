package org.deafsapps.shortlyapp.common.domain

import arrow.core.Either
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.deafsapps.shortlyapp.common.domain.model.FailureBo

/**
 * Gathers all protocols to interact with the domain layer.
 *
 * Includes several interfaces which define the contracts to comply, particularly for the presentation
 * layer
 */
interface DomainLayerContract {

    /**
     * Defines the requirements that an entity must fulfill to become the 'url shortening'
     * presentation layer
     */
    interface PresentationLayer {

        /**
         * Defines baseline use-case using Kotlin Coroutines
         */
        interface UseCase<in T, out S> {

            suspend operator fun invoke(
                params: T? = null,
                dispatcherWorker: CoroutineDispatcher = Dispatchers.IO
            ): Either<FailureBo, S>

        }

    }

}
