package com.alexiscrack3.spinny.adapters

import android.view.View
import com.alexiscrack3.spinny.api.Resource
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test

class BindingAdaptersKtTest {

    @Test
    fun `visibility is set to gone when resource is success`() {
        val view = mock<View>()

        setGoneUnless(view, Resource.Success(0))

        verify(view).visibility = View.GONE
    }

    @Test
    fun `visibility is set to gone when resource is failure`() {
        val view = mock<View>()

        setGoneUnless(
            view, Resource.Failure<Any>(
                Throwable()
            )
        )

        verify(view).visibility = View.GONE
    }

    @Test
    fun `visibility is set to visible when resource is loading`() {
        val view = mock<View>()

        setGoneUnless(view, Resource.Loading<Int>())

        verify(view).visibility = View.VISIBLE
    }

    @Test
    fun `visibility is set to gone when view is not visible`() {
        val view = mock<View>()

        setVisibleOrGone(view, false)

        verify(view).visibility = View.GONE
    }

    @Test
    fun `visibility is set to visible when view is visible`() {
        val view = mock<View>()

        setVisibleOrGone(view, true)

        verify(view).visibility = View.VISIBLE
    }
}
