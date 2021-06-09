package com.dpfht.testfetchphotodata.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dpfht.testfetchphotodata.R
import com.dpfht.testfetchphotodata.databinding.MainFragmentBinding
import com.dpfht.testfetchphotodata.ui.adapter.PhotoAdapter
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MainFragment : Fragment() {

  companion object {
    fun newInstance() = MainFragment()
  }

  private lateinit var binding: MainFragmentBinding

  private val viewModel: MainViewModel by viewModel()
  private val adapter: PhotoAdapter by inject()

  private lateinit var prgDialog: AlertDialog

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    prgDialog = get { parametersOf(requireActivity()) }
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    setHasOptionsMenu(true)
    binding = MainFragmentBinding.inflate(layoutInflater, container, false)

    return binding.root
  }

  @Suppress("DEPRECATION")
  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    binding.rvPhoto.layoutManager =
      LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    binding.rvPhoto.addItemDecoration(
      DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
    )
    binding.rvPhoto.adapter = adapter

    viewModel.showLoadingDialog.observe(viewLifecycleOwner, { value ->
      if (value) {
        prgDialog.show()
      } else {
        prgDialog.dismiss()
      }
    })

    viewModel.toastMessage.observe(viewLifecycleOwner, { msg ->
      if (msg.length > 0) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
        viewModel.clearToastMessage()
      }
    })

    viewModel.foundPhotos.observe(viewLifecycleOwner, { photos ->
      adapter.swapData(photos)
    })

    binding.ibSearch.setOnClickListener {
      binding.rvPhoto.scrollToPosition(0)
      viewModel.searchPhotoByTitle(binding.etSearch.text.toString())
    }

    if (viewModel.foundPhotos.value?.size ?: 0 == 0) {
      viewModel.start()
    }
  }

  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    inflater.inflate(R.menu.main_menu, menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.menu_sync -> {
        binding.etSearch.setText("")
        binding.rvPhoto.scrollToPosition(0)
        viewModel.syncData()
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }
}
