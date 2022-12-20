package org.deafsapps.shortlyapp.common.di

import android.content.Context
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import org.deafsapps.shortlyapp.common.data.db.ApplicationDatabase
import org.deafsapps.shortlyapp.common.data.repository.UrlRepository
import org.deafsapps.shortlyapp.common.utils.getRetrofitInstance
import org.deafsapps.shortlyapp.urlshortening.data.datasource.ShrtcodeDatasource
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class UtilsModule(private val applicationContext: Context) {

    @Singleton
    @Provides
    fun providesRetrofitInstance(converterFactory: Converter.Factory) : Retrofit =
        getRetrofitInstance(converterFactory = converterFactory)

    @Singleton
    @Provides
    fun providesConverterFactory() : Converter.Factory {
        val moshi = Moshi.Builder().build()
        return MoshiConverterFactory.create(moshi)
    }

    @Singleton
    @Provides
    fun providesRoomDatabaseInstance(): ApplicationDatabase =
        Room.databaseBuilder(
            applicationContext,
            ApplicationDatabase::class.java, "shorten-url-db"
        ).build()

}
