package com.dpfht.testfetchphotodata.rest

import com.dpfht.testfetchphotodata.model.Photo
import retrofit2.http.GET

interface RestService {

  @GET("photos")
  suspend fun getPhotosFromApi(): List<Photo>?
}
