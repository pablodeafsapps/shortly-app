package org.deafsapps.shortlyapp.common.data.model

import okhttp3.ResponseBody
import org.deafsapps.shortlyapp.common.domain.model.ErrorMessage

/**
 * A class which models any failure coming from any feature domain layer
 */
sealed class FailureDto(var msg: String) {

    companion object {
        private const val DEFAULT_ERROR_CODE = -1
    }

    class RequestError(
        val code: Int = DEFAULT_ERROR_CODE,
        msg: String,
        val errorBody: ResponseBody? = null
    ) : FailureDto(msg = msg)
    object NoData : FailureDto(msg = ErrorMessage.ERROR_NO_DATA)
    class Error(msg: String) : FailureDto(msg = msg)
    object Unknown : FailureDto(msg = ErrorMessage.ERROR_UNKNOWN)
}
