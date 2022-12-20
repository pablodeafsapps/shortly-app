package org.deafsapps.shortlyapp.urlhistory.di

import dagger.Module
import dagger.Provides
import org.deafsapps.shortlyapp.common.data.repository.UrlRepository
import org.deafsapps.shortlyapp.common.domain.DomainLayerContract
import org.deafsapps.shortlyapp.urlhistory.data.datasource.ShortenedUrlHistoryDataSource
import org.deafsapps.shortlyapp.urlhistory.data.datasource.UrlHistoryDatasource
import org.deafsapps.shortlyapp.urlhistory.domain.UrlHistoryDomainLayerContract
import org.deafsapps.shortlyapp.urlhistory.domain.usecase.FetchAllShortenedUrlsAsyncUc
import org.deafsapps.shortlyapp.urlhistory.domain.usecase.RemoveShortenedUrlUc
import org.deafsapps.shortlyapp.urlshortening.domain.model.ShortenUrlOperationBo
import javax.inject.Named
import javax.inject.Singleton

@Module
class UrlHistoryModule {

    @Provides
    @Named("fetchAllShortenedUrlsAsyncUc")
    fun providesFetchAllShortenedUrlsAsyncUc(
        usecase: FetchAllShortenedUrlsAsyncUc
    ) : @JvmSuppressWildcards DomainLayerContract.PresentationLayer.FlowUseCase<Unit, List<ShortenUrlOperationBo>> = usecase

    @Provides
    @Named("removeShortenedUrlUc")
    fun providesRemoveShortenedUrlUc(
        usecase: RemoveShortenedUrlUc
    ) : @JvmSuppressWildcards DomainLayerContract.PresentationLayer.UseCase<ShortenUrlOperationBo, Int> = usecase

    @Singleton
    @Provides
    fun providesUrlHistoryRepository(
        datasource: UrlHistoryDatasource
    ): UrlHistoryDomainLayerContract.DataLayer.Repository =
        UrlRepository.apply {
            urlHistoryDatasource = datasource
        }

    @Provides
    fun providesUrlHistoryDatasource(datasource: ShortenedUrlHistoryDataSource): UrlHistoryDatasource = datasource

}
