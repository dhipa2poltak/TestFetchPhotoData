package com.dpfht.testfetchphotodata.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class DatabaseHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

  companion object {
    private const val DATABASE_NAME = "TestPhoto.db"
    private const val DATABASE_VERSION = 1
  }

  override fun onCreate(db: SQLiteDatabase?) {
    db?.execSQL("CREATE TABLE IF NOT EXISTS tbl_cache (" +
        BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, cache TEXT)")
  }

  override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    db?.execSQL("DROP TABLE IF EXISTS tbl_cache")
    onCreate(db)
  }

  fun getCache(): String {
    val db = readableDatabase
    val cursor = db.query("tbl_cache", null, null, null, null, null, null)

    val count = cursor.count
    if (count > 0) {
      cursor.moveToFirst()
      val cache = cursor.getString(cursor.getColumnIndex("cache"))
      cursor.close()
      return cache
    }

    return ""
  }

  fun saveCache(cache: String): Long {
    val db = writableDatabase
    val cv = ContentValues()
    cv.put("cache", cache)

    return db.insert("tbl_cache", null, cv)
  }
  
  fun deleteCache(): Int {
    val db = writableDatabase

    return db.delete("tbl_cache", null, null)
  }
}
