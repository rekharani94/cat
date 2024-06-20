package me.intuit.cat.presentation.utils.rules

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

 fun <T> LiveData<T>.getorAwaitValue(time:Long=2,
                                            timeUnit: TimeUnit = TimeUnit.SECONDS,
                                            afterObserve:()->Unit = {}): T {
    var data:T? =null
    val latch  = CountDownLatch(1)
    val observrs = object:Observer<T> {
        override fun onChanged(value: T) {
            data = value
            latch.countDown()
            this@getorAwaitValue.removeObserver(this)

        }

    }
    this.observeForever(observrs)
    afterObserve.invoke()
    if(!latch.await(time,timeUnit)) {
        removeObserver(observrs)
        throw TimeoutException()
    }
return data as T
}