package org.deafsapps.shortlyapp

import android.app.Application

class ShortlyApplication : Application() {

//    private lateinit var appComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
//        appComponent = DaggerApplicationComponent.factory()
//            .create(UtilsModule(applicationContext = applicationContext))
    }

//    override fun provideMainComponentFactory(): MainComponent.Factory =
//        appComponent.mainComponentFactory()
//
//    override fun provideCharactersComponentFactory(): CharactersComponent.Factory =
//        appComponent.charactersComponentFactory()
//
//    override fun providesEpisodesComponent(): EpisodesComponent =
//        appComponent.episodesComponentFactory().create()
//
//    override fun providesLoginComponent(): LoginComponent =
//        appComponent.loginComponentFactory().create()

}
