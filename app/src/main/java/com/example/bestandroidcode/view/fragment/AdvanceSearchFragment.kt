package com.example.bestandroidcode.view.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.bestandroidcode.R
import com.example.bestandroidcode.databinding.CardViewCatPhotoBinding
import com.example.bestandroidcode.databinding.FragmentAdvanceSearchBinding
import com.example.bestandroidcode.viewModel.TheCatViewModel
import com.google.android.material.snackbar.Snackbar

class AdvanceSearchFragment : Fragment() {
    private val theCatViewModel by activityViewModels<TheCatViewModel>()

    private lateinit var viewDataBinding: FragmentAdvanceSearchBinding
    private lateinit var includeViewDataBinding: CardViewCatPhotoBinding

    private val loadingSnackbar by lazy {
        Snackbar.make(viewDataBinding.root, R.string.in_progress, Snackbar.LENGTH_INDEFINITE)
    }

    private var firstNumber: Int = 0
    private var secondNumber: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewDataBinding = FragmentAdvanceSearchBinding.inflate(inflater, container, false).apply {
            viewModel = theCatViewModel
        }

        includeViewDataBinding = viewDataBinding.catPhotoCardView
        includeViewDataBinding.apply {
            viewModel = theCatViewModel
        }

        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Set the lifecycle owner to the lifecycle of the view
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner

        theCatViewModel.catPhotoURL.observe(viewLifecycleOwner, {
            generateAndDisplayMathQuestion()
            resumeFromSearchCatPhotoCompleted()
            viewDataBinding.mathAnswerEditText.text = null
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

        generateAndDisplayMathQuestion()

        viewDataBinding.mathAnswerEditText.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
            override fun afterTextChanged(s: Editable?) = verifyInput()
        })

        viewDataBinding.submitAnswerButton.setOnClickListener {
            pauseForSearchingCatPhoto()
            val selectedCategory = viewDataBinding.categorySpinner.selectedItem.toString()
            theCatViewModel.searchCatBasedOn(selectedCategory)

        }
    }

    private fun generateAndDisplayMathQuestion() {
        firstNumber = (1..10).random()
        secondNumber = (0..10).random()
        val mathQuestion = "$firstNumber + $secondNumber = ?"

        viewDataBinding.mathQuestionTextInput.hint = mathQuestion
    }

    private fun verifyInput(){
        val answer = viewDataBinding.mathAnswerEditText.text.toString()
        viewDataBinding.submitAnswerButton.isEnabled = answer.isNotBlank() && (answer.toInt() == firstNumber + secondNumber)
    }

    private fun pauseForSearchingCatPhoto() {
        loadingSnackbar.show()
        viewDataBinding.submitAnswerButton.isEnabled = false
    }

    private fun resumeFromSearchCatPhotoCompleted() {
        if (loadingSnackbar.isShown) loadingSnackbar.dismiss()
        viewDataBinding.submitAnswerButton.isEnabled = true
    }

    override fun onStop() {
        theCatViewModel.reset()
        super.onStop()
    }
}