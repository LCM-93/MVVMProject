package cm.mvvm.core.repository

import cm.mvvm.core.repository.http.GlobalHttpHandler
import cm.mvvm.core.repository.http.log.DefaultFormatPrinter
import cm.mvvm.core.repository.http.log.FormatPrinter
import cm.mvvm.core.repository.http.log.RequestInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import com.google.gson.GsonBuilder


/**
 * ****************************************************************
 * Author: LiChenMing.Chaman
 * Date: 2019-07-18 10:56
 * Desc:
 * *****************************************************************
 */
class NetRetrofitFactory {
    private var mTimeOut: Long = 10
    private var mOkHttpClient: OkHttpClient? = null
    private var mBaseUrl: String? = null
    private var mFormatPrinter: FormatPrinter = DefaultFormatPrinter()
    private var mGlobalHttpHandler: GlobalHttpHandler = GlobalHttpHandler.EMPTY
    private var mLogLevel: RequestInterceptor.Level = RequestInterceptor.Level.NONE

    fun readTimeOut(timeOut: Long): NetRetrofitFactory {
        this.mTimeOut = timeOut
        return this
    }

    fun baseUrl(baseUrl: String): NetRetrofitFactory {
        mBaseUrl = baseUrl
        return this
    }

    fun formatPrinter(formatPrinter: FormatPrinter): NetRetrofitFactory {
        mFormatPrinter = formatPrinter
        return this
    }

    fun globalHttpHandler(globalHttpHandler: GlobalHttpHandler): NetRetrofitFactory {
        mGlobalHttpHandler = globalHttpHandler
        return this
    }

    fun logLevel(level: RequestInterceptor.Level): NetRetrofitFactory {
        mLogLevel = level
        return this
    }

    private fun buildOkHttpClient() {
        val requestInterceptor = RequestInterceptor(mGlobalHttpHandler, mFormatPrinter, mLogLevel)
        mOkHttpClient = OkHttpClient
            .Builder()
            .readTimeout(mTimeOut, TimeUnit.SECONDS)
            .writeTimeout(mTimeOut, TimeUnit.SECONDS)
            .addNetworkInterceptor(requestInterceptor)
            .addInterceptor { chain ->
                chain.proceed(
                    mGlobalHttpHandler.onHttpRequestBefore(
                        chain,
                        chain.request()
                    )
                )
            }
            .build()
    }

    fun build(): Retrofit {
        if (mBaseUrl == null) {
            throw Exception("baseUrl need input")
        }
        buildOkHttpClient()
        val gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create()

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(mBaseUrl!!)
            .client(mOkHttpClient!!)
            .build()
    }

}