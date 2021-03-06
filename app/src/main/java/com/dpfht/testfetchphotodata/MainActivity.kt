package com.dpfht.testfetchphotodata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dpfht.testfetchphotodata.databinding.MainActivityBinding
import com.dpfht.testfetchphotodata.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

  private lateinit var binding: MainActivityBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = MainActivityBinding.inflate(layoutInflater)
    setContentView(binding.root)

    if (savedInstanceState == null) {
      supportFragmentManager.beginTransaction()
        .replace(R.id.container, MainFragment.newInstance())
        .commitNow()
    }
  }
}