package org.deafsapps.shortlyapp.urlshortening.di

import dagger.Module
import dagger.Provides
import org.deafsapps.shortlyapp.common.data.repository.UrlRepository
import org.deafsapps.shortlyapp.common.domain.DomainLayerContract
import org.deafsapps.shortlyapp.common.domain.model.Url
import org.deafsapps.shortlyapp.urlshortening.data.datasource.ShortenUrlDatasource
import org.deafsapps.shortlyapp.urlshortening.data.datasource.ShrtcodeDatasource
import org.deafsapps.shortlyapp.urlshortening.domain.UrlShorteningDomainLayerContract
import org.deafsapps.shortlyapp.urlshortening.domain.model.ShortenUrlOperationBo
import org.deafsapps.shortlyapp.urlshortening.domain.usecase.ShortenAndPersistUrlUc
import javax.inject.Named
import javax.inject.Singleton

@Module
class UrlShorteningModule {

    @Provides
    @Named("shortenAndPersistUrlUc")
    fun providesShortenAndPersistUrlUc(
        usecase: ShortenAndPersistUrlUc
    ) : @JvmSuppressWildcards DomainLayerContract.PresentationLayer.UseCase<Url, ShortenUrlOperationBo> = usecase

    @Singleton
    @Provides
    fun providesShortenUrlRepositoryUrlHistory(
        datasource: ShortenUrlDatasource
    ): UrlShorteningDomainLayerContract.DataLayer.Repository =
        UrlRepository.apply {
            shortenUrlDatasource = datasource
        }

    @Provides
    fun providesShortenUrlDatasource(datasource: ShrtcodeDatasource): ShortenUrlDatasource = datasource

}
