package cm.module.core.utils

import com.blankj.utilcode.util.LogUtils
import cm.module.core.data.entity.ApiResponse
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.Exception
import java.util.concurrent.TimeUnit
import kotlin.Long
import kotlin.String


/**
 * ****************************************************************
 * Author: LiChenMing.Chaman
 * Date: 2019-09-13 10:49
 * Desc:
 * *****************************************************************
 */
object RxHelper {


    val TAG: String = RxHelper::class.java.simpleName

    fun <T> handleResponse(): ObservableTransformer<ApiResponse<T>, T> {
        return ObservableTransformer { upstream ->
            upstream.flatMap {
                val code = it.errorCode
                if (code == 0) {
                    Observable.just(it.data)
                } else {
                    LogUtils.eTag(TAG, it.errorMsg)
                    Observable.error(Exception(it.errorMsg))
                }
            }
        }
    }


    fun <T> io_main(): ObservableTransformer<T, T> {
        return ObservableTransformer {
            it.subscribeOn(Schedulers.io())
                    .unsubscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }

    fun <T> new_main(): ObservableTransformer<T, T> {
        return ObservableTransformer {
            it.subscribeOn(Schedulers.newThread())
                    .unsubscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }

    fun <T> new_new(): ObservableTransformer<T, T> {
        return ObservableTransformer {
            it.subscribeOn(Schedulers.newThread())
                    .unsubscribeOn(Schedulers.newThread())
                    .observeOn(Schedulers.newThread())
        }
    }

    fun countDownSeconds(count: Long, step: Long = 1L): Flowable<Long> {
        return Flowable.intervalRange(0, count, 0, step, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
    }

}