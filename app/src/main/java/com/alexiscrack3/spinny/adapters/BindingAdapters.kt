package com.alexiscrack3.spinny.adapters

import android.view.View
import androidx.databinding.BindingAdapter
import com.alexiscrack3.spinny.api.Resource

@BindingAdapter("app:goneUnless")
fun setGoneUnless(view: View, resource: Resource<*>) {
    view.visibility = if (resource is Resource.Success) {
        View.GONE
    } else {
        View.VISIBLE
    }
}
