package cm.module.core.data.remote

import cm.module.core.data.entity.LoginData
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

    companion object {
        val instance: UserApi by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { UserApi() }
    }


    fun login(username:String?,pwd:String?):Observable<LoginData>{
        return NetRepository.instance.api?.login(username,pwd)!!
                .compose(RxHelper.io_main())
                .compose(RxHelper.handleResponse())
    }
}