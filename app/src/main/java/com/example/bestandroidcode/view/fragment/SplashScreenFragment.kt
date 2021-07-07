package com.example.bestandroidcode.view.fragment

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.bestandroidcode.databinding.FragmentSplashScreenBinding
import com.example.bestandroidcode.viewModel.TheCatViewModel

class SplashScreenFragment : Fragment() {
    private val theCatViewModel by activityViewModels<TheCatViewModel>()

    private lateinit var viewDataBinding: FragmentSplashScreenBinding
    private val mainActivity: AppCompatActivity by lazy {
        requireActivity() as AppCompatActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentSplashScreenBinding.inflate(inflater, container, false)

        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        viewDataBinding.retryButton.setOnClickListener {
            theCatViewModel.retrieveCategoryList()
            viewDataBinding.progressBar.visibility = View.VISIBLE
            viewDataBinding.unableLoadGroup.visibility = View.GONE
        }

        theCatViewModel.categoryList.observe(viewLifecycleOwner, {
            Handler().postDelayed({
                val destination = SplashScreenFragmentDirections.actionSplashScreenToSearch()
                findNavController().navigate(destination)
            }, 3000)
        })
        theCatViewModel.errorMessage.observe(viewLifecycleOwner, {
            Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
            viewDataBinding.progressBar.visibility = View.GONE
            viewDataBinding.unableLoadGroup.visibility = View.VISIBLE
        })
    }

    override fun onStart() {
        super.onStart()

        mainActivity.supportActionBar?.hide()
        theCatViewModel.retrieveCategoryList()
    }

    override fun onStop() {
        mainActivity.supportActionBar?.show()
        theCatViewModel.reset()
        super.onStop()
    }
}