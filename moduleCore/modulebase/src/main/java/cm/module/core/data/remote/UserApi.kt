package cm.module.core.data.remote

import cm.module.core.data.entity.LoginData
import cm.module.core.data.remote.api.ApiUser
import cm.module.core.data.remote.base.NetRepository

/**
 * ****************************************************************
 * Author: LiChenMing.Chaman
 * Date: 2019/10/24 15:19
 * Desc:
 * *****************************************************************
 */
class UserApi private constructor() {
    private var apiUser:ApiUser? = null
    init {
        apiUser = NetRepository.instance.create(ApiUser::class.java)
    }

    companion object {
        val instance: UserApi by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { UserApi() }
    }

    suspend fun login(username:String?,pwd:String?):LoginData?{
        return apiUser?.login(username,pwd)?.covertData()
    }

    suspend fun register(username:String?,pwd:String?,rePwd:String?):Any?{
        return apiUser?.register(username,pwd,rePwd)?.covertData()
    }
}