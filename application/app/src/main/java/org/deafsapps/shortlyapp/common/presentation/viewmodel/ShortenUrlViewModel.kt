package org.deafsapps.shortlyapp.common.presentation.viewmodel

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.savedstate.SavedStateRegistryOwner
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.deafsapps.shortlyapp.common.base.StatefulViewModel
import org.deafsapps.shortlyapp.common.domain.DomainLayerContract
import org.deafsapps.shortlyapp.common.domain.model.Url
import org.deafsapps.shortlyapp.urlshortening.domain.model.ShortenUrlOperationBo
import org.deafsapps.shortlyapp.urlshortening.domain.model.UuidAdapter
import java.util.*


private const val UI_STATE_TAG = "ShortenUrlViewModelUiState"

class ShortenUrlViewModel(
    private val state: SavedStateHandle,
    val fetchAllShortenedUrlsAsyncUc: DomainLayerContract.PresentationLayer.FlowUseCase<Nothing, List<ShortenUrlOperationBo>>,
    val shortenAndPersistUrlUc: DomainLayerContract.PresentationLayer.UseCase<Url, ShortenUrlOperationBo>,
    val removeShortenedUrlUc: DomainLayerContract.PresentationLayer.UseCase<UUID, Int>,
) : StatefulViewModel<ShortenUrlViewModel.UiState>() {

    private val shortenedUrl: MutableStateFlow<String?> = MutableStateFlow(null)
    private val hasInputError: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    private val shortenedUrlHistory: MutableStateFlow<List<ShortenUrlOperationBo>> = MutableStateFlow(emptyList())
    private val defaultUiState: UiState = UiState()
    private val jsonUiStateAdapter: JsonAdapter<UiState> =
        Moshi.Builder().add(UuidAdapter).build().adapter(UiState::class.java)

    override val uiState: StateFlow<UiState> =
        combine(shortenedUrl, hasInputError, shortenedUrlHistory) { url, hasError, urlHistory ->
            UiState(shortenedUrl = url, hasInputError = hasError, shortenedUrlHistory = urlHistory).also {
                state[UI_STATE_TAG] = jsonUiStateAdapter.toJson(it)
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), getUiStateInitialValue())

    init {
        viewModelScope.launch {
            fetchAllShortenedUrlsAsyncUc().map { e ->
                e.fold({ failure ->
                    System.err.println(failure.msg)
                }, { operation ->
                    shortenedUrlHistory.update { operation }
                })
            }.collect()
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

    fun onRemoveShortenUrlSelected(urlUuid: UUID) {
        viewModelScope.launch {
            removeShortenedUrlUc(params = urlUuid).fold({ failure ->
                System.err.println(failure.msg)
            }, { registersAffected ->
                println("Registers removed: $registersAffected")
            })
        }
    }

    private fun getUiStateInitialValue(): UiState =
        state.get<String>(UI_STATE_TAG)?.let { uiStateString ->
            jsonUiStateAdapter.fromJson(uiStateString)
        } ?: run {
            defaultUiState
        }

    @JsonClass(generateAdapter = true)
    data class UiState(
        val shortenedUrl: String? = null,
        val hasInputError: Boolean? = null,
        val shortenedUrlHistory: List<ShortenUrlOperationBo> = emptyList()
    ) : StatefulViewModel.UiState

    class Provider(
        private val fetchAllShortenedUrlsAsyncUc: DomainLayerContract.PresentationLayer.FlowUseCase<Nothing, List<ShortenUrlOperationBo>>,
        private val shortenAndPersistUrlUc: DomainLayerContract.PresentationLayer.UseCase<Url, ShortenUrlOperationBo>,
        private val removeShortenedUrlUc: DomainLayerContract.PresentationLayer.UseCase<UUID, Int>,
        owner: SavedStateRegistryOwner
    ) : AbstractSavedStateViewModelFactory(owner, null) {

        override fun <T : ViewModel> create(
            key: String,
            modelClass: Class<T>,
            handle: SavedStateHandle
        ): T = ShortenUrlViewModel(
            handle,
            fetchAllShortenedUrlsAsyncUc,
            shortenAndPersistUrlUc,
            removeShortenedUrlUc
        ) as T

    }

}
