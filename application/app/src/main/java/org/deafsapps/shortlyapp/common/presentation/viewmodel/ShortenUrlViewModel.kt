package org.deafsapps.shortlyapp.common.presentation.viewmodel

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.savedstate.SavedStateRegistryOwner
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.deafsapps.shortlyapp.common.base.StatefulViewModel
import org.deafsapps.shortlyapp.common.domain.DomainLayerContract
import org.deafsapps.shortlyapp.common.domain.model.Url
import org.deafsapps.shortlyapp.urlshortening.domain.model.ShortenUrlOperationBo

class ShortenUrlViewModel(
    private val state: SavedStateHandle,
    val shortenAndPersistUrlUc: DomainLayerContract.PresentationLayer.UseCase<Url, ShortenUrlOperationBo>,
    val fetchAllShortenedUrlsAsyncUc: DomainLayerContract.PresentationLayer.FlowUseCase<Nothing, List<ShortenUrlOperationBo>>
) : StatefulViewModel<ShortenUrlViewModel.UiState>() {

    private val shortenedUrl: MutableStateFlow<String?> = MutableStateFlow(null)
    private val hasInputError: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val shortenedUrlHistory: MutableStateFlow<List<ShortenUrlOperationBo>> = MutableStateFlow(emptyList())
    private val defaultUiState: UiState = UiState()

    override val uiState: StateFlow<UiState> = combine(shortenedUrl, hasInputError, shortenedUrlHistory) { url, hasError, urlHistory ->
        UiState(shortenedUrl = url, hasInputError = hasError, shortenedUrlHistory = urlHistory)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), defaultUiState)

    init {
        viewModelScope.launch {
            fetchAllShortenedUrlsAsyncUc().map { e ->
                e.fold({ failure ->
                    System.err.println(failure.msg)
                }, { operation ->
                    shortenedUrlHistory.update { operation }
                })
            }
        }
    }

    fun onShortenUrlSelected(urlString: String) {
        viewModelScope.launch {
            shortenAndPersistUrlUc(params = Url(value = urlString)).fold({ failure ->
                hasInputError.update { true }
                System.err.println(failure.msg)
            }, { operation ->
                hasInputError.update { false }
                shortenedUrl.update { operation.result.shortLink }
                println("${operation.status}, ${operation.result.shortLink}")
            })
        }
    }

    data class UiState(
        val shortenedUrl: String? = null,
        val hasInputError: Boolean = false,
        val shortenedUrlHistory: List<ShortenUrlOperationBo> = emptyList()
    ) : StatefulViewModel.UiState

    class Provider(
        private val shortenAndPersistUrlUc: DomainLayerContract.PresentationLayer.UseCase<Url, ShortenUrlOperationBo>,
        private val fetchAllShortenedUrlsAsyncUc: DomainLayerContract.PresentationLayer.FlowUseCase<Nothing, List<ShortenUrlOperationBo>>,
        owner: SavedStateRegistryOwner
    ) : AbstractSavedStateViewModelFactory(owner, null) {

        override fun <T : ViewModel> create(
            key: String,
            modelClass: Class<T>,
            handle: SavedStateHandle
        ): T = ShortenUrlViewModel(handle, shortenAndPersistUrlUc, fetchAllShortenedUrlsAsyncUc) as T

    }

}
