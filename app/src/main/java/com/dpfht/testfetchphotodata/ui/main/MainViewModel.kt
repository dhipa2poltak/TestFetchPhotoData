package com.dpfht.testfetchphotodata.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dpfht.testfetchphotodata.model.Photo
import com.dpfht.testfetchphotodata.repository.AppRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(private val appRepository: AppRepository) : ViewModel() {

  private val _showLoadingDialog = MutableLiveData(false)
  val showLoadingDialog: LiveData<Boolean>
  get() = _showLoadingDialog

  private val _toastMessage = MutableLiveData<String>()
  val toastMessage: LiveData<String>
  get() = _toastMessage

  fun clearToastMessage() {
    _toastMessage.value = ""
  }

  private val _photos = MutableLiveData<List<Photo>>()

  private val _foundPhotos = MutableLiveData<List<Photo>>()
  val foundPhotos: LiveData<List<Photo>>
  get() = _foundPhotos

  private val typeToken = object : TypeToken<List<Photo>>() {}.type

  fun start() {
    viewModelScope.launch(Dispatchers.Main) {
      val cache = withContext(Dispatchers.IO) { appRepository.getCache() }
      if (cache.isEmpty()) {
        getPhotosFromApi()
      } else {
        _showLoadingDialog.value = true
        _photos.value = withContext(Dispatchers.IO) { Gson().fromJson(cache, typeToken) } ?: ArrayList()
        _photos.value?.let { photos ->
          _foundPhotos.value = photos.sortedBy { photo -> photo.title.lowercase() }
        }
        _showLoadingDialog.value = false
      }
    }
  }

  private fun getPhotosFromApi() {
    viewModelScope.launch(Dispatchers.Main) {
      val tmp = withContext(Dispatchers.IO) { appRepository.getPhotosFromApi() }
      tmp?.let { listPhoto ->
        _showLoadingDialog.value = true
        val str = withContext(Dispatchers.IO) { Gson().toJson(listPhoto) }
        withContext(Dispatchers.IO) { appRepository.saveCache(str) }
        _showLoadingDialog.value = false

        _photos.value = listPhoto
        _foundPhotos.value = listPhoto.sortedBy { photo -> photo.title.lowercase() }
      }
    }
  }

  fun searchPhotoByTitle(title: String) {
    if (title.isEmpty()) {
      _photos.value?.let { photos ->
        _foundPhotos.value = photos.sortedBy { photo -> photo.title.lowercase() }
      }

      return
    }

    _photos.value?.let { photos ->
      val tmp = photos.filter { photo -> photo.title.lowercase().contains(title.lowercase()) }.sortedBy { photo -> photo.title.lowercase() }
      _foundPhotos.value = tmp
    }
  }

  fun syncData() {
    viewModelScope.launch(Dispatchers.Main) {
      withContext(Dispatchers.IO) { appRepository.deleteCache() }
      getPhotosFromApi()
    }
  }
}
