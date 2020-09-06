package com.alexiscrack3.spinny.utils

import android.content.res.Resources

object DisplayUtil {
    val Int.dp: Int get() = (this / Resources.getSystem().displayMetrics.density).toInt()
    val Int.px: Int get() = (this * Resources.getSystem().displayMetrics.density).toInt()

    val Float.dp: Float get() = (this / Resources.getSystem().displayMetrics.density)
    val Float.px: Float get() = (this * Resources.getSystem().displayMetrics.density)
}
