package org.deafsapps.shortlyapp.urlshortening.domain.usecase

import arrow.core.Either
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.deafsapps.shortlyapp.common.domain.DomainLayerContract
import org.deafsapps.shortlyapp.common.domain.model.FailureBo
import org.deafsapps.shortlyapp.common.domain.model.Url
import org.deafsapps.shortlyapp.urlhistory.domain.UrlHistoryDomainLayerContract
import org.deafsapps.shortlyapp.urlshortening.domain.UrlShorteningDomainLayerContract
import org.deafsapps.shortlyapp.urlshortening.domain.model.ShortenUrlOperationBo
import org.deafsapps.shortlyapp.utils.getDummyFailureBoLeft
import org.deafsapps.shortlyapp.utils.getDummyShortenUrlOperationBoByUuid
import org.deafsapps.shortlyapp.utils.getDummyShortenUrlOperationBoByUuidRight
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.util.UUID

@OptIn(ExperimentalCoroutinesApi::class)
class ShortenAndPersistUrlUcTest {

    private lateinit var sut: DomainLayerContract.PresentationLayer.UseCase<Url, ShortenUrlOperationBo>
    private lateinit var testScheduler: TestCoroutineScheduler
    private lateinit var mockShortenUrlRepository: UrlShorteningDomainLayerContract.DataLayer.Repository
    private lateinit var mockUrlHistoryRepository: UrlHistoryDomainLayerContract.DataLayer.Repository

    @Before
    fun setUp() {
        mockShortenUrlRepository = mock()
        mockUrlHistoryRepository = mock()
        sut = ShortenAndPersistUrlUc(
            shortenUrlRepository = mockShortenUrlRepository,
            urlHistoryRepository = mockUrlHistoryRepository
        )

        testScheduler = TestCoroutineScheduler()
        Dispatchers.setMain(StandardTestDispatcher(testScheduler))
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `When usecase is invoked with invalid parameters, a 'FailureBo' instance is returned`() = runTest {
        // given
        val invalidParams: Url = Url("")
        // when
        val response = sut(params = invalidParams)
        // then
        assertTrue(response.isLeft() && response is Either.Left<FailureBo>)
    }

    @Test
    fun `When usecase is invoked with valid parameters, and the persistence operation fails, a 'FailureBo' instance is returned`() = runTest {
        // given
        val validParams: Url = Url("example.org/very/long/link.html")
        val validUuid: UUID = UUID.randomUUID()
        whenever(mockShortenUrlRepository.shortenUrl(url = validParams)).thenReturn(getDummyShortenUrlOperationBoByUuidRight(uuid = validUuid))
        whenever(mockUrlHistoryRepository.saveShortenedUrl(url = getDummyShortenUrlOperationBoByUuid(uuid = validUuid))).thenReturn(getDummyFailureBoLeft())
        // when
        val response = sut(params = validParams)
        // then
        assertTrue(response.isLeft() && response is Either.Left<FailureBo>)
    }

    @Test
    fun `When usecase is invoked with valid parameters, and the shorten url operation fails, a 'FailureBo' instance is returned`() = runTest {
        // given
        val validParams: Url = Url("example.org/very/long/link.html")
        whenever(mockShortenUrlRepository.shortenUrl(url = validParams)).thenReturn(getDummyFailureBoLeft())
        // when
        val response = sut(params = validParams)
        // then
        assertTrue(response.isLeft() && response is Either.Left<FailureBo>)
    }

    @Test
    fun `When usecase is invoked with valid parameters, and all repository operations are successful, a 'ShortenUrlOperationBo' instance is returned`() = runTest {
        // given
        val validParams: Url = Url("example.org/very/long/link.html")
        val validUuid: UUID = UUID.randomUUID()
        whenever(mockShortenUrlRepository.shortenUrl(url = validParams)).thenReturn(getDummyShortenUrlOperationBoByUuidRight(uuid = validUuid))
        whenever(mockUrlHistoryRepository.saveShortenedUrl(url = getDummyShortenUrlOperationBoByUuid(uuid = validUuid))).thenReturn(getDummyShortenUrlOperationBoByUuidRight(uuid = validUuid))
        // when
        val response = sut(params = validParams)
        // then
        assertTrue(response.isRight() && response is Either.Right<ShortenUrlOperationBo>)
    }

}
