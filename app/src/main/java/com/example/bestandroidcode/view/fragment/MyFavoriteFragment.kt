package com.example.bestandroidcode.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.bestandroidcode.databinding.FragmentMyFavoriteBinding
import com.example.bestandroidcode.view.adapter.MyFavoriteAdapter
import com.example.bestandroidcode.viewModel.MyFavoriteViewModel
import com.google.android.material.snackbar.Snackbar

/**
 * Created by Kuan Wah Teo on 08/11/2020
 **/

class MyFavoriteFragment: Fragment() {
    private val myFavoriteViewModel by activityViewModels<MyFavoriteViewModel>()

    private lateinit var viewDataBinding: FragmentMyFavoriteBinding
    private val myFavoriteAdapter: MyFavoriteAdapter by lazy {
        MyFavoriteAdapter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        viewDataBinding = FragmentMyFavoriteBinding.inflate(inflater, container, false).apply {
            myFavoriteList.adapter = myFavoriteAdapter
        }
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        myFavoriteViewModel.myFavoriteList.observe(viewLifecycleOwner, {
            myFavoriteAdapter.submitList(it)
        })

        myFavoriteViewModel.errorMessage.observe(viewLifecycleOwner, {
            if (!it.isNullOrEmpty()) {
                Snackbar.make(viewDataBinding.root, it, Snackbar.LENGTH_LONG).show()
            }
        })
    }

    override fun onStart() {
        super.onStart()

        myFavoriteViewModel.retrieveMyFavoriteList()
    }
}