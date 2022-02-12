package com.bianchini.vinicius.matheus.imagesSearch.network

import com.bianchini.vinicius.matheus.imagesSearch.BuildConfig
import com.bianchini.vinicius.matheus.imagesSearch.domain.model.DataContainerResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface ImgurAPI {

    @GET("gallery/search/?q=cats")
    @Headers("Authorization: Client-ID ${BuildConfig.CLIENTE_ID}")
    suspend fun getCatsImage(): Response<DataContainerResponse>
}