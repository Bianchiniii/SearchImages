package com.bianchini.vinicius.matheus.imagesSearch.presentation.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bianchini.vinicius.matheus.imagesSearch.data.repository.ImgurRepository
import com.bianchini.vinicius.matheus.imagesSearch.domain.model.DataContainerResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(private val repository: ImgurRepository) : ViewModel() {
    val toastMessage = MutableLiveData<String>()
    val imageResponse = MutableLiveData<DataContainerResponse>()

    init {
        getAllImages()
    }

    fun getAllImages() {
        viewModelScope.launch {
            try {
                repository.getCatsImage().let { response ->
                    if (response.isSuccessful) {
                        imageResponse.postValue(response.body())
                    } else {
                        Log.d("ERROR_GET_IMAGE", "getAllImages error: ${response.errorBody()}")
                    }
                }
            } catch (e: Exception) {
                toastMessage.postValue("Não foi possível buscar as imagens!")
            }
        }
    }
}
