package org.deafsapps.shortlyapp.common.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.*

/**
 * Provides the implementer with a public [StateFlow] of a [UiState] child type. This immutable
 * field brings a mutable backing property which allows to privately update it.
 */
abstract class StatefulViewModel<T : StatefulViewModel.UiState> : ViewModel() {

    abstract val uiState: StateFlow<T>

    interface UiState

}
