package com.example.bestandroidcode.utils

import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.widget.AppCompatImageView
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.bestandroidcode.R
import com.example.bestandroidcode.model.Category
import com.google.android.material.button.MaterialButton

/**
 * Created by Kuan Wah Teo on 08/11/2020
 **/

@BindingAdapter("catPhoto")
fun AppCompatImageView.displayCatPhoto(catPhotoURL: String?) {
    catPhotoURL?.let {
        Glide.with(context).load(it).into(this)
    }
}

@BindingAdapter("cardViewVisibility")
fun CardView.setCardViewVisibility(catImageURL: String?) {
    visibility = if (catImageURL.isNullOrEmpty()) {
        View.INVISIBLE
    } else {
        View.VISIBLE
    }
}

@BindingAdapter("favorite")
fun MaterialButton.setFavorite(isFavoriteCatImage: Boolean) = when(isFavoriteCatImage) {
    true -> {
        text = context.getString(R.string.remove_from_favorite)
        visibility = View.VISIBLE
    }
    else -> {
        text = context.getString(R.string.add_to_favorite)
        visibility = View.VISIBLE
    }
}

@BindingAdapter("categoryList")
fun Spinner.setCategoryList(categoryList: List<Category>) {
    val categoryNameList = ArrayList<String>()
    categoryList.forEach { category ->
        categoryNameList.add(category.name)
    }
    val arrayAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, categoryNameList)
    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

    adapter = arrayAdapter
}

