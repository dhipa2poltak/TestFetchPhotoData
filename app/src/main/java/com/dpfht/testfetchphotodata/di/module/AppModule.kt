package com.dpfht.testfetchphotodata.di.module

import android.content.Context
import com.dpfht.testfetchphotodata.Config
import com.dpfht.testfetchphotodata.db.DatabaseHelper
import com.dpfht.testfetchphotodata.repository.AppRepository
import com.dpfht.testfetchphotodata.repository.AppRepositoryImpl
import com.dpfht.testfetchphotodata.rest.RestService
import com.squareup.picasso.Picasso
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

  single { providePicasso(androidContext()) }

  single { provideDatabaseHelper(androidContext()) }
  single { provideAppRepository(get(), get()) }

  single { provideOkHttpClient() }
  single { provideRetrofit(get(), Config.API_BASE_URL) }
  single { provideRestService(get()) }
}

fun providePicasso(context: Context): Picasso {
  return Picasso.Builder(context).build()
}

fun provideAppRepository(restService: RestService, dbHelper: DatabaseHelper): AppRepository {
  return AppRepositoryImpl(restService, dbHelper)
}

fun provideDatabaseHelper(context: Context): DatabaseHelper {
  return DatabaseHelper(context)
}

fun provideOkHttpClient(): OkHttpClient {
  return OkHttpClient.Builder()
    .build()
}

fun provideRetrofit(client: OkHttpClient, baseUrl: String): Retrofit {
  return Retrofit.Builder()
    .baseUrl(baseUrl)
    .addConverterFactory(GsonConverterFactory.create())
    .client(client)
    .build()
}

fun provideRestService(retrofit: Retrofit): RestService {
  return retrofit.create(RestService::class.java)
}
