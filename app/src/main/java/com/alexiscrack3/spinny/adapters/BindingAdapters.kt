package com.alexiscrack3.spinny.adapters

import android.view.View
import androidx.databinding.BindingAdapter
import com.alexiscrack3.spinny.api.Result

@BindingAdapter("app:goneUnless")
fun setGoneUnless(view: View, result: Result<*>?) {
    view.visibility = if (result is Result.Loading) {
        View.VISIBLE
    } else {
        View.GONE
    }
}
