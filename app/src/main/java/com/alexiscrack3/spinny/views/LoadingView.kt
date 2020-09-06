package com.alexiscrack3.spinny.views

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.TextView
import com.alexiscrack3.spinny.R
import com.alexiscrack3.spinny.utils.DisplayUtil.dp

class LoadingView(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    init {
        inflate(context, R.layout.view_loading, this)

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.LoadingView)
        val textView = findViewById<TextView>(R.id.loading_text_view)
        textView.text = attributes.getString(R.styleable.LoadingView_text)

        this.elevation = Float.MAX_VALUE.dp
        attributes.recycle()
    }

}
