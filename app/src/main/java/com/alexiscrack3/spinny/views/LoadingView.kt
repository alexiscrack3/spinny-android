package com.alexiscrack3.spinny.views

import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.TextView
import com.alexiscrack3.spinny.R

class LoadingView(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {
    val Int.dp: Int get() = (this / Resources.getSystem().displayMetrics.density).toInt()
    val Int.px: Int get() = (this * Resources.getSystem().displayMetrics.density).toInt()

    val Float.dp: Float get() = (this / Resources.getSystem().displayMetrics.density)
    val Float.px: Float get() = (this * Resources.getSystem().displayMetrics.density)

    init {
        inflate(context, R.layout.view_loading, this)

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.LoadingView)
        val textView = findViewById<TextView>(R.id.loading_text_view)
        textView.text = attributes.getString(R.styleable.LoadingView_text)

        this.elevation = Float.MAX_VALUE.dp
        attributes.recycle()
    }

}
