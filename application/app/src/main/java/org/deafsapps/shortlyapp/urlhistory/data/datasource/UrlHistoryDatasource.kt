package org.deafsapps.shortlyapp.urlhistory.data.datasource

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import org.deafsapps.shortlyapp.common.data.db.ApplicationDatabase
import org.deafsapps.shortlyapp.common.domain.model.FailureBo
import org.deafsapps.shortlyapp.urlhistory.data.db.ShortenUrlOperationEntity

interface UrlHistoryDatasource {

    /**
     * Persists a given [ShortenUrlOperationEntity]
     */
    suspend fun saveUrl(urlEntity: ShortenUrlOperationEntity): Either<FailureBo, ShortenUrlOperationEntity>

    /**
     * Deletes a given [ShortenUrlOperationEntity]
     */
    suspend fun deleteUrl(urlEntity: ShortenUrlOperationEntity): Either<FailureBo, ShortenUrlOperationEntity>

}

class ShortenUrlHistoryDataSource(
    private val roomDatabaseInstance: ApplicationDatabase
) : UrlHistoryDatasource {

    override suspend fun saveUrl(urlEntity: ShortenUrlOperationEntity): Either<FailureBo, ShortenUrlOperationEntity> =
        roomDatabaseInstance.urlsDao().insert(urlEntity = urlEntity).let { insertionCode ->
            if (insertionCode != 0L) {
                urlEntity.right()
            } else {
                FailureBo.Error("'Url' couldn't be saved").left()
            }
        }

    override suspend fun deleteUrl(urlEntity: ShortenUrlOperationEntity): Either<FailureBo, ShortenUrlOperationEntity> =
        roomDatabaseInstance.urlsDao().delete(urlEntity = urlEntity).let { deletionCode ->
            if (deletionCode == 1) {
                urlEntity.right()
            } else {
                FailureBo.Error("'Url' couldn't be deleted").left()
            }
        }

}
