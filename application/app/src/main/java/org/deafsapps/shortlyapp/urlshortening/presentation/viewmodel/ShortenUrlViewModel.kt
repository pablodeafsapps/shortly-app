package org.deafsapps.shortlyapp.urlshortening.presentation.viewmodel

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.savedstate.SavedStateRegistryOwner
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.deafsapps.shortlyapp.common.base.StatefulViewModel
import org.deafsapps.shortlyapp.common.domain.DomainLayerContract
import org.deafsapps.shortlyapp.urlshortening.domain.model.ShortenUrlOperationBo
import org.deafsapps.shortlyapp.urlshortening.domain.model.Url

class ShortenUrlViewModel(
    private val state: SavedStateHandle,
    val shortenUrlUc: DomainLayerContract.PresentationLayer.UseCase<Url, ShortenUrlOperationBo>
) : StatefulViewModel<ShortenUrlViewModel.UiState>() {

    private val defaultUiState: UiState = UiState()
    override var _uiState: MutableStateFlow<UiState> = MutableStateFlow(defaultUiState)

    fun onShortenUrlSelected(urlString: String) {
        viewModelScope.launch {
            shortenUrlUc(params = Url(value = urlString)).fold({ failure ->
                _uiState.update { UiState(hasInputError = true) }
                System.err.println(failure.msg)
            }, { operation ->
                _uiState.update { UiState(shortenedUrl = operation.result.shortLink) }
                println("${operation.status}, ${operation.result.shortLink}")
            })
        }
    }

    data class UiState(
        val shortenedUrl: String? = null,
        val hasInputError: Boolean = false
    ) : StatefulViewModel.UiState

    class Provider(
        private val shortenUrlUc: DomainLayerContract.PresentationLayer.UseCase<Url, ShortenUrlOperationBo>,
        owner: SavedStateRegistryOwner
    ) : AbstractSavedStateViewModelFactory(owner, null) {

        override fun <T : ViewModel> create(
            key: String,
            modelClass: Class<T>,
            handle: SavedStateHandle
        ): T = ShortenUrlViewModel(handle, shortenUrlUc) as T

    }

}
