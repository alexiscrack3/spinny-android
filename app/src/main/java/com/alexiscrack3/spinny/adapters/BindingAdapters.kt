package com.alexiscrack3.spinny.adapters

import android.view.View
import androidx.databinding.BindingAdapter
import com.alexiscrack3.spinny.api.Resource

@BindingAdapter("app:goneUnless")
fun setGoneUnless(view: View, resource: Resource<*>?) {
    view.visibility = if (resource is Resource.Loading) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

@BindingAdapter("app:visibleUnless")
fun setVisibleUnless(view: View, visible: Boolean) {
    view.visibility = if (visible) {
        View.VISIBLE
    } else {
        View.GONE
    }
}
