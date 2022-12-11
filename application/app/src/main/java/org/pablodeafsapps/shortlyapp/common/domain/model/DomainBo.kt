package org.pablodeafsapps.shortlyapp.common.domain.model

/**
 * A class which models any failure coming from any feature domain layer
 */
sealed class FailureBo(var msg: String) {
    class Error(msg: String) : FailureBo(msg = msg)
}
