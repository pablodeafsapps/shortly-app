package org.deafsapps.shortlyapp.common.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import org.deafsapps.shortlyapp.urlhistory.data.db.ShortenUrlOperationEntity
import org.deafsapps.shortlyapp.urlhistory.data.db.UrlsDao

@Database(
    entities = [ ShortenUrlOperationEntity::class ],
    version = 1
)
abstract class ApplicationDatabase : RoomDatabase() {

    abstract fun urlsDao(): UrlsDao

}
