package com.alexiscrack3.spinny.adapters

import android.view.View
import com.alexiscrack3.spinny.api.Result
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test

class BindingAdaptersKtTest {

    @Test
    fun `visibility is set to gone when result is success`() {
        val view = mock<View>()
        setGoneUnless(view, Result.Success(0))

        verify(view).visibility = View.GONE
    }

    @Test
    fun `visibility is set to gone when result is failure`() {
        val view = mock<View>()
        setGoneUnless(
            view, Result.Failure<Any>(
                Throwable()
            )
        )

        verify(view).visibility = View.GONE
    }

    @Test
    fun `visibility is set to visible when result is loading`() {
        val view = mock<View>()
        setGoneUnless(view, Result.Loading<Int>())

        verify(view).visibility = View.VISIBLE
    }
}
