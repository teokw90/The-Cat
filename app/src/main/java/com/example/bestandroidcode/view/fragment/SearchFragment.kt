package com.example.bestandroidcode.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.bestandroidcode.R
import com.example.bestandroidcode.databinding.CardViewCatPhotoBinding
import com.example.bestandroidcode.databinding.FragmentSearchBinding
import com.example.bestandroidcode.viewModel.TheCatViewModel
import com.google.android.material.snackbar.Snackbar

class SearchFragment : Fragment() {
    private val theCatViewModel by activityViewModels<TheCatViewModel>()

    private lateinit var viewDataBinding: FragmentSearchBinding
    private lateinit var includeViewDataBinding: CardViewCatPhotoBinding

    private val loadingSnackbar by lazy {
        Snackbar.make(viewDataBinding.root, R.string.in_progress, Snackbar.LENGTH_INDEFINITE)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        viewDataBinding = FragmentSearchBinding.inflate(inflater, container, false)

        includeViewDataBinding = viewDataBinding.catPhotoCardView
        includeViewDataBinding.apply {
            viewModel = theCatViewModel
        }

        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner

        theCatViewModel.catPhotoURL.observe(viewLifecycleOwner, {
            resumeFromSearchCatPhotoCompleted()
        })

        theCatViewModel.errorMessage.observe(viewLifecycleOwner, {
            if (!it.isNullOrEmpty()) {
                resumeFromSearchCatPhotoCompleted()
                Snackbar.make(viewDataBinding.root, it, Snackbar.LENGTH_LONG).show()
            }
        })
    }

    override fun onStart() {
        super.onStart()

        viewDataBinding.searchRandomlyButton.setOnClickListener {
            pauseForSearchingCatPhoto()
            theCatViewModel.searchCatRandomly()

        }
        viewDataBinding.advanceSearchButton.setOnClickListener { navigateToAdvanceSearch() }
    }

    private fun pauseForSearchingCatPhoto() {
        loadingSnackbar.show()
        viewDataBinding.searchRandomlyButton.isEnabled = false
        viewDataBinding.advanceSearchButton.isEnabled = false
    }

    private fun resumeFromSearchCatPhotoCompleted() {
        if (loadingSnackbar.isShown) loadingSnackbar.dismiss()
        viewDataBinding.searchRandomlyButton.isEnabled = true
        viewDataBinding.advanceSearchButton.isEnabled = true
    }

    private fun navigateToAdvanceSearch() {
        val destination = SearchFragmentDirections.actionSearchToAdvanceSearch()
        findNavController().navigate(destination)
    }

    override fun onStop() {
        theCatViewModel.reset()
        super.onStop()
    }
}