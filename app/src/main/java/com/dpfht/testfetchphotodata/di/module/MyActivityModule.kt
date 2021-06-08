package com.dpfht.testfetchphotodata.di.module

import android.app.Activity
import androidx.appcompat.app.AlertDialog
import com.dpfht.testfetchphotodata.R
import com.dpfht.testfetchphotodata.ui.adapter.PhotoAdapter
import com.squareup.picasso.Picasso
import org.koin.dsl.module

val myActivityModule = module {
  factory { providePhotoAdapter(get()) }
  factory { provideLoadingDialog(it[0]) }
}

fun providePhotoAdapter(picasso: Picasso): PhotoAdapter {
  return PhotoAdapter(mutableListOf(), picasso)
}

fun provideLoadingDialog(activity: Activity): AlertDialog {
  return AlertDialog.Builder(activity)
    .setCancelable(false)
    .setView(R.layout.dialog_loading)
    .create()
}
