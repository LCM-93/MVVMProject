package com.lcm.modulebase.data.remote.base

import com.lcm.modulebase.BuildConfig
import com.lcm.mvvm.repository.NetRetrofitFactory
import com.lcm.mvvm.repository.http.log.RequestInterceptor
import retrofit2.Retrofit

/**
 * ****************************************************************
 * Author: LiChenMing.Chaman
 * Date: 2019-06-24 17:43
 * Desc: 远程仓库
 * *****************************************************************
 */
class NetRepository private constructor() {
    companion object {
        val instance: NetRepository by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { NetRepository() }

        const val BASE_URL: String = BuildConfig.HTTP_BASE
    }

    private var retrofit: Retrofit? = null
    var api: Api? = null

    init {
        retrofit = NetRetrofitFactory.baseUrl(BASE_URL)
            .logLevel(if (BuildConfig.DEBUG) RequestInterceptor.Level.ALL else RequestInterceptor.Level.NONE)
            .build()
        api = retrofit?.create(Api::class.java)
    }

}