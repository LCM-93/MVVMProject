package com.lcm.modulebase.data.remote.base

import com.lcm.modulebase.data.entity.ApiResponse
import com.lcm.modulebase.data.entity.LoginData
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * ****************************************************************
 * Author: LiChenMing.Chaman
 * Date: 2019/10/24 15:22
 * Desc:
 * *****************************************************************
 */
interface Api {

    @POST("user/login")
    @FormUrlEncoded
    fun login(@Field("username") username: String?, @Field("password") password: String?): Observable<ApiResponse<LoginData?>>
}