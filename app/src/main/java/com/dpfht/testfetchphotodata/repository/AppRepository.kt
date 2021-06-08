package com.dpfht.testfetchphotodata.repository

import com.dpfht.testfetchphotodata.model.Photo

interface AppRepository {

  fun getCache(): String
  fun saveCache(cache: String): Long
  fun deleteCache(): Int
  suspend fun getPhotosFromApi(): List<Photo>?
}
