package com.lcm.modulebase.data.remote

import com.lcm.modulebase.data.entity.LoginData
import com.lcm.modulebase.data.remote.base.NetRepository
import com.lcm.modulebase.utils.RxHelper
import io.reactivex.Observable

/**
 * ****************************************************************
 * Author: LiChenMing.Chaman
 * Date: 2019/10/24 15:19
 * Desc:
 * *****************************************************************
 */
class UserApi private constructor() {

    companion object {
        val instance: UserApi by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { UserApi() }
    }


    fun login(username:String?,pwd:String?):Observable<LoginData>{
        return NetRepository.instance.api?.login(username,pwd)!!
                .compose(RxHelper.io_main())
                .compose(RxHelper.handleResponse())
    }
}