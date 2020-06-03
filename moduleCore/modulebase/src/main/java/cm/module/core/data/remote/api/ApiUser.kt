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
 * Date: 2020/6/3 15:55
 * Desc:
 * *****************************************************************
 */
interface ApiUser {


    @POST("user/login")
    @FormUrlEncoded
    fun login(@Field("username") username: String?, @Field("password") password: String?): Observable<ApiResponse<LoginData?>>
}