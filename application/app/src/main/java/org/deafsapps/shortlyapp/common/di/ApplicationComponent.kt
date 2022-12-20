package org.deafsapps.shortlyapp.common.di

import dagger.Component
import org.deafsapps.shortlyapp.main.di.MainComponent
import org.deafsapps.shortlyapp.main.di.MainModule
import org.deafsapps.shortlyapp.urlhistory.di.UrlHistoryModule
import org.deafsapps.shortlyapp.urlshortening.di.UrlShorteningModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        MainModule::class, UrlShorteningModule::class, UrlHistoryModule::class, UtilsModule::class
    ]
)
interface ApplicationComponent {

    @Component.Factory
    interface Factory {
        fun create(
            utilsModule: UtilsModule
        ): ApplicationComponent
    }

    fun mainComponentFactory(): MainComponent.Factory

}
