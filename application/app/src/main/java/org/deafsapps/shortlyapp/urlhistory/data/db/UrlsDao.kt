package org.deafsapps.shortlyapp.urlhistory.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UrlsDao {

    @Query("SELECT * FROM urls_table")
    fun getAllUrlsFlow(): Flow<List<ShortenUrlOperationEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(urlEntity: ShortenUrlOperationEntity) : Long

    @Query("DELETE FROM urls_table WHERE uuidString = :urlUuidString")
    suspend fun delete(urlUuidString: String) : Int

}
