package com.dpfht.testfetchphotodata.model

data class Photo(
  val _id: Long? = 0,
  val albumId: Long = 0,
  val id: Long = 0,
  val title: String = "",
  val url: String = "",
  val thumbnailUrl: String = ""
)
