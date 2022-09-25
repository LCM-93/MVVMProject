package cm.mvvm.core.base.rxjava

/**
 * *****************************************************************
 * @author: LiChenMing
 * @date: 2022/9/19 15:22
 * @description: ObserverCallback.kt
 * *****************************************************************
 */
interface IObserverCallback<T> {
    fun result(data: T)

    fun error(throwable: Throwable, message: String)
}

abstract class SimpleObserverCallback<T> : IObserverCallback<T> {
    override fun error(throwable: Throwable, message: String) {

    }
}