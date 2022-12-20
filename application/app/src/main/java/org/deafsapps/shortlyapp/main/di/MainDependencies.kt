package org.deafsapps.shortlyapp.main.di

import androidx.savedstate.SavedStateRegistryOwner
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import org.deafsapps.shortlyapp.main.presentation.view.MainActivity

interface MainComponentFactoryProvider {
    fun provideMainComponentFactory() : MainComponent.Factory
}

@Module(subcomponents = [ MainComponent::class ])
object MainModule

@Subcomponent(modules = [MainPresentationModule::class])
interface MainComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(
            presentationModule: MainPresentationModule
        ): MainComponent
    }

    fun inject(activity: MainActivity)

}

@Module
class MainPresentationModule(private val owner: SavedStateRegistryOwner) {

    @Provides
    fun providesSavedStateRegistryOwner() : SavedStateRegistryOwner = owner

}
