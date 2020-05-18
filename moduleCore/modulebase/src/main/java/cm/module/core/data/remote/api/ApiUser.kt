package cm.module.core.data.remote.api

import cm.module.core.data.entity.ApiResponse
import cm.module.core.data.entity.LoginData
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * ****************************************************************
 * Author: LiChenMing.Chaman
 * Date: 2020/5/18 14:14
 * Desc:
 * *****************************************************************
 */
interface ApiUser {

    @POST("user/login")
    @FormUrlEncoded
    suspend fun login(@Field("username") username: String?, @Field("password") password: String?): ApiResponse<LoginData?>

    @POST("user/register")
    @FormUrlEncoded
    suspend fun register(@Field("username") username: String?, @Field("password") password: String?,@Field("repassword")repassword:String?):ApiResponse<Any>
}