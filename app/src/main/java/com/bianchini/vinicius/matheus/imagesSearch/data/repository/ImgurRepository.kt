package com.bianchini.vinicius.matheus.imagesSearch.data.repository

import com.bianchini.vinicius.matheus.imagesSearch.network.ImgurAPI
import javax.inject.Inject

class ImgurRepository
@Inject
constructor(private val api: ImgurAPI) {
    suspend fun getCatsImage() = api.getCatsImage()
}
