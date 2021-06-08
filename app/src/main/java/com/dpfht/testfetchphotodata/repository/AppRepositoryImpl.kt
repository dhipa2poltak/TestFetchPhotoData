package com.dpfht.testfetchphotodata.repository

import com.dpfht.testfetchphotodata.db.DatabaseHelper
import com.dpfht.testfetchphotodata.model.Photo
import com.dpfht.testfetchphotodata.rest.RestService

class AppRepositoryImpl(
  private val restService: RestService,
  private val dbHelper: DatabaseHelper): AppRepository {

  override fun getCache(): String {
    return dbHelper.getCache()
  }

  override fun saveCache(cache: String): Long {
    return dbHelper.saveCache(cache)
  }

  override fun deleteCache(): Int {
    return dbHelper.deleteCache()
  }

  override suspend fun getPhotosFromApi(): List<Photo>? {
    return restService.getPhotosFromApi()
  }
}
