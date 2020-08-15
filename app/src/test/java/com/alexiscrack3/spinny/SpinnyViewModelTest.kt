package com.alexiscrack3.spinny

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class SpinnyViewModelTest {

    @Test
    fun `subscriptions are disposed on cleared`() {
        val testObject = TestViewModel()
        testObject.addSubscription()

        assertThat(testObject.size()).isEqualTo(1)

        testObject.onCleared()

        assertThat(testObject.size()).isEqualTo(0)
    }
}
