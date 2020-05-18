package cm.module.core.data.entity

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

    fun covertData(): T? {
        if(errorCode == 0) return data
        else throw Exception(errorMsg)
    }

}