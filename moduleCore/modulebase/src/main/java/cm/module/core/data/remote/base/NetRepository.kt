package cm.module.core.data.remote.base

import com.lcm.modulebase.BuildConfig
import cm.mvvm.core.repository.NetRetrofitFactory
import cm.mvvm.core.repository.http.log.RequestInterceptor
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

    init {
        retrofit = NetRetrofitFactory()
            .baseUrl(BASE_URL)
            .logLevel(if (BuildConfig.DEBUG) RequestInterceptor.Level.ALL else RequestInterceptor.Level.NONE)
            .build()
    }

    fun <T> create(clz:Class<T>):T? = retrofit?.create(clz)

}