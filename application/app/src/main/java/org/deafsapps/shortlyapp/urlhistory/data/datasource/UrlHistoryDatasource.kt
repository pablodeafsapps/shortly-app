package org.deafsapps.shortlyapp.urlhistory.data.datasource

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.deafsapps.shortlyapp.common.data.db.ApplicationDatabase
import org.deafsapps.shortlyapp.common.domain.model.FailureBo
import org.deafsapps.shortlyapp.urlhistory.data.db.ShortenUrlOperationEntity
import java.util.UUID
import javax.inject.Inject

interface UrlHistoryDatasource {

    /**
     * Subscribes to a [Flow] bringing all available [ShortenUrlOperationEntity]
     */
    suspend fun fetchAllUrlsAsync(): Flow<Either<FailureBo, List<ShortenUrlOperationEntity>>>

    /**
     * Persists a given [ShortenUrlOperationEntity]
     */
    suspend fun saveUrl(urlEntity: ShortenUrlOperationEntity): Either<FailureBo, ShortenUrlOperationEntity>

    /**
     * Deletes an entity uniquely identified by [urlUuid]
     */
    suspend fun deleteUrl(urlUuid: UUID): Either<FailureBo, Int>

}

class ShortenedUrlHistoryDataSource @Inject constructor(
    private val roomDatabaseInstance: ApplicationDatabase
) : UrlHistoryDatasource {

    override suspend fun fetchAllUrlsAsync(): Flow<Either<FailureBo, List<ShortenUrlOperationEntity>>> =
        roomDatabaseInstance.urlsDao().getAllUrlsFlow().map { list ->
            if (list.isNotEmpty()) {
                list.right()
            } else {
                FailureBo.NoData.left()
            }
        }

    override suspend fun saveUrl(urlEntity: ShortenUrlOperationEntity): Either<FailureBo, ShortenUrlOperationEntity> =
        roomDatabaseInstance.urlsDao().insert(urlEntity = urlEntity).let { insertionCode ->
            if (insertionCode != 0L) {
                urlEntity.right()
            } else {
                FailureBo.Error("'Url' couldn't be saved").left()
            }
        }

    override suspend fun deleteUrl(urlUuid: UUID): Either<FailureBo, Int> =
        roomDatabaseInstance.urlsDao().delete(urlUuidString = urlUuid.toString()).let { deletionCode ->
            if (deletionCode > 0) {
                deletionCode.right()
            } else {
                FailureBo.Error("'Url' couldn't be deleted").left()
            }
        }

}
