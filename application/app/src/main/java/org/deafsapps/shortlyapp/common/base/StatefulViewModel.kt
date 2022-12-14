package org.deafsapps.shortlyapp.common.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Provides the implementer with a public [StateFlow] of a [UiState] child type. This immutable
 * field brings a mutable backing property which allows to privately update it.
 */
abstract class StatefulViewModel<T : StatefulViewModel.UiState> : ViewModel() {

    val uiState: StateFlow<T>
        get() = _uiState.asStateFlow()
    protected abstract var _uiState: MutableStateFlow<T>

    interface UiState

}
