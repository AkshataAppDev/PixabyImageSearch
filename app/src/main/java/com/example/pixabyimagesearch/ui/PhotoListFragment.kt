package com.example.pixabyimagesearch.ui

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pixabyimagesearch.R
import com.example.pixabyimagesearch.databinding.FragmentPhotoListBinding
import com.example.pixabyimagesearch.utils.autoCleared
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class PhotoListFragment : BaseFragment() {

    private val TAG = "SearchFragment"

    companion object {
        private const val DEFAULT_SEARCH_QUERY: String = "fruits"
    }

    var adapter by autoCleared<PhotoListAdapterNew>()

    private lateinit var binding: FragmentPhotoListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentPhotoListBinding.inflate(inflater)
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        photoListViewModel =
            ViewModelProvider(this, providerFactory).get(PhotoListViewModel::class.java)

        binding.viewModel = photoListViewModel

        binding.photoListRecyclerView.layoutManager = LinearLayoutManager(activity)

        photoListViewModel.setQuery(DEFAULT_SEARCH_QUERY)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val rvadapter = PhotoListAdapterNew(
            appExecutors
        ) { photo ->

            activity?.let {
                MaterialAlertDialogBuilder(it, R.style.CustomDialogTheme)
                    .setTitle("Show Details")
                    .setMessage(resources.getString(R.string.dialog_question))
                    .setNegativeButton(resources.getString(R.string.yes)) { dialog, which ->
                        findNavController().navigate(PhotoListFragmentDirections.showPhoto(photo.id))
                    }
                    .setPositiveButton(resources.getString(R.string.no))
                    { dialog, which ->
                        dialog.dismiss()
                    }
                    .show()
            }
        }

        binding.photoListRecyclerView.adapter = rvadapter
        adapter = rvadapter

        photoListViewModel.results.observe(this, Observer {
            binding.searchStatus = it
            binding.resultCount = it?.data?.size ?: 0
            adapter.submitList(it.data)
        })

        setupSearchView()
    }

    fun setupSearchView() {

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                performSearchQuery(binding.searchView, query!!)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    fun performSearchQuery(searchView: View, query: String) {

        dismissKeyBoard(searchView.windowToken)
        photoListViewModel.setQuery(query)
    }

    fun dismissKeyBoard(windowToken: IBinder) {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(windowToken, 0)

    }
}