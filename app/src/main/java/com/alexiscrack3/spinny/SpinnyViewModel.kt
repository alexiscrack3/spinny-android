package com.alexiscrack3.spinny

import androidx.annotation.RestrictTo
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class SpinnyViewModel : ViewModel() {
    private val disposables = CompositeDisposable()

    protected fun Disposable.autoDispose() = disposables.add(this)

    @RestrictTo(RestrictTo.Scope.TESTS)
    fun size(): Int = disposables.size()

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}
