package org.deafsapps.shortlyapp

import android.app.Application
import org.deafsapps.shortlyapp.common.di.ApplicationComponent
import org.deafsapps.shortlyapp.common.di.DaggerApplicationComponent
import org.deafsapps.shortlyapp.common.di.UtilsModule
import org.deafsapps.shortlyapp.main.di.MainComponent
import org.deafsapps.shortlyapp.main.di.MainComponentFactoryProvider

class ShortlyApplication : Application(), MainComponentFactoryProvider {

    private lateinit var appComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerApplicationComponent.factory()
            .create(UtilsModule(applicationContext = applicationContext))
    }

    override fun provideMainComponentFactory(): MainComponent.Factory =
        appComponent.mainComponentFactory()

}
