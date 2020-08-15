package com.alexiscrack3.spinny

import io.reactivex.Single

class TestViewModel: SpinnyViewModel() {

    fun addSubscription() {
        val disposable = Single.just(1).subscribe()
        disposable.autoDispose()
    }

    public override fun onCleared() {
        super.onCleared()
    }
}
