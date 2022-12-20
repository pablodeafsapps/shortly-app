package org.deafsapps.shortlyapp.common.domain.model

@JvmInline
value class Url(val value: String)

/**
 * A class which models any failure coming from any feature domain layer
 */
sealed class FailureBo(val msg: String) {
    class RequestError(msg: String) : FailureBo(msg = msg)
    class ServerError(msg: String) : FailureBo(msg = msg)
    object NoData : FailureBo(msg = ErrorMessage.ERROR_NO_DATA)
    class Error(msg: String) : FailureBo(msg = msg)
    object Unknown : FailureBo(msg = ErrorMessage.ERROR_UNKNOWN)
}

/**
 * This object gathers all error messages available throughout the app
 */
object ErrorMessage {
    const val ERROR_BAD_REQUEST = "Bad Request"
    const val ERROR_NO_DATA = "No Data"
    const val ERROR_SERVER = "Server Error"
    const val ERROR_UNKNOWN = "Unknown Error"
}
