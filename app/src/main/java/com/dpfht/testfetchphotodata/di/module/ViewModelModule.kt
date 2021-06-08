package com.dpfht.testfetchphotodata.di.module

import com.dpfht.testfetchphotodata.ui.main.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
  viewModel {
    MainViewModel(get())
  }
}