package com.alexiscrack3.spinny.utils

import androidx.appcompat.app.ActionBar

fun ActionBar.isUpButtonEnabled(): Boolean {
    return this.displayOptions.and(ActionBar.DISPLAY_HOME_AS_UP) != 0
}