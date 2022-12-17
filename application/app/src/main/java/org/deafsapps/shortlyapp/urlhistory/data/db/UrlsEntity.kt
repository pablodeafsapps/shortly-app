package org.deafsapps.shortlyapp.urlhistory.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "urls_table")
data class ShortenUrlOperationEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val isSuccessful: Boolean,
    val code: String,
    @ColumnInfo(name = "short_link") val shortLink: String,
    @ColumnInfo(name = "full_short_link") val fullShortLink: String,
    @ColumnInfo(name = "original_link") val originalLink: String
)
