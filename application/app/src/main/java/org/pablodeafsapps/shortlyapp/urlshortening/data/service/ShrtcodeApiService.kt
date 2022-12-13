package org.pablodeafsapps.shortlyapp.urlshortening.data.service

import org.pablodeafsapps.shortlyapp.urlshortening.data.model.ShortenUrlOperationDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * This interface is used by Retrofit to conform the API service queries
 */
interface ShrtcodeApiService {

    /**
     * Returns a shortened version of the input url
     */
    @GET("shorten")
    suspend fun getShortenedUrl(@Query("url") urlString: String): Response<ShortenUrlOperationDto>

}
