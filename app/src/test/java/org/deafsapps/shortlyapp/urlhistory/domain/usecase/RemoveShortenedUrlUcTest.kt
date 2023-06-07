package org.deafsapps.shortlyapp.urlhistory.domain.usecase

import arrow.core.Either
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.*
import org.deafsapps.shortlyapp.common.domain.DomainLayerContract
import org.deafsapps.shortlyapp.common.domain.model.FailureBo
import org.deafsapps.shortlyapp.common.domain.model.Url
import org.deafsapps.shortlyapp.urlhistory.domain.UrlHistoryDomainLayerContract
import org.deafsapps.shortlyapp.urlshortening.domain.UrlShorteningDomainLayerContract
import org.deafsapps.shortlyapp.urlshortening.domain.model.ShortenUrlOperationBo
import org.deafsapps.shortlyapp.urlshortening.domain.usecase.ShortenAndPersistUrlUc
import org.deafsapps.shortlyapp.utils.*
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.util.*

class RemoveShortenedUrlUcTest {

    private lateinit var sut: DomainLayerContract.PresentationLayer.UseCase<ShortenUrlOperationBo, Int>
    private lateinit var testScheduler: TestCoroutineScheduler
    private lateinit var mockUrlHistoryRepository: UrlHistoryDomainLayerContract.DataLayer.Repository

    @Before
    fun setUp() {
        mockUrlHistoryRepository = mock()
        sut = RemoveShortenedUrlUc(
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
        val invalidParams: ShortenUrlOperationBo? = null
        // when
        val response = sut(params = invalidParams)
        // then
        assertTrue(response.isLeft() && response is Either.Left<FailureBo>)
    }

    @Test
    fun `When usecase is invoked with valid parameters, and the repository operation fails, a 'FailureBo' instance is returned`() = runTest {
        // given
        val validParams: ShortenUrlOperationBo = getDummyShortenUrlOperationBo()
        whenever(mockUrlHistoryRepository.removeShortenedUrl(url = validParams)).thenReturn(getDummyFailureBoLeft())
        // when
        val response = sut(params = validParams)
        // then
        assertTrue(response.isLeft() && response is Either.Left<FailureBo>)
    }

    @Test
    fun `When usecase is invoked with valid parameters, and the repository operation is successful, an 'Int' is returned`() = runTest {
        // given
        val validParams: ShortenUrlOperationBo = getDummyShortenUrlOperationBo()
        whenever(mockUrlHistoryRepository.removeShortenedUrl(url = validParams)).thenReturn(getDummyIntRight())
        // when
        val response = sut(params = validParams)
        // then
        assertTrue(response.isRight() && response.getOrNull()?.equals(1) == true)
    }

}
