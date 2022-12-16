package org.deafsapps.shortlyapp.urlhistory.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UrlsDao {

    @Query("SELECT * FROM urls_table")
    suspend fun getAllUrls(): List<ShortenUrlOperationEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(urlEntity: ShortenUrlOperationEntity) : Long

    @Delete
    suspend fun delete(urlEntity: ShortenUrlOperationEntity) : Int

}
