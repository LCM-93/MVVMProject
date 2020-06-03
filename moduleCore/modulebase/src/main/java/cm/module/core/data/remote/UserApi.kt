package cm.module.core.data.remote

import cm.module.core.data.entity.LoginData
import cm.module.core.data.remote.api.ApiUser
import cm.module.core.data.remote.base.NetRepository
import cm.module.core.utils.RxHelper
import io.reactivex.Observable

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


    fun login(username:String?,pwd:String?):Observable<LoginData>{
        return apiUser?.login(username,pwd)!!
                .compose(RxHelper.io_main())
                .compose(RxHelper.handleResponse())
    }
}