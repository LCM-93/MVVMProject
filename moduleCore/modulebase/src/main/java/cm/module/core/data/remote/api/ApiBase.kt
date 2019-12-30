package cm.module.core.data.remote.api

import cm.module.core.data.entity.ApiResponse
import cm.module.core.data.entity.LoginData
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * ****************************************************************
 * Author: LiChenMing.Chaman
 * Date: 2019/10/24 15:22
 * Desc: api接口  不同模块api接口建议创建单独的接口类  用ApiBase继承
 * *****************************************************************
 */
interface ApiBase {

    @POST("user/login")
    @FormUrlEncoded
    fun login(@Field("username") username: String?, @Field("password") password: String?): Observable<ApiResponse<LoginData?>>
}