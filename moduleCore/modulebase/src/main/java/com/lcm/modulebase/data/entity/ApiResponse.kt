package com.lcm.modulebase.data.entity

/**
 * ****************************************************************
 * Author: LiChenMing.Chaman
 * Date: 2019-09-13 9:17
 * Desc:
 * *****************************************************************
 */
class ApiResponse<T> {

    var errorCode: Int? = 0

    var errorMsg: String? = null

    var data: T? = null

    override fun toString(): String {
        return "ApiResponse(errorCode=$errorCode, errorMsg=$errorMsg, data=$data)"
    }


}